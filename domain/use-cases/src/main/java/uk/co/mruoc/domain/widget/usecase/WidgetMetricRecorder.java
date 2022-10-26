package uk.co.mruoc.domain.widget.usecase;

import uk.co.mruoc.domain.widget.entity.Widget;

public interface WidgetMetricRecorder {

    void recordCreated(Widget widget);
}
