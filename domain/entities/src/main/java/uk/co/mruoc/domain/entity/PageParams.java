package uk.co.mruoc.domain.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageParams {

    private final int limit;
    private final int offset;
}
