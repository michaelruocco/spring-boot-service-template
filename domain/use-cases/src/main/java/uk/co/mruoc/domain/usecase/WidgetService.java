package uk.co.mruoc.domain.usecase;

import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;
import uk.co.mruoc.domain.entity.WidgetsPage;

@RequiredArgsConstructor
public class WidgetService {

    private final Supplier<UUID> newWidgetIdSupplier;
    private final WidgetRepository repository;

    public Widget create(CreateWidgetRequest request) {
        Widget widget = toWidget(request);
        repository.save(widget);
        return getById(widget.getId());
    }

    public Widget getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new WidgetNotFoundException(id));
    }

    public WidgetsPage getWidgetsPage(QueryWidgetsPageRequest request) {
        return WidgetsPage.builder()
                .request(request)
                .totalNumberOfWidgets(repository.getTotalNumberOfWidgets(request))
                .widgets(repository.findWidgets(request))
                .build();
    }

    private Widget toWidget(CreateWidgetRequest request) {
        return Widget.builder()
                .id(newWidgetIdSupplier.get())
                .description(request.getDescription())
                .cost(request.getCost())
                .build();
    }
}
