package uk.co.mruoc.widget.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.domain.widget.usecase.WidgetRepository;
import uk.co.mruoc.widget.repository.InMemoryWidgetRepository;

@Configuration
public class RepositoryConfig {

    @Bean
    public WidgetRepository widgetRepository() {
        return new InMemoryWidgetRepository();
    }
}
