package uk.co.mruoc.widget.app.config.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.domain.widget.usecase.WidgetRepository;
import uk.co.mruoc.domain.widget.usecase.test.OverridableRandomUuidSupplier;
import uk.co.mruoc.domain.widget.usecase.test.WidgetDeleter;
import uk.co.mruoc.test.clock.OverridableClock;
import uk.co.mruoc.widget.app.rest.test.RestTestApiDelegate;

@Conditional(TestEndpointsEnabledCondition.class)
@Configuration
public class ApplicationTestConfig {

    @Bean
    public OverridableRandomUuidSupplier overridableRandomUuidSupplier() {
        return new OverridableRandomUuidSupplier();
    }

    @Bean
    public OverridableClock overridableClock() {
        return new OverridableClock();
    }

    @Bean
    public WidgetDeleter widgetDeleter(WidgetRepository repository) {
        return new WidgetDeleter(repository);
    }

    @Bean
    public RestTestApiDelegate adminApiDelegate(
            OverridableRandomUuidSupplier uuidSupplier, OverridableClock clock, WidgetDeleter widgetDeleter) {
        return RestTestApiDelegate.builder()
                .uuidSupplier(uuidSupplier)
                .clock(clock)
                .widgetDeleter(widgetDeleter)
                .build();
    }
}
