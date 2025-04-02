package no.bachelorgroup13.backend;

import io.github.cdimascio.dotenv.Dotenv;
import no.bachelorgroup13.backend.azurecv.LicensePlateProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LicensePlateProperties.class)
public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

        if (dotenv.get("JWT_SECRET") != null) {
            System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET").trim());
        }

        if (dotenv.get("JWT_EXPIRATION") != null) {
            System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION").trim());
        }

        if (dotenv.get("COMPUTER_VISION_ENDPOINT") != null) {
            System.setProperty("COMPUTER_VISION_ENDPOINT", dotenv.get("COMPUTER_VISION_ENDPOINT"));
        }

        if (dotenv.get("COMPUTER_VISION_SUBSCRIPTION_KEY") != null) {
            System.setProperty(
                    "COMPUTER_VISION_SUBSCRIPTION_KEY",
                    dotenv.get("COMPUTER_VISION_SUBSCRIPTION_KEY"));
        }
        SpringApplication.run(Application.class, args);
    }
}
