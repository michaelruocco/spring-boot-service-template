package uk.co.mruoc.widget.app.metric;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.widget.entity.Widget;
import uk.co.mruoc.domain.widget.usecase.WidgetMetricRecorder;

@RequiredArgsConstructor
public class SpringWidgetMetricRecorder implements WidgetMetricRecorder {

    private final MeterRegistry registry;

    @Override
    public void recordCreated(Widget widget) {
        registry.counter("widget-created").increment();
    }
}
