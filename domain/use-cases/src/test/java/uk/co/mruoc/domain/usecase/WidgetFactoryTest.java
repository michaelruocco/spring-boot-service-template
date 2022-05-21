package uk.co.mruoc.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.CreateWidgetRequestMother;
import uk.co.mruoc.domain.entity.Widget;

class WidgetFactoryTest {

    private static final UUID ID = UUID.fromString("75fa54d8-19c1-48b4-aee9-ed05ef3c81d0");
    private static final Instant NOW = Instant.parse("2022-05-21T06:21:45Z");

    private final Supplier<UUID> idSupplier = () -> ID;
    private final Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);

    private final WidgetFactory factory = new WidgetFactory(idSupplier, clock);

    @Test
    void shouldAllocateId() {
        CreateWidgetRequest request = CreateWidgetRequestMother.build();

        Widget widget = factory.build(request);

        assertThat(widget.getId()).isEqualTo(ID);
    }

    @Test
    void shouldPopulateCreatedAt() {
        CreateWidgetRequest request = CreateWidgetRequestMother.build();

        Widget widget = factory.build(request);

        assertThat(widget.getCreatedAt()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateDescription() {
        CreateWidgetRequest request = CreateWidgetRequestMother.build();

        Widget widget = factory.build(request);

        assertThat(widget.getDescription()).isEqualTo(request.getDescription());
    }

    @Test
    void shouldPopulateCost() {
        CreateWidgetRequest request = CreateWidgetRequestMother.build();

        Widget widget = factory.build(request);

        assertThat(widget.getCost()).isEqualTo(request.getCost());
    }
}
