package pi.turathai.turathaibackend.Security;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Repositories.*;
@Service
public class EmailService {

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String userName, String token, String newPassword) {
        String resetUrl = baseUrl + "/reset-password?token=" + token;
        String subject = "Password Reset Request";

        String content = buildEmailWithNewPassword(userName, resetUrl, newPassword);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    private String buildEmailWithNewPassword(String name, String resetLink, String newPassword) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "<tr><td style=\"padding:20px;background:#f5f5f5\">\n" +
                "  <table width=\"80%\" align=\"center\" style=\"max-width:580px;margin:0 auto;background:#ffffff;padding:30px;border-radius:8px\">\n" +
                "    <tr><td>\n" +
                "      <h2 style=\"margin:0 0 20px;color:#333\">Hello " + name + ",</h2>\n" +
                "      <p style=\"margin-bottom:15px\">We’ve reset your password. You can log in with the new password below or choose to set your own password using the reset link.</p>\n" +
                "      <p style=\"font-size:18px;font-weight:bold;background:#f0f0f0;padding:10px 15px;border-radius:5px;text-align:center;margin-bottom:20px\">" + newPassword + "</p>\n" +
                "      <p style=\"text-align:center;margin-bottom:20px\">\n" +
                "        <a href=\"" + resetLink + "\" style=\"display:inline-block;padding:10px 20px;background:#00823b;color:#fff;text-decoration:none;border-radius:5px\">Set Your Own Password</a>\n" +
                "      </p>\n" +
                "      <p style=\"font-size:14px;color:#777\">If you didn’t request this, you can safely ignore this email.</p>\n" +
                "    </td></tr>\n" +
                "  </table>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "</div>";
    }

    public void sendLocalInsightNotificationEmail(String toEmail, String userName, String insightTitle) {
        String subject = "Your Local Insight has been added!";

        String content = buildLocalInsightEmailContent(userName, insightTitle);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String buildLocalInsightEmailContent(String name, String insightTitle) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "<tr><td style=\"padding:20px;background:#f5f5f5\">\n" +
                "  <table width=\"80%\" align=\"center\" style=\"max-width:580px;margin:0 auto;background:#ffffff;padding:30px;border-radius:8px\">\n" +
                "    <tr><td>\n" +
                "      <h2 style=\"margin:0 0 20px;color:#333\">Hello " + name + ",</h2>\n" +
                "      <p style=\"margin-bottom:15px\">Your local insight \"" + insightTitle + "\" has been successfully added.</p>\n" +
                "      <p style=\"margin-bottom:15px\">Thank you for contributing to TurathAI and helping us share the cultural heritage with the world!</p>\n" +
                "      <p style=\"font-size:14px;color:#777\">Best regards,<br>The TurathAI Team</p>\n" +
                "    </td></tr>\n" +
                "  </table>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "</div>";
    }

}