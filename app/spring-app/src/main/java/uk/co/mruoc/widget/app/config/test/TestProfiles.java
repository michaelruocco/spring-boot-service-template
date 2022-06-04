package uk.co.mruoc.widget.app.config.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ConditionContext;

@RequiredArgsConstructor
public class TestProfiles {

    private final Collection<String> profiles;

    public TestProfiles() {
        this(defaultTestProfiles());
    }

    public boolean containsTestProfile(ConditionContext context) {
        Collection<String> activeProfiles = toActiveProfiles(context);
        return profiles.stream().anyMatch(activeProfiles::contains);
    }

    public boolean doesNotContainTestProfile(ConditionContext context) {
        return !containsTestProfile(context);
    }

    private static Collection<String> toActiveProfiles(ConditionContext context) {
        return Arrays.asList(context.getEnvironment().getActiveProfiles());
    }

    private static Collection<String> defaultTestProfiles() {
        return Collections.singleton("local");
    }
}
