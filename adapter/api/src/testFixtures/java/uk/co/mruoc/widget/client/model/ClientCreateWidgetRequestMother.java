package uk.co.mruoc.widget.client.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientCreateWidgetRequestMother {

    public static ClientCreateWidgetRequest build() {
        return new ClientCreateWidgetRequest()
                .description("default test widget")
                .cost(ClientMonetaryAmountMother.build());
    }
}
