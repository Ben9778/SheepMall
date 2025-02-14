package com.blackfiresoft.sheepmall.interceptor;

import com.blackfiresoft.sheepmall.result.ResultEntity;
import com.blackfiresoft.sheepmall.result.ResultEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 管理员登录权限校验拦截器
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object admin = request.getSession().getAttribute("admin");
            if (admin == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(mapper.writeValueAsString(ResultEntity.fail(ResultEnum.UNAUTHORIZED)));
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("An error occurred while processing request:{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(mapper.writeValueAsString(ResultEntity.fail(ResultEnum.AUTH_INTERCEPTOR_ERROR)));
            return false;
        }
    }
}


