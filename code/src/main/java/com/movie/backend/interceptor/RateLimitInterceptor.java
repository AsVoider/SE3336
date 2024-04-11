package com.movie.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.curator.shaded.com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final RateLimiter rateLimiter = RateLimiter.create(10);
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        boolean flag;
        try {
            flag = rateLimiter.tryAcquire();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return false;
        }
        if (!flag) {
            PrintWriter writer = response.getWriter();
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            writer.write("too many requests");
            return false;
        }
        return true;
    }
}
