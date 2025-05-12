import os
import pandas as pd
import networkx as nx
import numpy as np
from flask import Flask, jsonify, request
from flask_cors import CORS
from collections import defaultdict

app = Flask(__name__)
CORS(app)  # Enable CORS for all routes

# Cache the data to avoid repeated loading
_sites_df = None
_connections_df = None
_graph = None

def load_data():
    """Load CSV data into a graph with error handling"""
    global _sites_df, _connections_df, _graph
    
    # Return cached data if available
    if _graph is not None and _sites_df is not None and _connections_df is not None:
        return _graph, _sites_df, _connections_df
    
    try:
        data_dir = os.path.join(os.path.dirname(__file__), 'data')
        
        # Fix for location column containing commas - use a custom parser
        # The heritage_sites.csv has 6 columns: id, name, description, period, architectural_style, location
        # But location may contain a comma between lat and long
        sites_file_path = os.path.join(data_dir, 'heritage_sites.csv')
        with open(sites_file_path, 'r', encoding='utf-8-sig') as f:
            # Read header
            header = f.readline().strip().split(',')
            
            # Prepare data list
            data = []
            for line in f:
                # Split by commas, but only for the first 5 fields
                parts = line.strip().split(',', 5)
                if len(parts) >= 6:
                    # All the parts up to the location
                    row_data = parts[:5]
                    # Add location as is (may contain commas)
                    row_data.append(parts[5])
                    data.append(row_data)
                else:
                    print(f"Warning: Skipping incomplete line: {line}")
            
            # Create DataFrame manually
            _sites_df = pd.DataFrame(data, columns=header)
            
            # Convert id column to integer
            _sites_df['id'] = _sites_df['id'].astype(int)
        
        # Load connections normally
        _connections_df = pd.read_csv(os.path.join(data_dir, 'heritage_connections.csv'), encoding='utf-8-sig')

        # Create graph with all connections
        _graph = nx.Graph()
        
        # Add all sites as nodes
        for _, row in _sites_df.iterrows():
            _graph.add_node(row['id'], name=row['name'], description=row['description'],
                          period=row['period'], style=row['architectural_style'])
        
        # Add all connections as edges
        for _, row in _connections_df.iterrows():
            _graph.add_edge(
                row['source_id'],
                row['target_id'],
                type=row['connection_type'],
                weight=float(row['weight'])
            )
            
        return _graph, _sites_df, _connections_df
    except Exception as e:
        print(f"Error loading data: {str(e)}")
        return nx.Graph(), pd.DataFrame(), pd.DataFrame()  # Return empty graph on error

def get_recommendations(site_id, limit=5, include_details=True):
    """Get recommended heritage sites based on connections and weights"""
    try:
        G, sites_df, connections_df = load_data()
        
        if site_id not in G.nodes:
            return {"error": f"Site with ID {site_id} not found"}
        
        # Get direct neighbors with their weights
        direct_neighbors = {}
        connection_details = {}
        
        for neighbor in G.neighbors(site_id):
            # Get edge data between site and neighbor
            edge_data = G.get_edge_data(site_id, neighbor)
            weight = edge_data.get('weight', 0.5)
            conn_type = edge_data.get('type', 'unknown')
            
            direct_neighbors[neighbor] = weight
            connection_details[neighbor] = {
                'type': conn_type,
                'weight': weight
            }
        
        # Get second-degree connections (neighbors of neighbors)
        second_degree = {}
        for neighbor in direct_neighbors:
            for neighbor_of_neighbor in G.neighbors(neighbor):
                if neighbor_of_neighbor != site_id and neighbor_of_neighbor not in direct_neighbors:
                    # Decrease weight for second-degree connections
                    edge_data = G.get_edge_data(neighbor, neighbor_of_neighbor)
                    weight = edge_data.get('weight', 0.5) * 0.5  # Reduce weight for indirect connections
                    
                    if neighbor_of_neighbor in second_degree:
                        # Take the highest weight if there are multiple paths
                        second_degree[neighbor_of_neighbor] = max(second_degree[neighbor_of_neighbor], weight)
                    else:
                        second_degree[neighbor_of_neighbor] = weight
        
        # Combine direct and second-degree connections
        all_connections = {**direct_neighbors, **second_degree}
        
        # Sort by weight (descending) and take top results
        recommended_sites = sorted(all_connections.items(), key=lambda x: x[1], reverse=True)[:limit]
        
        # Format response
        result = []
        for site_id, score in recommended_sites:
            site_info = sites_df[sites_df['id'] == site_id].iloc[0].to_dict() if include_details else {}
            
            # Add connection information for direct neighbors
            connection_info = connection_details.get(site_id, {'type': 'indirect', 'weight': score})
            
            # Create result entry
            entry = {
                'id': int(site_id),
                'score': float(score),
                'connection': connection_info
            }
            
            # Add site details if requested
            if include_details:
                entry['name'] = site_info.get('name', '')
                entry['description'] = site_info.get('description', '')
                entry['period'] = site_info.get('period', '')
                entry['architectural_style'] = site_info.get('architectural_style', '')
            
            result.append(entry)
            
        return result
    except Exception as e:
        print(f"Error getting recommendations: {str(e)}")
        return {"error": str(e)}

@app.route('/connections/<int:site_id>')
def get_connections(site_id):
    """Get recommended heritage sites with detailed connection information"""
    try:
        # Get optional parameters
        limit = request.args.get('limit', default=5, type=int)
        include_details = request.args.get('details', default='true').lower() == 'true'
        
        recommendations = get_recommendations(site_id, limit, include_details)
        
        # Return a response object
        return jsonify({
            "status": "success",
            "site_id": site_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({
            "status": "error",
            "message": str(e)
        }), 500

# Additional endpoint for content-based recommendations
@app.route('/similar/<int:site_id>')
def get_similar_sites(site_id):
    """Get similar heritage sites based on architectural style and period"""
    try:
        _, sites_df, _ = load_data()
        
        # Check if site exists
        if site_id not in sites_df['id'].values:
            return jsonify({"status": "error", "message": f"Site with ID {site_id} not found"}), 404
        
        # Get the reference site
        reference_site = sites_df[sites_df['id'] == site_id].iloc[0]
        
        # Calculate similarity scores based on architectural style and period
        similar_sites = []
        for _, site in sites_df.iterrows():
            if site['id'] == site_id:
                continue
                
            similarity_score = 0
            
            # Check architectural style similarity
            if pd.notna(reference_site['architectural_style']) and pd.notna(site['architectural_style']):
                ref_styles = str(reference_site['architectural_style']).lower().split(',')
                site_styles = str(site['architectural_style']).lower().split(',')
                
                # Count matching styles
                matching_styles = sum(1 for style in ref_styles if any(style.strip() in s.strip() for s in site_styles))
                if matching_styles > 0:
                    similarity_score += 0.5 * (matching_styles / max(len(ref_styles), len(site_styles)))
            
            # Check period similarity
            if pd.notna(reference_site['period']) and pd.notna(site['period']):
                if reference_site['period'] == site['period']:
                    similarity_score += 0.5
                elif reference_site['period'] in site['period'] or site['period'] in reference_site['period']:
                    similarity_score += 0.3
            
            if similarity_score > 0:
                similar_sites.append({
                    'id': int(site['id']),
                    'name': site['name'],
                    'description': site['description'],
                    'similarity_score': round(similarity_score, 2),
                    'period': site['period'],
                    'architectural_style': site['architectural_style']
                })
        
        # Sort by similarity score
        similar_sites.sort(key=lambda x: x['similarity_score'], reverse=True)
        
        return jsonify({
            "status": "success",
            "site_id": site_id,
            "similar_sites": similar_sites[:5]  # Return top 5
        })
    except Exception as e:
        return jsonify({
            "status": "error",
            "message": str(e)
        }), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
