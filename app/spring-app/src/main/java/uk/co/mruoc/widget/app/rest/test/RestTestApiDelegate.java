package uk.co.mruoc.widget.app.rest.test;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.domain.widget.usecase.test.OverridableRandomUuidSupplier;
import uk.co.mruoc.domain.widget.usecase.test.WidgetDeleter;
import uk.co.mruoc.widget.api.TestApiDelegate;

@Profile("local")
@Builder
public class RestTestApiDelegate implements TestApiDelegate {

    private final OverridableRandomUuidSupplier uuidSupplier;
    private final WidgetDeleter widgetDeleter;

    @Override
    public ResponseEntity<List<UUID>> setUuidOverride(List<UUID> uuids) {
        uuidSupplier.setOverrideUuids(uuids);
        return ResponseEntity.ok(uuids);
    }

    @Override
    public ResponseEntity<Void> deleteUuidOverride() {
        uuidSupplier.deleteOverrideUuids();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAllWidgets() {
        widgetDeleter.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
