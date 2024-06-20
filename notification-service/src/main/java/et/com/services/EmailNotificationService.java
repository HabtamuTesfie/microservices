package et.com.services;

import et.com.dto.OrderPlacedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationService {

    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;
    private final EmailService emailService;

    @Value("${notification.email.recipients}")
    private String notificationEmailRecipients;

    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
            log.info("Got message <{}>", orderPlacedEvent);
            log.info("TraceId- {}, Received Notification for Order - {}",
                Objects.requireNonNull(this.tracer.currentSpan()).context().traceId(),
                orderPlacedEvent.getOrderNumber());

            try {
                String subject = "Order Placed Notification";
                String body = String.format("Received notification for Order Number: %s", orderPlacedEvent.getOrderNumber());

                // Split the recipients from the configuration into a list
                List<String> recipients = Arrays.asList(notificationEmailRecipients.split(","));

                emailService.sendEmail(recipients, subject, body);
            } catch (Exception e) {
                log.error("Failed to send email notification", e);
            }
        });
    }
}
