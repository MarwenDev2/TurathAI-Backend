package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.DTO.SmsRequest;

public interface SmsService {
    void sendSms(SmsRequest request);
}