package no.bachelorgroup13.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import no.bachelorgroup13.backend.entity.PushNotifications;
import org.springframework.data.repository.CrudRepository;

public interface PushSubscriptionRepository extends CrudRepository<PushNotifications, Long> {
    List<PushNotifications> findAllByUserId(UUID userId);

    Optional<PushNotifications> findByEndpoint(String endpoint);
}
