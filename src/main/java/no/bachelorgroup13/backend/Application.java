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
    // Load the dotenv configuration
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().ignoreIfMalformed().load();
    String endpoint = dotenv.get("COMPUTER_VISION_ENDPOINT");
    String key = dotenv.get("COMPUTER_VISION_SUBSCRIPTION_KEY");
    if (endpoint != null) {
      System.setProperty("COMPUTER_VISION_ENDPOINT", endpoint);
    }
    if (key != null) {
      System.setProperty("COMPUTER_VISION_SUBSCRIPTION_KEY", key);
    }

    SpringApplication.run(Application.class, args);
  }
}
