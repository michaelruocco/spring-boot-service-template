package uk.co.mruoc.spring.app.runner;

import com.tngtech.keycloakmock.api.TokenConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;
import uk.co.mruoc.keycloak.KeycloakRunner;
import uk.co.mruoc.keycloak.TokenConfigMother;
import uk.co.mruoc.widget.client.TestApi;
import uk.co.mruoc.widget.client.WidgetsApi;
import uk.co.mruoc.widget.client.model.ClientFactory;

@RequiredArgsConstructor
public class SpringAppExtension implements BeforeAllCallback, AfterAllCallback, CloseableResource {

    private static final KeycloakRunner KEYCLOAK = new KeycloakRunner();
    private static final SpringAppRunner APP = new SpringAppRunner();

    private final SpringAppTestEnvironment env;
    private final ClientFactory clientFactory;

    public SpringAppExtension(SpringAppTestEnvironment env) {
        this(env, new ClientFactory(env.getAppPort()));
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        KEYCLOAK.startIfNotStarted(env.getKeycloakConfig());
        APP.startIfNotStarted(env.getAppConfig());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        shutdown();
    }

    @Override
    public void close() {
        shutdown();
    }

    public WidgetsApi getWidgetClient() {
        return getWidgetClient(TokenConfigMother.demoUser());
    }

    public WidgetsApi getWidgetClient(TokenConfig tokenConfig) {
        return clientFactory.widgetClient(generateToken(tokenConfig));
    }

    public TestApi getTestClient() {
        return getTestClient(TokenConfigMother.demoAdmin());
    }

    private TestApi getTestClient(TokenConfig tokenConfig) {
        return clientFactory.testClient(generateToken(tokenConfig));
    }

    private String generateToken(TokenConfig config) {
        return KEYCLOAK.getAccessToken(config);
    }

    private void shutdown() {
        APP.shutdownIfRunning();
        KEYCLOAK.shutdownIfRunning();
    }
}
