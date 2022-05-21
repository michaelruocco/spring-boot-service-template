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
import org.junit.jupiter.api.Test;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;
import uk.co.mruoc.domain.entity.WidgetMother;
import uk.co.mruoc.domain.entity.WidgetsPage;

class WidgetServiceTest {

    private final WidgetFactory factory = mock(WidgetFactory.class);
    private final WidgetRepository repository = mock(WidgetRepository.class);

    private final WidgetService service = new WidgetService(factory, repository);

    @Test
    void shouldThrowExceptionIfWidgetIsNotFoundInRepository() {
        UUID id = UUID.fromString("632b0be0-55bd-46ba-bfb8-149e06e262d7");
        when(repository.findById(id)).thenReturn(Optional.empty());

        Throwable error = catchThrowable(() -> service.getById(id));

        assertThat(error).isInstanceOf(WidgetNotFoundException.class).hasMessage(id.toString());
    }

    @Test
    void shouldReturnWidgetIfFoundInRepository() {
        UUID id = UUID.fromString("92054068-af48-4903-acc4-0b3f8ed7a128");
        Widget expectedWidget = givenWidgetReturnedForId(id);

        Widget widget = service.getById(id);

        assertThat(widget).isEqualTo(expectedWidget);
    }

    @Test
    void shouldSaveWidget() {
        CreateWidgetRequest request = mock(CreateWidgetRequest.class);
        Widget expectedWidget = WidgetMother.build();
        when(factory.build(request)).thenReturn(expectedWidget);

        service.create(request);

        verify(repository).save(expectedWidget);
    }

    @Test
    void shouldReturnWidgetIdOnCreate() {
        CreateWidgetRequest request = mock(CreateWidgetRequest.class);
        Widget expectedWidget = WidgetMother.build();
        when(factory.build(request)).thenReturn(expectedWidget);

        UUID id = service.create(request);

        assertThat(id).isEqualTo(expectedWidget.getId());
    }

    @Test
    void shouldPopulateRequestOnWidgetsPage() {
        QueryWidgetsPageRequest request = mock(QueryWidgetsPageRequest.class);

        WidgetsPage page = service.getWidgetsPage(request);

        assertThat(page.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldPopulateTotalCountOnWidgetsPage() {
        int expectedCount = 10;
        QueryWidgetsPageRequest request = mock(QueryWidgetsPageRequest.class);
        when(repository.getTotalCount(request)).thenReturn(expectedCount);

        WidgetsPage page = service.getWidgetsPage(request);

        assertThat(page.getTotalCount()).isEqualTo(expectedCount);
    }

    @Test
    void shouldPopulateWidgetsOnWidgetsPage() {
        Collection<Widget> widgets = Collections.emptyList();
        QueryWidgetsPageRequest request = mock(QueryWidgetsPageRequest.class);
        when(repository.find(request)).thenReturn(widgets);

        WidgetsPage page = service.getWidgetsPage(request);

        assertThat(page.getWidgets()).isEqualTo(widgets);
    }

    private Widget givenWidgetReturnedForId(UUID id) {
        Widget widget = WidgetMother.withId(id);
        when(repository.findById(widget.getId())).thenReturn(Optional.of(widget));
        return widget;
    }
}
