package com.woniuxy.fileter;


import com.woniuxy.utils.ApiConst;
import com.woniuxy.utils.JwtConst;
import com.woniuxy.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.SignatureException;
import java.util.List;
@Component
public class AuthorizationFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> strings = exchange.getRequest().getHeaders().get(JwtConst.JWT_TOKEN);
        String uri = exchange.getRequest().getURI().toString();
        boolean flag = true;
//        ApiConst.list.forEach(url -> {
//            if(uri.contains(url)) {
//                return chain.filter(exchange);
//            }
//        });
        if(uri.contains("/login") || uri.contains("/register") || uri.contains("/showMovie"))
            return chain.filter(exchange);
        if (ObjectUtils.isEmpty(strings)) {
            throw  new RuntimeException("请求中没有携带token");
        }
        try {
            JwtUtil.parseToken(strings.get(0));
        } catch (Exception e) {
           throw new RuntimeException("token验证错误");
        }
        return chain.filter(exchange);
    }
}
