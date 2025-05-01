package pi.turathai.turathaibackend.Services;





import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.LocalInsight;
import pi.turathai.turathaibackend.Repositories.LocalInsightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalInsightService implements ILocalInsight {

    private static final Logger logger = LoggerFactory.getLogger(LocalInsightService.class);

    private final JavaMailSender mailSender;
    private final LocalInsightRepository localInsightRepository;

    // Constructeur explicite (remplace @RequiredArgsConstructor)
    public LocalInsightService(JavaMailSender mailSender,
                               LocalInsightRepository localInsightRepository) {
        this.mailSender = mailSender;
        this.localInsightRepository = localInsightRepository;

    }


    @Transactional
    public LocalInsight incrementLike(Long id) {
        LocalInsight insight = localInsightRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("LocalInsight not found with id: " + id));

        insight.setLikes(insight.getLikes() + 1);
        return localInsightRepository.save(insight);
    }

    @Transactional
    public LocalInsight incrementDislike(Long id) {
        LocalInsight insight = localInsightRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("LocalInsight not found with id: " + id));

        insight.setDislikes(insight.getDislikes() + 1);
        return localInsightRepository.save(insight);
    }
    @Override
    public LocalInsight saveLocalInsight(LocalInsight localInsight) {
        return localInsightRepository.save(localInsight);
    }

    @Override
    public Optional<LocalInsight> getLocalInsightById(Long id) {
        return localInsightRepository.findById(id);
    }
    @Override
    public List<LocalInsight> getAllLocalInsights() {
        return localInsightRepository.findAll();
    }

    public List<Map<String, Object>> getInsightsByType() {
        List<Object[]> results = localInsightRepository.countByType();
        return results.stream()
                .map(result -> Map.of(
                        "type", result[0],
                        "count", result[1]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLocalInsight(Long id) {
        localInsightRepository.deleteById(id);
    }

    @Override
    public void sendConfirmationEmail(LocalInsight insight) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // L'email est envoyé depuis le compte Gmail de l'application
            helper.setFrom("noreplyturathai@gmail.com", "TurathAI Notification");
            helper.setTo("syrine.benamara@esprit.com");  // Email de l'admin
            helper.setSubject("[TurathAI] Nouveau Local Insight: " + insight.getTitle());

            String htmlContent = String.format(
                    "<h3>Nouveau Local Insight créé</h3>" +
                            "<p><strong>Titre:</strong> %s</p>" +
                            "<p><strong>Type:</strong> %s</p>" +
                            "<p><strong>Description:</strong> %s</p>" +
                            "<p>Date de création: %s</p>",
                    insight.getTitle(),
                    insight.getType(),
                    insight.getDescription(),
                    LocalDateTime.now()
            );

            helper.setText(htmlContent, true);
            mailSender.send(message);
            logger.info("Email de confirmation envoyé à l'admin");
        } catch (Exception e) {
            logger.error("Échec d'envoi d'email", e);
        }
    }

    @Override
    public List<LocalInsight> getLocalInsightsBySiteId(Long siteId) {
        return localInsightRepository.findBySiteId(siteId);
    }
}
