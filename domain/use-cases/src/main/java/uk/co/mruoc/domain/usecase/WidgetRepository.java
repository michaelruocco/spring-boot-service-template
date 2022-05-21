package uk.co.mruoc.domain.usecase;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;

public interface WidgetRepository {

    void save(Widget widget);

    Optional<Widget> findById(UUID id);

    int getTotalCount(QueryWidgetsPageRequest request);

    Collection<Widget> find(QueryWidgetsPageRequest request);
}
