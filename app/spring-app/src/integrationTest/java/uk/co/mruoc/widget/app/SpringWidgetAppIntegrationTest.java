package uk.co.mruoc.widget.app;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.spring.app.runner.SpringAppExtension;
import uk.co.mruoc.widget.app.env.WidgetAppTestEnvironment;
import uk.co.mruoc.widget.client.TestApi;
import uk.co.mruoc.widget.client.WidgetsApi;
import uk.co.mruoc.widget.client.model.ClientCreateWidgetRequest;
import uk.co.mruoc.widget.client.model.ClientCreateWidgetRequestMother;
import uk.co.mruoc.widget.client.model.ClientFactory;
import uk.co.mruoc.widget.client.model.ClientWidget;

class SpringWidgetAppIntegrationTest {

    private static final WidgetAppTestEnvironment ENV = WidgetAppTestEnvironment.build();
    private static final ClientFactory CLIENT_FACTORY = new ClientFactory();

    @RegisterExtension
    public static final SpringAppExtension EXTENSION = new SpringAppExtension(ENV);

    @Test
    void shouldCreateWidget() {
        UUID expectedId = UUID.fromString("4488ae82-519b-419f-9661-3c5cd7cc156c");
        givenNextRandomUuids(expectedId);
        WidgetsApi client = widgetClient();
        ClientCreateWidgetRequest request = ClientCreateWidgetRequestMother.build();

        ClientWidget widget = client.createWidget(request);

        assertThat(widget.getId()).isEqualTo(expectedId);
        assertThat(widget.getCreatedAt()).isCloseToUtcNow(within(550, MILLIS));
        assertThat(widget.getDescription()).isEqualTo(request.getDescription());
        assertThat(widget.getCost()).isEqualTo(request.getCost());
    }

    @Test
    void shouldReturnCreatedResponseAndLocationHeaderWhenWidgetCreated() {
        UUID expectedId = UUID.fromString("80282312-a0dd-4b08-a23f-fa2ae3114e6f");
        givenNextRandomUuids(expectedId);
        WidgetsApi client = widgetClient();
        ClientCreateWidgetRequest request = ClientCreateWidgetRequestMother.build();

        ResponseEntity<ClientWidget> response = client.createWidgetWithHttpInfo(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().get("location")).containsExactly(toWidgetLocation(expectedId));
    }

    private static void givenNextRandomUuids(UUID... uuids) {
        TestApi testClient = testClient();
        testClient.setUuidOverride(Arrays.asList(uuids));
    }

    private static String toWidgetLocation(UUID id) {
        return String.format("http://localhost:%d/v1/widgets/%s", ENV.getAppPort(), id);
    }

    private static WidgetsApi widgetClient() {
        return CLIENT_FACTORY.widgetClient(ENV.getAppPort());
    }

    private static TestApi testClient() {
        return CLIENT_FACTORY.testClient(ENV.getAppPort());
    }
}