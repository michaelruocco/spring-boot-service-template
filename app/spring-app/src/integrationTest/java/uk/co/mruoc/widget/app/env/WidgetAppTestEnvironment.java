package uk.co.mruoc.widget.app.env;

import static uk.co.mruoc.spring.app.runner.AvailablePortFinder.findAvailableTcpPort;

import lombok.Builder;
import lombok.Data;
import uk.co.mruoc.keycloak.KeycloakRunnerConfig;
import uk.co.mruoc.spring.app.runner.SpringAppRunnerConfig;
import uk.co.mruoc.spring.app.runner.SpringAppTestEnvironment;

@Builder
@Data
public class WidgetAppTestEnvironment implements SpringAppTestEnvironment {

    private final SpringAppRunnerConfig appConfig;
    private final KeycloakRunnerConfig keycloakConfig;

    public static WidgetAppTestEnvironment build() {
        KeycloakRunnerConfig keycloakConfig = buildKeycloakConfig();
        SpringAppRunnerConfig appConfig = WidgetAppRunnerConfig.builder()
                .appPort(findAvailableTcpPort())
                .authIssuerUrl(keycloakConfig.getIssuerUrl())
                .build();
        return WidgetAppTestEnvironment.builder()
                .appConfig(appConfig)
                .keycloakConfig(keycloakConfig)
                .build();
    }

    private static KeycloakRunnerConfig buildKeycloakConfig() {
        return KeycloakRunnerConfig.builder()
                .port(findAvailableTcpPort())
                .realm("demo-local-realm")
                .build();
    }
}
