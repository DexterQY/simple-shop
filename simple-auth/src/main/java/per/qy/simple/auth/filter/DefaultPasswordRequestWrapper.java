package per.qy.simple.auth.filter;

import per.qy.simple.auth.constant.AuthConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认密码参数包装
 *
 * @author : QY
 * @date : 2022/5/30
 */
public class DefaultPasswordRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> parameterMap = new HashMap<>();

    public DefaultPasswordRequestWrapper(HttpServletRequest request) {
        super(request);
        parameterMap.putAll(request.getParameterMap());
        parameterMap.put(AuthConstant.PWD_NAME, new String[]{AuthConstant.DEFAULT_PWD});
    }

    @Override
    public String getParameter(String name) {
        String[] values = parameterMap.get(name);
        if (values == null) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }
}
