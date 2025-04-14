package pi.turathai.turathaibackend.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * CommentDTO est un objet de transfert de données utilisé pour
 * recevoir les données d’un commentaire depuis le frontend.
 * Il contient uniquement les champs nécessaires pour la création d’un commentaire.
 */
@Getter
@Setter
public class CommentDTO {

    /**
     * Contenu textuel du commentaire.
     */
    private String content;

    /**
     * Chemin ou URL de l’image liée au commentaire.
     */
    private String image;

    /**
     * Identifiant de l’utilisateur qui écrit le commentaire.
     */
    private Long userId;

    /**
     * Identifiant du forum auquel ce commentaire est lié.
     */
    private Long forumId;
}
