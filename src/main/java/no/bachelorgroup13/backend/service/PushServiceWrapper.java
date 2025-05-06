package no.bachelorgroup13.backend.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Map;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import no.bachelorgroup13.backend.entity.PushNotifications;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PushServiceWrapper {
    private final PushService pushService;

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
