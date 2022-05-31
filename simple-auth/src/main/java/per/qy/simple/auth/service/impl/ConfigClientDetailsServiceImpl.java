package per.qy.simple.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import per.qy.simple.auth.config.AuthClientsConfig;

import java.util.Map;

/**
 * 客户端信息加载服务
 *
 * @author : QY
 * @date : 2022/5/24
 */
@Component
public class ConfigClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private AuthClientsConfig authClientsConfig;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        Map<String, BaseClientDetails> clients = authClientsConfig.getClients();
        if (CollUtil.isNotEmpty(clients)) {
            BaseClientDetails details = clients.get(clientId);
            if (details != null) {
                details.setClientId(clientId);
                return details;
            }
        }
        throw new NoSuchClientException("No client with requested id: " + clientId);
    }
}
