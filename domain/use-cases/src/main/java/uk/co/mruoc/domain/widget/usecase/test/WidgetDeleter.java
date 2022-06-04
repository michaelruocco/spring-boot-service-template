package uk.co.mruoc.domain.widget.usecase.test;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.domain.widget.usecase.WidgetRepository;

@RequiredArgsConstructor
public class WidgetDeleter {

    private final WidgetRepository repository;

    public void deleteAll() {
        repository.deleteAll();
    }
}
