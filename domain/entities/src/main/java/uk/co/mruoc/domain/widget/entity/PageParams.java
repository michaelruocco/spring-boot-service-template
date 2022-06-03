package uk.co.mruoc.domain.widget.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageParams {

    private final int limit;
    private final int offset;
}
