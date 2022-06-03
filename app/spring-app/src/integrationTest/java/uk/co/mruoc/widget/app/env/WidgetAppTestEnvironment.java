package uk.co.mruoc.widget.app.env;

import lombok.Builder;
import uk.co.mruoc.spring.app.runner.AvailablePortFinder;
import uk.co.mruoc.spring.app.runner.SpringAppRunnerConfig;
import uk.co.mruoc.spring.app.runner.SpringAppTestEnvironment;

@Builder
public class WidgetAppTestEnvironment implements SpringAppTestEnvironment {

    private final int appPort;

    public static WidgetAppTestEnvironment build() {
        return WidgetAppTestEnvironment.builder()
                .appPort(AvailablePortFinder.findAvailableTcpPort())
                .build();
    }

    @Override
    public SpringAppRunnerConfig getConfig() {
        return new WidgetAppRunnerConfig(appPort);
    }
}
