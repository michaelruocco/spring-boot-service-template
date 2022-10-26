package uk.co.mruoc.domain.widget.usecase;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.widget.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.widget.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.widget.entity.Widget;
import uk.co.mruoc.domain.widget.entity.WidgetsPage;

@RequiredArgsConstructor
public class WidgetService {

    private final WidgetFactory factory;
    private final WidgetRepository repository;
    private final WidgetMetricRecorder metricRecorder;

    public UUID create(CreateWidgetRequest request) {
        Widget widget = factory.build(request);
        repository.save(widget);
        metricRecorder.recordCreated(widget);
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
