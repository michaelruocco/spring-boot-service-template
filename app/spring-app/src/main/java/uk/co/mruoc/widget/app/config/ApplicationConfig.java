package uk.co.mruoc.widget.app.config;

import java.time.Clock;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.domain.widget.usecase.WidgetFactory;
import uk.co.mruoc.domain.widget.usecase.WidgetRepository;
import uk.co.mruoc.domain.widget.usecase.WidgetService;
import uk.co.mruoc.widget.api.WidgetsApiDelegate;
import uk.co.mruoc.widget.app.config.test.TestEndpointsDisabledCondition;
import uk.co.mruoc.widget.app.rest.RestWidgetsApiDelegate;

@Configuration
@ComponentScan("uk.co.mruoc.widget.api")
public class ApplicationConfig {

    @Bean
    @Conditional(TestEndpointsDisabledCondition.class)
    public Supplier<UUID> randomUuidSupplier() {
        return UUID::randomUUID;
    }

    @Bean
    @Conditional(TestEndpointsDisabledCondition.class)
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public WidgetFactory widgetFactory(Supplier<UUID> uuidSupplier, Clock clock) {
        return new WidgetFactory(uuidSupplier, clock);
    }

    @Bean
    public WidgetService widgetService(WidgetFactory factory, WidgetRepository repository) {
        return new WidgetService(factory, repository);
    }

    @Bean
    public WidgetsApiDelegate widgetsApiDelegate(WidgetService service) {
        return new RestWidgetsApiDelegate(service);
    }
}
