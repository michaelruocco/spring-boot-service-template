package uk.co.mruoc.service.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.domain.repository.InMemoryWidgetRepository;
import uk.co.mruoc.domain.usecase.WidgetRepository;

@Configuration
public class RepositoryConfig {

    @Bean
    public WidgetRepository widgetRepository() {
        return new InMemoryWidgetRepository();
    }
}
