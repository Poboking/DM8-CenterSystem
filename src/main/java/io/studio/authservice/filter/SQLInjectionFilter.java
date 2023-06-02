package io.studio.authservice.filter;

/**
 * @author:poboking
 * @version:1.0
 * @time:2023/5/8 1:37
 */


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.regex.Pattern;

/**过滤登录和注册请求SQL注入
 * @author poboking*/

@WebFilter(filterName = "SQLInjectionFilter", urlPatterns = {"/login", "/register"})
public class SQLInjectionFilter implements Filter {
    private static final Pattern SQL_PATTERN = Pattern.compile(".*(;|'|--).*");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
    }

    @Override
    public void destroy() {
        // 销毁操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (containsSQLInjection(httpRequest)) {
            // 包含 SQL 注入攻击字符串，拒绝请求并返回错误信息
            response.getWriter().write("Error: Detected SQL injection attempt.");
            return;
        }
        // 继续处理请求
        chain.doFilter(request, response);
    }

    private boolean containsSQLInjection(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null && SQL_PATTERN.matcher(queryString).matches()) {
            return true;
        }

        String value;
        for (String key : request.getParameterMap().keySet()) {
            value = request.getParameter(key);
            if (SQL_PATTERN.matcher(value).matches()) {
                return true;
            }
        }

        return false;
    }
}
