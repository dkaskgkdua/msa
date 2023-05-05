package com.example.userservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * CircuitBreaker
 * - 장애가 발생하는 서비스에 반복적인 호출이 되지 못하게 차단
 * - 특정 서비스가 정상적으로 동작하지 않을 경우 다른 기능으로 대체 수행 -> 장애 회피
 *
 * */

@Configuration
public class Resilience4JConfig {
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(4) // 4% 확률로 circuitBreaker 열음(default 50)
                .waitDurationInOpenState(Duration.ofMillis(1000)) // CircuitBreaker를 열은 상태로 유지하는 시간 1초(default 60초)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // CircuitBreaker가 닫힐 때 결과 기록하는 유형(카운트 or 시간 기반)
                .slidingWindowSize(2) // CircuitBreakder가 닫힐 때 호출 결과를 기록하는데 사용되는 슬라이딩 창의 크기를 구성(default 100)
                .build();

        // TimeLimiter는 future supplier의 time limit을 정하는 API(default 1초)
        // ex) order-service에서 문제가 생길 시에 요청자인 user-service에서 4초 동안 기다리는데 응답이 없으면 열음
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }
}
