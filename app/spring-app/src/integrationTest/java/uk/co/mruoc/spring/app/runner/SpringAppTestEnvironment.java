package uk.co.mruoc.spring.app.runner;

public interface SpringAppTestEnvironment {

    SpringAppRunnerConfig getConfig();

    default int getAppPort() {
        return getConfig().getAppPort();
    }
}
