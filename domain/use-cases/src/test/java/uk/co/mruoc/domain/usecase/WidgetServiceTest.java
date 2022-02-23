package uk.co.mruoc.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;
import uk.co.mruoc.domain.entity.WidgetsPage;

class WidgetServiceTest {

    private static final UUID ID = UUID.fromString("75fa54d8-19c1-48b4-aee9-ed05ef3c81d0");

    private final Supplier<UUID> newIdSupplier = () -> ID;
    private final WidgetRepository repository = mock(WidgetRepository.class);

    private final WidgetService service = new WidgetService(newIdSupplier, repository);

    @Test
    void shouldThrowExceptionIfWidgetIsNotFoundInRepository() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        Throwable error = catchThrowable(() -> service.getById(ID));

        assertThat(error).isInstanceOf(WidgetNotFoundException.class).hasMessage(ID.toString());
    }

    @Test
    void shouldReturnWidgetIfFoundInRepository() {
        Widget expectedWidget = givenWidgetReturnedForId();

        Widget widget = service.getById(ID);

        assertThat(widget).isEqualTo(expectedWidget);
    }

    @Test
    void shouldAllocateNewIdToWidgetAndSave() {
        givenWidgetReturnedForId();
        CreateWidgetRequest request = CreateWidgetRequest.builder()
                .description("test widget")
                .cost(Money.of(89.99, "GBP"))
                .build();

        service.create(request);

        ArgumentCaptor<Widget> captor = ArgumentCaptor.forClass(Widget.class);
        verify(repository).save(captor.capture());
        Widget widget = captor.getValue();
        assertThat(widget.getId()).isEqualTo(ID);
        assertThat(widget.getDescription()).isEqualTo(request.getDescription());
        assertThat(widget.getCost()).isEqualTo(request.getCost());
    }

    @Test
    void shouldReturnCreatedWidgetFromRepository() {
        Widget expectedWidget = givenWidgetReturnedForId();
        CreateWidgetRequest request = mock(CreateWidgetRequest.class);

        Widget widget = service.create(request);

        assertThat(widget).isEqualTo(expectedWidget);
    }

    @Test
    void shouldPopulateRequestOnWidgetsPage() {
        QueryWidgetsPageRequest request = mock(QueryWidgetsPageRequest.class);

        WidgetsPage page = service.getWidgetsPage(request);

        assertThat(page.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldPopulateNumberOfWidgetsOnWidgetsPage() {
        int expectedNumberOfWidgets = 10;
        QueryWidgetsPageRequest request = mock(QueryWidgetsPageRequest.class);
        when(repository.getTotalNumberOfWidgets(request)).thenReturn(expectedNumberOfWidgets);

        WidgetsPage page = service.getWidgetsPage(request);

        assertThat(page.getTotalNumberOfWidgets()).isEqualTo(expectedNumberOfWidgets);
    }

    @Test
    void shouldPopulateWidgetsOnWidgetsPage() {
        Collection<Widget> widgets = Collections.emptyList();
        QueryWidgetsPageRequest request = mock(QueryWidgetsPageRequest.class);
        when(repository.findWidgets(request)).thenReturn(widgets);

        WidgetsPage page = service.getWidgetsPage(request);

        assertThat(page.getWidgets()).isEqualTo(widgets);
    }

    private Widget givenWidgetReturnedForId() {
        Widget widget = mock(Widget.class);
        when(repository.findById(ID)).thenReturn(Optional.of(widget));
        return widget;
    }
}
