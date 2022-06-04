package uk.co.mruoc.widget.app.rest.test;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.domain.widget.usecase.test.OverridableRandomUuidSupplier;
import uk.co.mruoc.widget.api.TestApiDelegate;

@Profile("local")
@RequiredArgsConstructor
public class RestTestApiDelegate implements TestApiDelegate {

    private final OverridableRandomUuidSupplier uuidSupplier;

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
}
