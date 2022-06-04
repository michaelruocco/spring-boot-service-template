package uk.co.mruoc.widget.app.env;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.spring.app.runner.SpringAppRunnerConfig;
import uk.co.mruoc.widget.app.SpringWidgetApp;

@RequiredArgsConstructor
public class WidgetAppRunnerConfig implements SpringAppRunnerConfig {

    private final int appPort;

    @Override
    public Class<?> getAppClass() {
        return SpringWidgetApp.class;
    }

    @Override
    public int getAppPort() {
        return appPort;
    }

    @Override
    public String[] getAppArgs() {
        return new String[] {String.format("--server.port=%d", appPort), "--spring.profiles.active=local"};
    }

    @Override
    public Duration getAppStartupMaxWait() {
        return Duration.ofSeconds(5);
    }

    @Override
    public Duration getAppStartupPollInterval() {
        return Duration.ofMillis(250);
    }
}
