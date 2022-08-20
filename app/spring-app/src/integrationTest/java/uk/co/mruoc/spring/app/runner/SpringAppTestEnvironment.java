package uk.co.mruoc.spring.app.runner;

import uk.co.mruoc.keycloak.KeycloakRunnerConfig;

public interface SpringAppTestEnvironment {

    SpringAppRunnerConfig getAppConfig();

    KeycloakRunnerConfig getKeycloakConfig();

    default int getAppPort() {
        return getAppConfig().getPort();
    }
}
