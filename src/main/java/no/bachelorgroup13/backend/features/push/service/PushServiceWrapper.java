package no.bachelorgroup13.backend.features.push.service;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Map;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import no.bachelorgroup13.backend.features.push.entity.PushNotifications;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PushServiceWrapper {
    @Value("${vapid.keys.public}")
    private String publicKey;

    @Value("${vapid.keys.private}")
    private String privateKey;

    private PushService pushService;

    @PostConstruct
    public void init() {
        try {
            String safePublicKey =
                    publicKey.replace('+', '-').replace('/', '_').replaceAll("=+$", "");
            String safePrivateKey =
                    privateKey.replace('+', '-').replace('/', '_').replaceAll("=+$", "");

            pushService = new PushService(safePublicKey, safePrivateKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize push service", e);
        }
    }

    public PushServiceWrapper(
            @Value("${vapid.keys.public}") String publicKey,
            @Value("${vapid.keys.private}") String privateKey)
            throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        this.pushService = new PushService(publicKey, privateKey);
    }

    public void sendPush(PushNotifications sub, String title, String body)
            throws GeneralSecurityException, IOException {
        String payload = new Gson().toJson(Map.of("title", title, "body", body));
        Notification notification =
                new Notification(sub.getEndpoint(), sub.getP256dh(), sub.getAuth(), payload);

        try {
            pushService.send(notification);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send push notification", e);
        }
    }
}
