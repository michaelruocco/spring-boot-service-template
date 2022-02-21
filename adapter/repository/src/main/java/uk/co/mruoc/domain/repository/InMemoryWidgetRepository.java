package uk.co.mruoc.domain.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.entity.PageParams;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;
import uk.co.mruoc.domain.usecase.WidgetRepository;

@RequiredArgsConstructor
public class InMemoryWidgetRepository implements WidgetRepository {

    private final Map<UUID, Widget> widgets;

    public InMemoryWidgetRepository() {
        this(new HashMap<>());
    }

    @Override
    public void save(Widget widget) {
        widgets.put(widget.getId(), widget);
    }

    @Override
    public Optional<Widget> findById(UUID id) {
        return Optional.ofNullable(widgets.get(id));
    }

    @Override
    public int getTotalNumberOfWidgets(QueryWidgetsPageRequest request) {
        return widgets.size();
    }

    @Override
    public Collection<Widget> findWidgets(QueryWidgetsPageRequest request) {
        return getPageWidgets(request.getPageParams());
    }

    private Collection<Widget> getPageWidgets(PageParams pageParams) {
        return widgets.values().stream()
                .sorted(Comparator.comparing(Widget::getDescription))
                .skip(pageParams.getOffset())
                .limit(pageParams.getLimit())
                .collect(Collectors.toList());
    }
}
