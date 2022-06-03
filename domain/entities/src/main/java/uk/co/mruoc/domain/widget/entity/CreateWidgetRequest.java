package uk.co.mruoc.domain.widget.entity;

import javax.money.MonetaryAmount;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateWidgetRequest {

    private final String description;
    private final MonetaryAmount cost;
}
