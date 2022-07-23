package per.qy.simple.gateway.filter;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import per.qy.simple.common.base.constant.SimpleConstant;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 网关过滤器
 *
 * @author : QY
 * @date : 2022/5/27
 */
@Slf4j
@Component
public class GatewayGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 请求头设置requestId
        String requestId;
        List<String> requestIds = request.getHeaders().get(SimpleConstant.HEADER_REQUEST_ID);
        if (requestIds == null || requestIds.size() != 1) {
            requestId = IdUtil.fastSimpleUUID();
            request = request.mutate()
                    .header(SimpleConstant.HEADER_REQUEST_ID, requestId).build();
            exchange = exchange.mutate().request(request).build();
        } else {
            requestId = requestIds.get(0);
        }
        MDC.put(SimpleConstant.HEADER_REQUEST_ID, requestId);

        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        log.info("remote={},path={},route={}", request.getRemoteAddress(),
                request.getPath().value(), route);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return SecurityWebFiltersOrder.FIRST.getOrder();
    }
}
