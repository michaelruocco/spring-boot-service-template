package uk.co.mruoc.widget.app.rest.test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.domain.widget.usecase.test.OverridableRandomUuidSupplier;
import uk.co.mruoc.domain.widget.usecase.test.WidgetDeleter;
import uk.co.mruoc.test.clock.OverridableClock;
import uk.co.mruoc.widget.api.TestApiDelegate;

@Profile("local")
@Builder
public class RestTestApiDelegate implements TestApiDelegate {

    private final OverridableRandomUuidSupplier uuidSupplier;
    private final OverridableClock clock;
    private final WidgetDeleter widgetDeleter;

    @Override
    public ResponseEntity<List<UUID>> setUuidOverrides(List<UUID> uuids) {
        uuidSupplier.setOverrideUuids(uuids);
        return ResponseEntity.ok(uuids);
    }

    @Override
    public ResponseEntity<List<OffsetDateTime>> setCurrentTimeOverrides(List<OffsetDateTime> currentTimes) {
        List<Instant> instants = toInstants(currentTimes);
        // TODO set instants directly with newer version of testing clocks
        clock.setOverrides(instants.toArray(new Instant[0]));
        return ResponseEntity.ok(currentTimes);
    }

    @Override
    public ResponseEntity<Void> deleteUuidOverrides() {
        uuidSupplier.deleteOverrideUuids();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAllWidgets() {
        widgetDeleter.deleteAll();
        return ResponseEntity.noContent().build();
    }

    private static List<Instant> toInstants(List<OffsetDateTime> currentTimes) {
        return currentTimes.stream().map(OffsetDateTime::toInstant).collect(Collectors.toList());
    }
}
