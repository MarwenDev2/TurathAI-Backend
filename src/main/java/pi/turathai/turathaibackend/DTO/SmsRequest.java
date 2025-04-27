package pi.turathai.turathaibackend.DTO;

import lombok.Data;

@Data
public class SmsRequest {
    private String message;
    private Long itineraryId;
    private String phoneNumber;
}
