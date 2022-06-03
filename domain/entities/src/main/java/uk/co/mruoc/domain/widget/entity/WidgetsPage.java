package uk.co.mruoc.domain.widget.entity;

import java.util.Collection;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WidgetsPage {

    private final QueryWidgetsPageRequest request;
    private final int totalCount;
    private final Collection<Widget> widgets;
}
