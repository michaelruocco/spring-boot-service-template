package uk.co.mruoc.domain.usecase;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;
import uk.co.mruoc.domain.entity.WidgetsPage;

@RequiredArgsConstructor
public class WidgetService {

    private final WidgetFactory factory;
    private final WidgetRepository repository;

    public UUID create(CreateWidgetRequest request) {
        Widget widget = factory.build(request);
        repository.save(widget);
        return widget.getId();
    }

    public Widget getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new WidgetNotFoundException(id));
    }

    public WidgetsPage getWidgetsPage(QueryWidgetsPageRequest request) {
        return WidgetsPage.builder()
                .request(request)
                .totalCount(repository.getTotalCount(request))
                .widgets(repository.find(request))
                .build();
    }
}
