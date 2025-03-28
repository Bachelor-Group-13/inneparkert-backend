package no.bachelorgroup13.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@Data
public class JwtConfig {
  @Value("${app.jwt.secret}")
  private String secret;
  @Value("${app.jwt.expiration}")
  private long expiration;
}
