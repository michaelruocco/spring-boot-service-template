package uk.co.mruoc.widget.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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
import uk.co.mruoc.widget.client.model.ClientPageParams;
import uk.co.mruoc.widget.client.model.ClientQueryWidgetsPageRequest;
import uk.co.mruoc.widget.client.model.ClientWidget;
import uk.co.mruoc.widget.client.model.ClientWidgetsPage;

class SpringWidgetAppIntegrationTest {

    private static final WidgetAppTestEnvironment ENV = WidgetAppTestEnvironment.build();

    @RegisterExtension
    public static final SpringAppExtension EXTENSION = new SpringAppExtension(ENV);

    @BeforeEach
    void setUp() {
        deleteAllWidgets();
    }

    @Test
    void shouldAllocateIdToCreatedWidget() {
        UUID expectedId = UUID.fromString("4488ae82-519b-419f-9661-3c5cd7cc156c");
        givenNextRandomUuids(expectedId);
        WidgetsApi client = widgetClient();
        ClientCreateWidgetRequest request = ClientCreateWidgetRequestMother.build();

        ClientWidget widget = client.createWidget(request);

        assertThat(widget.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldSetCurrentTimeOnCreatedWidget() {
        OffsetDateTime currentTime = OffsetDateTime.parse("2022-06-04T15:25:00Z");
        givenCurrentTimes(currentTime);
        WidgetsApi client = widgetClient();
        ClientCreateWidgetRequest request = ClientCreateWidgetRequestMother.build();

        ClientWidget widget = client.createWidget(request);

        assertThat(widget.getCreatedAt()).isEqualTo(currentTime);
    }

    @Test
    void shouldSetSpecifiedFieldsOnCreatedWidget() {
        WidgetsApi client = widgetClient();
        ClientCreateWidgetRequest request = ClientCreateWidgetRequestMother.build();

        ClientWidget widget = client.createWidget(request);

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

    @Test
    void shouldReturnPageOfWidgets() {
        WidgetsApi client = widgetClient();
        givenWidgetExistsWithDescriptions("widget-1", "widget-2", "widget-3");
        ClientQueryWidgetsPageRequest request = new ClientQueryWidgetsPageRequest()
                .pageParams(new ClientPageParams().offset(0).limit(2));

        ClientWidgetsPage page = client.queryWidgetsPage(request);

        assertThat(page.getTotalCount()).isEqualTo(3);
        assertThat(page.getWidgets()).map(ClientWidget::getDescription).containsExactly("widget-1", "widget-2");
    }

    @Test
    void shouldGetWidgetById() {
        UUID id = UUID.fromString("89afff48-d3bf-485f-8fa8-4d40c2e11751");
        givenNextRandomUuids(id);
        OffsetDateTime currentTime = OffsetDateTime.parse("2022-05-04T14:14:11Z");
        givenCurrentTimes(currentTime);
        WidgetsApi client = widgetClient();
        ClientCreateWidgetRequest request = ClientCreateWidgetRequestMother.build();
        givenWidgetExists(request);

        ClientWidget widget = client.getWidgetById(id);

        assertThat(widget.getId()).isEqualTo(id);
        assertThat(widget.getCreatedAt()).isEqualTo(currentTime);
        assertThat(widget.getDescription()).isEqualTo(request.getDescription());
        assertThat(widget.getCost()).isEqualTo(request.getCost());
    }

    private static void deleteAllWidgets() {
        TestApi client = testClient();
        client.deleteAllWidgets();
        client.deleteUuidOverrides();
    }

    private static void givenWidgetExistsWithDescriptions(String... descriptions) {
        Arrays.stream(descriptions)
                .map(d -> ClientCreateWidgetRequestMother.build().description(d))
                .forEach(SpringWidgetAppIntegrationTest::givenWidgetExists);
    }

    private static void givenWidgetExists(ClientCreateWidgetRequest request) {
        WidgetsApi client = widgetClient();
        client.createWidget(request);
    }

    private static void givenNextRandomUuids(UUID... uuids) {
        TestApi testClient = testClient();
        testClient.setUuidOverrides(Arrays.asList(uuids));
    }

    private static void givenCurrentTimes(OffsetDateTime... timestamps) {
        TestApi testClient = testClient();
        testClient.setCurrentTimeOverrides(Arrays.asList(timestamps));
    }

    private static String toWidgetLocation(UUID id) {
        return String.format("http://localhost:%d/v1/widgets/%s", ENV.getAppPort(), id);
    }

    private static WidgetsApi widgetClient() {
        return EXTENSION.getWidgetClient();
    }

    private static TestApi testClient() {
        return EXTENSION.getTestClient();
    }
}
