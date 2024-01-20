package com.odk.template.api.interceptor;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.template.util.constext.SessionHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * SessionInterceptor
 * 定义拦截器
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
public class SessionInterceptor implements AsyncHandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        //权限放开
        if (method.getAnnotation(NoLogigCondition.class) != null) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            logger.error("Token required: {}", request.getRequestURI());
            throw new BizException(BizErrorCode.TOKEN_MISSING);
        }
        AssertUtil.notNull(SessionHolder.checkUserSession(token), BizErrorCode.TOKEN_EXPIRED);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 在请求被处理之后执行的操作，相当于拦截请求的后置处理
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // 在请求完成后执行的操作，包括在视图渲染之后执行（即在DispatcherServlet做了所有事情后）

        // 你可以在这里进行一些清理工作，例如释放资源等
    }
}
