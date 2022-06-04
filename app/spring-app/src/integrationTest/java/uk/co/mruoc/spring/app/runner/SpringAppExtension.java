package uk.co.mruoc.spring.app.runner;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;

@RequiredArgsConstructor
public class SpringAppExtension implements BeforeAllCallback, AfterAllCallback, CloseableResource {

    private final SpringAppTestEnvironment env;

    @Override
    public void beforeAll(ExtensionContext context) {
        SpringAppRunner.startAppIfNotStarted(env.getConfig());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        shutdown();
    }

    @Override
    public void close() {
        shutdown();
    }

    private void shutdown() {
        SpringAppRunner.shutdownAppIfRunning();
    }
}
