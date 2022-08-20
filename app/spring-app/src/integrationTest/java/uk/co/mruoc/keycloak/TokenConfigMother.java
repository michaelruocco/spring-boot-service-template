package uk.co.mruoc.keycloak;

import com.tngtech.keycloakmock.api.TokenConfig;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenConfigMother {

    public static TokenConfig demoAdmin() {
        return TokenConfig.aTokenConfig()
                .withSubject("9097c371-a639-4239-b4ec-9fadfb91c99d")
                .withName("demo admin")
                .withEmail("demo.admin@hotmail.com")
                .withRealmRole("user-role")
                .withClaim("groups", Collections.singleton("admin"))
                .build();
    }

    public static TokenConfig demoUser() {
        return TokenConfig.aTokenConfig()
                .withSubject("9097c371-a639-4239-b4ec-9fadfb91c99d")
                .withName("demo user")
                .withEmail("demo.user@hotmail.com")
                .withRealmRole("user-role")
                .withClaim("groups", Collections.singleton("user"))
                .build();
    }
}
