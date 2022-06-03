package uk.co.mruoc.domain.widget.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class QueryWidgetsPageRequest {

    private final PageParams pageParams;
}
