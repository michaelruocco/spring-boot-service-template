package uk.co.mruoc.widget.app.rest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import javax.money.MonetaryAmount;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import uk.co.mruoc.domain.widget.entity.CreateWidgetRequest;
import uk.co.mruoc.domain.widget.entity.PageParams;
import uk.co.mruoc.domain.widget.entity.QueryWidgetsPageRequest;
import uk.co.mruoc.domain.widget.entity.Widget;
import uk.co.mruoc.domain.widget.entity.WidgetsPage;
import uk.co.mruoc.widget.api.model.ApiCreateWidgetRequest;
import uk.co.mruoc.widget.api.model.ApiMonetaryAmount;
import uk.co.mruoc.widget.api.model.ApiPageParams;
import uk.co.mruoc.widget.api.model.ApiQueryWidgetsPageRequest;
import uk.co.mruoc.widget.api.model.ApiWidget;
import uk.co.mruoc.widget.api.model.ApiWidgetsPage;

@RequiredArgsConstructor
public class ApiTypeConverter {

    private final ZoneOffset offset;

    public ApiTypeConverter() {
        this(ZoneOffset.UTC);
    }

    public CreateWidgetRequest toCreateRequest(ApiCreateWidgetRequest apiRequest) {
        return CreateWidgetRequest.builder()
                .description(apiRequest.getDescription())
                .cost(toMonetaryAmount(apiRequest.getCost()))
                .build();
    }

    public ApiWidget toApiWidget(Widget widget) {
        return new ApiWidget()
                .id(widget.getId())
                .description(widget.getDescription())
                .cost(toApiMonetaryAmount(widget.getCost()))
                .createdAt(toOffset(widget.getCreatedAt()));
    }

    public QueryWidgetsPageRequest toWidgetsPageRequest(ApiQueryWidgetsPageRequest apiRequest) {
        return new QueryWidgetsPageRequest(toPageParams(apiRequest.getPageParams()));
    }

    public ApiWidgetsPage toApiWidgetsPage(WidgetsPage page) {
        return new ApiWidgetsPage()
                .request(toApiWidgetsPageRequest(page.getRequest()))
                .totalCount(page.getTotalCount())
                .widgets(toApiWidgets(page.getWidgets()));
    }

    private ApiQueryWidgetsPageRequest toApiWidgetsPageRequest(QueryWidgetsPageRequest request) {
        return new ApiQueryWidgetsPageRequest().pageParams(toApiPageParams(request.getPageParams()));
    }

    private List<ApiWidget> toApiWidgets(Collection<Widget> widgets) {
        return widgets.stream().map(this::toApiWidget).toList();
    }

    private PageParams toPageParams(ApiPageParams apiPageParams) {
        return PageParams.builder()
                .limit(apiPageParams.getLimit())
                .offset(apiPageParams.getOffset())
                .build();
    }

    private ApiPageParams toApiPageParams(PageParams pageParams) {
        return new ApiPageParams().limit(pageParams.getLimit()).offset(pageParams.getOffset());
    }

    private MonetaryAmount toMonetaryAmount(ApiMonetaryAmount amount) {
        return Money.of(amount.getValue(), amount.getCurrency());
    }

    private ApiMonetaryAmount toApiMonetaryAmount(MonetaryAmount amount) {
        return new ApiMonetaryAmount()
                .value(amount.getNumber().numberValue(BigDecimal.class))
                .currency(amount.getCurrency().getCurrencyCode());
    }

    private OffsetDateTime toOffset(Instant instant) {
        return instant.atOffset(offset);
    }
}
