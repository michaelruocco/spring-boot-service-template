package uk.co.mruoc.widget.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import uk.co.mruoc.widget.ApiClient;
import uk.co.mruoc.widget.client.TestApi;
import uk.co.mruoc.widget.client.WidgetsApi;

@RequiredArgsConstructor
public class ClientFactory {

    private final int port;

    public WidgetsApi widgetClient(String token) {
        return new WidgetsApi(buildApiClient(token));
    }

    public TestApi testClient(String token) {
        return new TestApi(buildApiClient(token));
    }

    private ApiClient buildApiClient(String token) {
        return new ApiClient(buildRestTemplate(token)).setBasePath(buildBasePath());
    }

    private String buildBasePath() {
        return String.format("http://localhost:%d/v1", port);
    }

    private RestTemplate buildRestTemplate(String token) {
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(template.getInterceptors());
        interceptors.add(new AuthorizationTokenRequestInterceptor(() -> Optional.ofNullable(token)));
        template.setInterceptors(interceptors);
        return template;
    }
}
