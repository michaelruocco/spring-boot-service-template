package uk.co.mruoc.domain.usecase;

import java.time.Clock;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.Widget;

@RequiredArgsConstructor
public class WidgetFactory {

    private final Supplier<UUID> idSupplier;
    private final Clock clock;

    public Widget build(CreateWidgetRequest request) {
        return Widget.builder()
                .id(idSupplier.get())
                .description(request.getDescription())
                .cost(request.getCost())
                .createdAt(clock.instant())
                .build();
    }
}
