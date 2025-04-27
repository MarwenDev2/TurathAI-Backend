package pi.turathai.turathaibackend.Services;
import com.twilio.type.PhoneNumber;


import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import pi.turathai.turathaibackend.Controllers.EventsController;
import pi.turathai.turathaibackend.DTO.SmsRequest;
import pi.turathai.turathaibackend.Entites.Itinery;
import com.twilio.Twilio;
import pi.turathai.turathaibackend.Services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pi.turathai.turathaibackend.Repositories.ItenaryRepo;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private ItenaryRepo itineraryRepository;

    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String FROM_NUMBER;

    @Override
    public void sendSms(SmsRequest request) {
        // Initialize Twilio
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Get the itinerary to verify it exists
        Itinery itinerary = itineraryRepository.findById(request.getItineraryId())
                .orElseThrow(() -> new RuntimeException("Itinerary not found"));

        try {
            // Send the SMS using Twilio with the provided phone number
            Message.creator(
                    new PhoneNumber(request.getPhoneNumber()), // To (using provided number)
                    new PhoneNumber(FROM_NUMBER),              // From
                    request.getMessage()                       // Message
            ).create();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }
    }
}