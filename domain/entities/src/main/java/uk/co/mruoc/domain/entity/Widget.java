package uk.co.mruoc.domain.entity;

import java.util.UUID;
import javax.money.MonetaryAmount;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Widget {

    private final UUID id;
    private final String description;
    private final MonetaryAmount cost;
}
