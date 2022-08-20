package uk.co.mruoc.widget.client.model;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationTokenRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final String HEADER_NAME = "Authorization";

    private final Supplier<Optional<String>> tokenSupplier;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        tokenSupplier.get().ifPresent(token -> setAuthorizationHeader(request, token));
        return execution.execute(request, body);
    }

    private void setAuthorizationHeader(HttpRequest request, String token) {
        log.debug("setting {} header with value {}", HEADER_NAME, token);
        request.getHeaders().set(HEADER_NAME, token);
    }
}
