package uk.co.mruoc.domain.widget.entity;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateWidgetRequestMother {

    public static CreateWidgetRequest build() {
        return CreateWidgetRequest.builder()
                .description("a fancy widget")
                .cost(Money.of(new BigDecimal("10.99"), "GBP"))
                .build();
    }
}
