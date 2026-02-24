package com.emi.Api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public class UserIdHeaderFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		  return exchange.getPrincipal()
	                .cast(JwtAuthenticationToken.class)
	                .map(auth -> auth.getToken().getSubject())
	                .flatMap(userId -> {

	                    ServerHttpRequest mutatedRequest = exchange.getRequest()
	                            .mutate()
	                            .header("X-User-Id", userId)
	                            .build();

	                    return chain.filter(exchange.mutate()
	                            .request(mutatedRequest)
	                            .build());
	                })
	                .switchIfEmpty(chain.filter(exchange));
	}

}
