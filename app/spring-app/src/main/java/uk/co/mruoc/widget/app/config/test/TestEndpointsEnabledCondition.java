package uk.co.mruoc.widget.app.config.test;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@RequiredArgsConstructor
public class TestEndpointsEnabledCondition implements Condition {

    private final TestProfiles testProfiles;

    public TestEndpointsEnabledCondition() {
        this(new TestProfiles());
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return testProfiles.containsTestProfile(context);
    }
}
