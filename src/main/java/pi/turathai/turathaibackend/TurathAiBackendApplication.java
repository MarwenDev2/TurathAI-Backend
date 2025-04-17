package pi.turathai.turathaibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TurathAiBackendApplication {

    public static void main(String[] args) {

        /* Run this in a test or main method
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("testpppp");
        System.out.println("Correct hash for 'testpppp': " + hash);*/

        SpringApplication.run(TurathAiBackendApplication.class, args);
    }

}
