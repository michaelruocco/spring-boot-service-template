package uk.co.mruoc.widget.app.config;

import java.time.Clock;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.domain.widget.usecase.WidgetFactory;
import uk.co.mruoc.domain.widget.usecase.WidgetRepository;
import uk.co.mruoc.domain.widget.usecase.WidgetService;
import uk.co.mruoc.widget.api.WidgetsApiDelegate;
import uk.co.mruoc.widget.app.rest.RestWidgetsApiDelegate;

@Configuration
@ComponentScan("uk.co.mruoc.widget.api")
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
