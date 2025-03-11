package no.bachelorgroup13.license_plate_recognition.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/** This class is used to load the dotenv configuration. */
@Component
public class DotenvConfig {
  @PostConstruct
  public void loadEnv() {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();

    String endpoint = dotenv.get("COMPUTER_VISION_ENDPOINT");
    if (endpoint != null) {
      System.setProperty("COMPUTER_VISION_ENDPOINT", endpoint);
    }

    String key = dotenv.get("COMPUTER_VISION_SUBSCRIPTION_KEY");
    if (key != null) {
      System.setProperty("COMPUTER_VISION_SUBSCRIPTION_KEY", key);
    }
  }
}
