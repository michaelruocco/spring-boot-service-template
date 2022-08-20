package uk.co.mruoc.keycloak;

import static org.awaitility.Awaitility.await;

import com.tngtech.keycloakmock.api.KeycloakMock;
import com.tngtech.keycloakmock.api.ServerConfig;
import com.tngtech.keycloakmock.api.TokenConfig;
import java.time.Duration;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.spring.app.runner.PortReady;

@Slf4j
public class KeycloakRunner {

    private KeycloakMock keycloak;

    public void startIfNotStarted(KeycloakRunnerConfig config) {
        if (Objects.isNull(keycloak)) {
            startKeycloak(config.getServerConfig());
        }
    }

    public void shutdownIfRunning() {
        if (Objects.isNull(keycloak)) {
            return;
        }
        keycloak.stop();
        keycloak = null;
    }

    public String getAccessToken(TokenConfig config) {
        return String.format("Bearer %s", keycloak.getAccessToken(config));
    }

    private void startKeycloak(ServerConfig config) {
        int port = config.getPort();
        String realm = config.getDefaultRealm();
        log.info("starting mock keycloak on port {} with realm {}", port, realm);
        keycloak = new KeycloakMock(config);
        keycloak.start();
        waitForKeycloakStartupToComplete(port);
    }

    private static void waitForKeycloakStartupToComplete(int port) {
        log.info("waiting for keycloak startup to complete...");
        await().dontCatchUncaughtExceptions()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofMillis(250))
                .until(PortReady.local(port));
    }
}
