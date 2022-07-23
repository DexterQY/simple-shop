package per.qy.simple.common.core.init;

import org.slf4j.TtlMDCAdapter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化替换MDCAdapter
 *
 * @author : QY
 * @date : 2022/7/23
 */
@Component
public class MdcAdapterInit implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        TtlMDCAdapter.replaceMDCAdapter();
    }
}
