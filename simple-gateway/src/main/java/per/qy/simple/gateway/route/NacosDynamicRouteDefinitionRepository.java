package per.qy.simple.gateway.route;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * nacos动态路由
 *
 * @author : QY
 * @date : 2022/5/23
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "simple.gateway.route.dynamic", havingValue = "true")
public class NacosDynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    private final Map<String, RouteDefinition> routes = new LinkedHashMap<>();
    private static final String DATA_ID = "gateway-routes.yml";
    private final String groupId;
    private final ConfigService configService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public NacosDynamicRouteDefinitionRepository(NacosConfigProperties nacosConfigProperties) {
        this.groupId = nacosConfigProperties.getGroup();
        this.configService = new NacosConfigManager(nacosConfigProperties).getConfigService();
    }

    @PostConstruct
    public void listener() {
        try {
            // 初始化路由
            String content = configService.getConfig(DATA_ID, groupId, 5000);
            refreshRoutes(content);
            // 监听器
            configService.addListener(DATA_ID, groupId,
                    new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            // 监听刷新路由
                            refreshRoutes(configInfo);
                            // 发布事件触发路由更新调用 getRouteDefinitions
                            applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
                        }
                    });
        } catch (NacosException e) {
            log.error("nacos error", e);
        }
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private void refreshRoutes(String content) {
        routes.clear();
        if (StrUtil.isEmpty(content)) {
            return;
        }

        Map<String, List<Map<String, Object>>> map = new Yaml().load(content);
        if (CollUtil.isEmpty(map)) {
            return;
        }

        for (List<Map<String, Object>> list : map.values()) {
            if (CollUtil.isEmpty(list)) {
                continue;
            }
            for (Map<String, Object> value : list) {
                RouteDefinition route = BeanUtil.fillBeanWithMap(value, new RouteDefinition(),
                        CopyOptions.create().setIgnoreError(true).setIgnoreNullValue(true));

                List<Object> predicates = (List<Object>) value.get("predicates");
                for (Object predicate : predicates) {
                    route.getPredicates().add(new PredicateDefinition(predicate.toString()));
                }

                List<Object> filters = (List<Object>) value.get("filters");
                for (Object filter : filters) {
                    route.getFilters().add(new FilterDefinition(filter.toString()));
                }
                routes.put(route.getId(), route);
            }
        }
        log.debug("refresh routes size=" + routes.size());
    }
}
