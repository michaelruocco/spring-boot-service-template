package uk.co.mruoc.widget.app.env;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import uk.co.mruoc.widget.ApiClient;
import uk.co.mruoc.widget.client.WidgetApi;

@RequiredArgsConstructor
public class ClientFactory {

    private final RestTemplate restTemplate;

    public ClientFactory() {
        this(buildRestTemplate());
    }

    public WidgetApi widgetClient(int port) {
        return new WidgetApi(toApiClient(port));
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
