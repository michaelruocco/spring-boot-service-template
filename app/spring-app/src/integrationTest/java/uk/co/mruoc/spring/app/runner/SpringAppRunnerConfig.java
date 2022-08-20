package uk.co.mruoc.spring.app.runner;

import java.time.Duration;

public interface SpringAppRunnerConfig {

    Class<?> getAppClass();

    int getPort();

    String[] getArgs();

    Duration getStartupMaxWait();

    Duration getStartupPollInterval();
}
