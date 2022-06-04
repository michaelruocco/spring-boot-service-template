package uk.co.mruoc.widget.client.model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import uk.co.mruoc.widget.ApiClient;
import uk.co.mruoc.widget.client.TestApi;
import uk.co.mruoc.widget.client.WidgetsApi;

@RequiredArgsConstructor
public class ClientFactory {

    private final RestTemplate restTemplate;

    public ClientFactory() {
        this(buildRestTemplate());
    }

    public WidgetsApi widgetClient(int port) {
        return new WidgetsApi(toApiClient(port));
    }

    public TestApi testClient(int port) {
        return new TestApi(toApiClient(port));
    }

    private static RestTemplate buildRestTemplate() {
        return new RestTemplate();
    }

    private ApiClient toApiClient(int port) {
        return new ApiClient(restTemplate).setBasePath(toBasePath(port));
    }

    private static String toBasePath(int port) {
        return String.format("http://localhost:%d/v1", port);
    }
}
