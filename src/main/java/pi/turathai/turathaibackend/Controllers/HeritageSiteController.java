package pi.turathai.turathaibackend.Controllers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.HeritageSiteDTO;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;
import pi.turathai.turathaibackend.Services.HeritageSiteService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/Sites")
@CrossOrigin(origins = "*")
public class HeritageSiteController {

    @Autowired
    private HeritageSiteService heritageSiteService;

    @PostMapping("/addSite")
    public HeritageSiteDTO addSite(@RequestBody HeritageSiteDTO dto) {
        return heritageSiteService.addFromDTO(dto);
    }

    @PutMapping("/updateSite")
    public HeritageSiteDTO updateSite(@RequestBody HeritageSiteDTO dto) {
        return heritageSiteService.updateFromDTO(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSite(@PathVariable long id) {
        heritageSiteService.remove(id);
    }

    @GetMapping("/get/{id}")
    public HeritageSiteDTO getSiteById(@PathVariable long id) {
        return heritageSiteService.getByID(id);
    }

    @GetMapping("/gett/{id}")
    public HeritageSite gettSite(@PathVariable long id)
    {
        return heritageSiteService.getSite(id);
    }

    @GetMapping("/all")
    public List<HeritageSiteDTO> getAllSites() {
        return heritageSiteService.getAll();
    }

    @GetMapping("/count")
    public long getSiteCount() {
        return heritageSiteService.countSites();
    }


    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportSitesToExcel() {
        List<HeritageSite> sites = heritageSiteService.getAllE(); // adjust based on your service

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sites");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("Location");
            header.createCell(2).setCellValue("Description");
            header.createCell(3).setCellValue("Category");
            header.createCell(4).setCellValue("Popularity");
            header.createCell(5).setCellValue("Historical Significance");

            int rowIdx = 1;
            for (HeritageSite site : sites) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(site.getName());
                row.createCell(1).setCellValue(site.getLocation());
                row.createCell(2).setCellValue(site.getDescription());
                row.createCell(3).setCellValue(site.getCategory().getName());
                row.createCell(4).setCellValue(site.getPopularityScore());
                row.createCell(5).setCellValue(site.getHistoricalSignificance());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sites.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(out.toByteArray());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public List<HeritageSite> searchSites(@RequestParam(required = false) String keyword) {
        return heritageSiteService.searchSites(keyword);
    }

}
