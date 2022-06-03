package uk.co.mruoc.domain.widget.usecase;

import java.util.UUID;

public class WidgetNotFoundException extends RuntimeException {

    public WidgetNotFoundException(UUID id) {
        super(id.toString());
    }
}
