package com.enterpriseapplications.springboot.config;

import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.LocalBucketBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class RateLimitInterceptor implements HandlerInterceptor
{
    private final int RATE_LIMIT_HOURS = 500;
    private final int RATE_LIMIT_MINUTES = 500;
    private final int RATE_LIMIT_SECONDS = 500;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createBucket()
    {
        Refill hourRefill = Refill.intervally(RATE_LIMIT_HOURS, Duration.ofHours(1));
        Refill minuteRefill = Refill.intervally(RATE_LIMIT_MINUTES,Duration.ofMinutes(1));
        Refill secondRefill = Refill.intervally(RATE_LIMIT_SECONDS,Duration.ofSeconds(1));

        Bandwidth hourLimit = Bandwidth.classic(RATE_LIMIT_HOURS,hourRefill);
        Bandwidth minuteLimit = Bandwidth.classic(RATE_LIMIT_MINUTES,minuteRefill);
        Bandwidth secondLimit = Bandwidth.classic(RATE_LIMIT_SECONDS,secondRefill);

        LocalBucketBuilder builder = Bucket.builder();
        builder.addLimit(hourLimit);
        builder.addLimit(minuteLimit);
        builder.addLimit(secondLimit);
        return builder.build();
    }

    private Bucket handleBucket(String ipAddress) {
        if(cache.containsKey(ipAddress))
            return this.cache.get(ipAddress);
        Bucket bucket = this.createBucket();
        cache.put(ipAddress,bucket);
        return bucket;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteIPAddress = request.getHeader("X-Forwarded-For");
        if(remoteIPAddress == null || remoteIPAddress.isEmpty())
            remoteIPAddress = request.getRemoteAddr();
        if(remoteIPAddress == null || remoteIPAddress.isEmpty())
            throw new InvalidFormat("error.ratelimiting.noaddress");
        Bucket bucket = this.handleBucket(remoteIPAddress);
        if(!bucket.tryConsume(1))
            throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS);
        return true;
    }
}
