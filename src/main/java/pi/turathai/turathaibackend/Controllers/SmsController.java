package pi.turathai.turathaibackend.Controllers;



import pi.turathai.turathaibackend.DTO.SmsRequest;
import pi.turathai.turathaibackend.DTO.SmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Services.SmsService;

@RestController
@RequestMapping("/api/sms")
@CrossOrigin(origins = "http://localhost:4200")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<SmsResponse> sendSms(@RequestBody SmsRequest request) {

            smsService.sendSms(request);
            return ResponseEntity.ok(new SmsResponse(true, "SMS sent successfully"));

}}