package uk.co.mruoc.widget.client.model;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMonetaryAmountMother {

    public static ClientMonetaryAmount build() {
        return gbp(10.99);
    }

    public static ClientMonetaryAmount gbp(double value) {
        return new ClientMonetaryAmount().value(BigDecimal.valueOf(value)).currency("GBP");
    }
}
