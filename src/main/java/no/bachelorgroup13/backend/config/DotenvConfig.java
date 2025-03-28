package no.bachelorgroup13.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

  @PostConstruct
  public void loadEnv() {
    Dotenv dotenv = Dotenv.configure()
        .ignoreIfMissing()
        .ignoreIfMalformed()
        .load();

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
      System.setProperty("COMPUTER_VISION_SUBSCRIPTION_KEY", dotenv.get("COMPUTER_VISION_SUBSCRIPTION_KEY"));
    }
  }
}
