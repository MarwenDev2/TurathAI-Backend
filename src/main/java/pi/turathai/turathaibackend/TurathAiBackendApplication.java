package pi.turathai.turathaibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TurathAiBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(TurathAiBackendApplication.class, args);
    }

}
