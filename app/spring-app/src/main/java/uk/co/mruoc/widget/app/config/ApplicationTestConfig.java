package uk.co.mruoc.widget.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.mruoc.domain.widget.usecase.test.OverridableRandomUuidSupplier;
import uk.co.mruoc.widget.app.rest.test.RestTestApiDelegate;

@Profile("local")
@Configuration
public class ApplicationTestConfig {

    @Bean
    public OverridableRandomUuidSupplier overridableRandomUuidSupplier() {
        return new OverridableRandomUuidSupplier();
    }

    @Bean
    public RestTestApiDelegate adminApiDelegate(OverridableRandomUuidSupplier uuidSupplier) {
        return new RestTestApiDelegate(uuidSupplier);
    }
}
