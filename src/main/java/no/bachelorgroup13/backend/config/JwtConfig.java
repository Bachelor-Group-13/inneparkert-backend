package no.bachelorgroup13.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class JwtConfig {
  @Value("${app.jwt.secret}")
  private String secret;
  @Value("${app.jwt.expiration}")
  private long expiration;

  public String getSecret() {
    return secret;
  }

  public long getExpiration() {
    return expiration;
  }
}
