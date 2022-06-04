package uk.co.mruoc.spring.app.runner;

import java.time.Duration;

public interface SpringAppRunnerConfig {

    Class<?> getAppClass();

    int getAppPort();

    String[] getAppArgs();

    Duration getAppStartupMaxWait();

    Duration getAppStartupPollInterval();
}
