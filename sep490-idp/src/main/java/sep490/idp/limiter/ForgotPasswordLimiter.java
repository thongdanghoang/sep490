package sep490.idp.limiter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.github.bucket4j.local.SynchronizationStrategy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ForgotPasswordLimiter {
    
    private final Cache<String, Bucket> submitEmailBuckets = Caffeine.newBuilder()
                                                                     .expireAfterWrite(1, TimeUnit.HOURS)
                                                                     .build();
    private final Cache<String, Bucket> submitOTPBuckets = Caffeine.newBuilder()
                                                                     .expireAfterWrite(1, TimeUnit.HOURS)
                                                                     .build();
    
    
    
    public boolean isSubmitEmailPermitted(String email) {
        Bucket bucket = submitEmailBuckets.get(email, e -> this.createSubmitEmailBucket());
        return Objects.requireNonNull(bucket).tryConsume(1);
    }
    
    private Bucket createSubmitEmailBucket() {
        Bandwidth limit = Bandwidth.classic(
                3,
                Refill.intervally(3, Duration.ofMinutes(10)));
        return Bucket.builder()
                     .addLimit(limit)
                     .withSynchronizationStrategy(SynchronizationStrategy.SYNCHRONIZED)
                     .build();
    }
    
    public boolean isSubmitOTPPermitted(String email) {
        Bucket bucket = submitOTPBuckets.get(email, e -> this.createSubmitOTPBucket());
        return Objects.requireNonNull(bucket).tryConsume(1);
    }
    
    public boolean isSubmitOTPAvailable(String email) {
        Bucket bucket = submitOTPBuckets.getIfPresent(email);
        return bucket == null || bucket.getAvailableTokens() > 0;
    }
    
    private Bucket createSubmitOTPBucket() {
        Bandwidth limit = Bandwidth.classic(
                3,
                Refill.intervally(3, Duration.ofMinutes(15)));
        return Bucket.builder()
                     .addLimit(limit)
                     .withSynchronizationStrategy(SynchronizationStrategy.LOCK_FREE)
                     .build();
    }
}
