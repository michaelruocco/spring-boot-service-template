package uk.co.mruoc.keycloak;

import com.tngtech.keycloakmock.api.ServerConfig;
import lombok.Builder;

@Builder
public class KeycloakRunnerConfig {

    private final int port;
    private final String realm;

    public ServerConfig getServerConfig() {
        return ServerConfig.aServerConfig()
                .withPort(port)
                .withDefaultRealm(realm)
                .build();
    }

    public String getIssuerUrl() {
        return String.format("http://localhost:%d/auth/realms/%s", port, realm);
    }
}
