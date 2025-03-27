package no.bachelorgroup13.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/** This class is used to load the dotenv configuration. */
@Component
public class DotenvConfig {
  @PostConstruct
  public void loadEnv() {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

    // Azure CV
    String endpoint = dotenv.get("COMPUTER_VISION_ENDPOINT");
    if (endpoint != null) {
      System.setProperty("COMPUTER_VISION_ENDPOINT", endpoint);
    }

    String key = dotenv.get("COMPUTER_VISION_SUBSCRIPTION_KEY");
    if (key != null) {
      System.setProperty("COMPUTER_VISION_SUBSCRIPTION_KEY", key);
    }

    // JWT
    String jwtSecret = dotenv.get("JWT_SECRET");
    if (jwtSecret != null) {
      System.setProperty("JWT_SECRET", jwtSecret.trim());
    }

    String jwtExpiration = dotenv.get("JWT_EXPIRATION");
    if (jwtExpiration != null) {
      System.setProperty("JWT_EXPIRATION", jwtExpiration.trim());
    }
    System.out.println("JWT_SECRET = " + System.getProperty("JWT_SECRET"));

  }
}
