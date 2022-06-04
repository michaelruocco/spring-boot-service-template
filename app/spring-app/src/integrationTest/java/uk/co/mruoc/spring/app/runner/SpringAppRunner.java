package uk.co.mruoc.spring.app.runner;

import static org.awaitility.Awaitility.await;

import java.util.Arrays;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringAppRunner {

    private static ConfigurableApplicationContext context;

    public static void startAppIfNotStarted(SpringAppRunnerConfig config) {
        if (isRunning()) {
            log.info("app already running so not starting");
            return;
        }
        Class<?> appClass = config.getAppClass();
        String[] args = config.getAppArgs();
        log.info("starting application {} with args {}", appClass, Arrays.toString(args));
        context = SpringApplication.run(appClass, args);
        log.info("waiting for app startup to complete...");
        waitForStartupToComplete(config);
    }

    public static void shutdownAppIfRunning() {
        if (!isRunning()) {
            log.info("app not running so cannot shut down");
            return;
        }
        log.info("shutting down app");
        SpringApplication.exit(context, () -> 0);
        context = null;
    }

    private static boolean isRunning() {
        return !Objects.isNull(context);
    }

    private static void waitForStartupToComplete(SpringAppRunnerConfig config) {
        await().dontCatchUncaughtExceptions()
                .atMost(config.getAppStartupMaxWait())
                .pollInterval(config.getAppStartupPollInterval())
                .until(PortReady.local(config.getAppPort()));
    }
}
