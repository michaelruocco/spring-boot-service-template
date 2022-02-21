package uk.co.mruoc.domain.usecase;

import java.util.UUID;

public class WidgetNotFoundException extends RuntimeException {

    public WidgetNotFoundException(UUID id) {
        super(id.toString());
    }
}
