package uk.co.mruoc.service.app.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.domain.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.entity.Widget;
import uk.co.mruoc.domain.entity.WidgetsPage;
import uk.co.mruoc.domain.usecase.WidgetService;
import uk.co.mruoc.service.api.WidgetsApiController;
import uk.co.mruoc.service.api.WidgetsApiDelegate;
import uk.co.mruoc.service.api.model.ApiCreateWidgetRequest;
import uk.co.mruoc.service.api.model.ApiQueryWidgetsPageRequest;
import uk.co.mruoc.service.api.model.ApiWidget;
import uk.co.mruoc.service.api.model.ApiWidgetsPage;

@RequiredArgsConstructor
public class RestWidgetsApiDelegate implements WidgetsApiDelegate {

    private final WidgetService service;
    private final ApiTypeConverter converter;

    public RestWidgetsApiDelegate(WidgetService service) {
        this(service, new ApiTypeConverter());
    }

    @Override
    public ResponseEntity<ApiWidget> createWidget(ApiCreateWidgetRequest apiRequest) {
        CreateWidgetRequest request = converter.toCreateRequest(apiRequest);
        Widget widget = service.create(request);
        return ResponseEntity.created(toCreatedLink(widget.getId())).body(converter.toApiWidget(widget));
    }

    @Override
    public ResponseEntity<ApiWidget> getWidgetById(String apiId) {
        UUID id = UUID.fromString(apiId);
        Widget widget = service.getById(id);
        return ResponseEntity.ok(converter.toApiWidget(widget));
    }

    @Override
    public ResponseEntity<ApiWidgetsPage> queryWidgetsPage(ApiQueryWidgetsPageRequest apiRequest) {
        QueryWidgetsPageRequest request = converter.toWidgetsPageRequest(apiRequest);
        WidgetsPage page = service.getWidgetsPage(request);
        return ResponseEntity.ok(converter.toApiWidgetsPage(page));
    }

    private static URI toCreatedLink(UUID id) {
        String link = linkTo(methodOn(WidgetsApiController.class).getWidgetById(id.toString()))
                .toString();
        return URI.create(workAroundFixLink(link));
    }

    private static String workAroundFixLink(String link) {
        return link.replace("${openapi.springBootTemplate.base-path/", "").replace("}", "");
    }
}
