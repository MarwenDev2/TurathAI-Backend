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
import pi.turathai.turathaibackend.Services.MailingService;
import pi.turathai.turathaibackend.Services.HeritageSiteService;
import pi.turathai.turathaibackend.Services.UserService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/Sites")
@CrossOrigin(origins = "*")
public class HeritageSiteController {


    private static final Logger logger = LoggerFactory.getLogger(HeritageSiteController.class);

    @Autowired
    private HeritageSiteService heritageSiteService;

    @Autowired
    private UserService userService ;

    @Autowired
    private MailingService emailService;



    @PostMapping("/addSite")
    public ResponseEntity<HeritageSiteDTO> addSite(@RequestBody HeritageSiteDTO dto) {
        try {
            // 1. Add the site
            HeritageSiteDTO newSite = heritageSiteService.addFromDTO(dto);

            // 2. Get all user emails
            List<String> userEmails = userService.getAllUserEmails();

            // 3. Create email subject
            String subject = "New Heritage Site Added: " + newSite.getName();

            // 4. Send emails asynchronously
            CompletableFuture.runAsync(() -> {
                for (String email : userEmails) {
                    try {
                        emailService.sendEmail(
                                email,
                                subject,
                                newSite.getName(),
                                newSite.getLocation(),
                                newSite.getDescription()
                        );
                    } catch (Exception e) {
                        logger.error("Failed to send email to {}", email, e);
                    }
                }
            });

            // 5. Return success response
            return ResponseEntity.status(HttpStatus.CREATED).body(newSite);

        } catch (Exception e) {
            logger.error("Failed to add heritage site", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
