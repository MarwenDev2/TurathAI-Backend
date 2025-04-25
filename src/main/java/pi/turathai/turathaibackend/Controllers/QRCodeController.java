package pi.turathai.turathaibackend.Controllers;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Services.QRCodeService;
import pi.turathai.turathaibackend.Services.ItenaryService;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;
    private final ItenaryService itenaryService;

    @GetMapping(value = "/itinery/{id}",
            produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getQRCode(@PathVariable Long id)
            throws WriterException, IOException {
        return qrCodeService.generateQRCodeForItinery(id, 300, 300);
    }
}