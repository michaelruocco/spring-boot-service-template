package uk.co.mruoc.widget.app;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import uk.co.mruoc.spring.app.runner.SpringAppExtension;
import uk.co.mruoc.widget.app.env.ClientFactory;
import uk.co.mruoc.widget.app.env.WidgetAppTestEnvironment;
import uk.co.mruoc.widget.client.WidgetApi;
import uk.co.mruoc.widget.client.model.ClientCreateWidgetRequest;
import uk.co.mruoc.widget.client.model.ClientMonetaryAmount;
import uk.co.mruoc.widget.client.model.ClientWidget;

class SpringWidgetAppIntegrationTest {

    private static final WidgetAppTestEnvironment ENV = WidgetAppTestEnvironment.build();
    private static final ClientFactory CLIENT_FACTORY = new ClientFactory();

    @RegisterExtension
    public static final SpringAppExtension EXTENSION = new SpringAppExtension(ENV);

    @Test
    void shouldCreateWidget() {
        WidgetApi client = widgetClient();
        ClientCreateWidgetRequest request = new ClientCreateWidgetRequest()
                .description("first test widget")
                .cost(new ClientMonetaryAmount().value(new BigDecimal("10.99")).currency("GBP"));

        ClientWidget widget = client.createWidget(request);

        assertThat(widget.getId()).isNotNull();
        assertThat(widget.getCreatedAt()).isCloseToUtcNow(within(550, MILLIS));
        assertThat(widget.getDescription()).isEqualTo(request.getDescription());
        assertThat(widget.getCost()).isEqualTo(request.getCost());
    }

    private static WidgetApi widgetClient() {
        return CLIENT_FACTORY.widgetClient(ENV.getAppPort());
    }
}
