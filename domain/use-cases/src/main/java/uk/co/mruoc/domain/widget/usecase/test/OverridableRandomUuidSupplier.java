package uk.co.mruoc.domain.widget.usecase.test;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class OverridableRandomUuidSupplier implements Supplier<UUID> {

    private List<UUID> overrideUuids;

    public void setOverrideUuids(List<UUID> overrideUuids) {
        this.overrideUuids = overrideUuids;
    }

    public void deleteOverrideUuids() {
        this.overrideUuids = null;
    }

    @Override
    public UUID get() {
        if (CollectionUtils.isEmpty(overrideUuids)) {
            return getRandomUuid();
        }
        return getNextOverrideUuid();
    }

    private UUID getNextOverrideUuid() {
        UUID uuid = overrideUuids.remove(0);
        log.info("returning override uuid {}", uuid);
        return uuid;
    }

    private UUID getRandomUuid() {
        UUID uuid = UUID.randomUUID();
        log.info("returning random uuid {}", uuid);
        return uuid;
    }
}
