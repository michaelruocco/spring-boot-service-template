package uk.co.mruoc.domain.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WidgetMother {

    public static Widget build() {
        return builder().build();
    }

    public static Widget withId(UUID id) {
        return builder().id(id).build();
    }

    public static Widget.WidgetBuilder builder() {
        return Widget.builder()
                .id(UUID.fromString("dfa46f40-eb42-4d8b-9108-82b93ba68fb1"))
                .description("a fancy widget")
                .cost(Money.of(new BigDecimal("10.99"), "GBP"))
                .createdAt(Instant.parse("2022-05-21T06:35:21Z"));
    }
}
