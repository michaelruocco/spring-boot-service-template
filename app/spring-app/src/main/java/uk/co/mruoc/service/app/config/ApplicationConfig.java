package uk.co.mruoc.service.app.config;

import java.time.Clock;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.domain.usecase.WidgetFactory;
import uk.co.mruoc.domain.usecase.WidgetRepository;
import uk.co.mruoc.domain.usecase.WidgetService;
import uk.co.mruoc.service.api.WidgetsApiDelegate;
import uk.co.mruoc.service.app.rest.RestWidgetsApiDelegate;

@Configuration
@ComponentScan("uk.co.mruoc.service.api")
public class ApplicationConfig {

    @Bean
    public WidgetsApiDelegate widgetsApiDelegate(WidgetService service) {
        return new RestWidgetsApiDelegate(service);
    }

    @Bean
    public WidgetFactory widgetFactory() {
        return new WidgetFactory(UUID::randomUUID, Clock.systemUTC());
    }

    @Bean
    public WidgetService widgetService(WidgetFactory factory, WidgetRepository repository) {
        return new WidgetService(factory, repository);
    }
}
