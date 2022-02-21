package uk.co.mruoc.domain.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class QueryWidgetsPageRequest {

    private final PageParams pageParams;
}
