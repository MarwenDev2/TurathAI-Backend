package pi.turathai.turathaibackend.Services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("mailingService")
public class MailingService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String name, String location, String description) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);

        String htmlBody = """
            <html>
                <body style="font-family: Arial, sans-serif;">
                    <div style="text-align: center; background-color: #f4f4f4; padding: 20px;">
                        <img src='cid:logoImage' style="width: 150px; margin-bottom: 20px;">
                        <br>
                        <img src='cid:backgroundImage' style="width: 100%%; max-width: 600px; height: auto; border-radius: 8px;">
                        <h2 style="color: #333;">New Heritage Site: %s</h2>
                        <p style="font-size: 16px; color: #555;"><strong>Location:</strong> %s</p>
                        <p style="font-size: 16px; color: #555;"><strong>Description:</strong> %s</p>
                        <p style="margin-top: 20px; font-size: 14px; color: #888;">Visit TurathAI for more amazing sites!</p>
                    </div>
                </body>
            </html>
        """.formatted(name, location, description);

        helper.setText(htmlBody, true);

        FileSystemResource logo = new FileSystemResource(new File("C:/Users/hazem/OneDrive/Desktop/hazem/TurathAI-Frontend-main/public/assets/images/logo-dark.png"));
        FileSystemResource background = new FileSystemResource(new File("C:/Users/hazem/OneDrive/Desktop/hazem/TurathAI-Frontend-main/public/assets/images/small/img-10.jpg"));

        helper.addInline("logoImage", logo);
        helper.addInline("backgroundImage", background);

        mailSender.send(message);
    }
}