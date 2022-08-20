package uk.co.mruoc.widget.app.env;

import java.time.Duration;
import lombok.Builder;
import uk.co.mruoc.spring.app.runner.SpringAppRunnerConfig;
import uk.co.mruoc.widget.app.SpringWidgetApp;

@Builder
public class WidgetAppRunnerConfig implements SpringAppRunnerConfig {

    private final int appPort;
    private final String authIssuerUrl;

    @Override
    public Class<?> getAppClass() {
        return SpringWidgetApp.class;
    }

    @Override
    public int getPort() {
        return appPort;
    }

    @Override
    public String[] getArgs() {
        return new String[] {
            "--spring.profiles.active=local",
            String.format("--server.port=%d", appPort),
            String.format("--auth.issuer.url=%s", authIssuerUrl)
        };
    }

    @Override
    public Duration getStartupMaxWait() {
        return Duration.ofSeconds(5);
    }

    @Override
    public Duration getStartupPollInterval() {
        return Duration.ofMillis(250);
    }
}
