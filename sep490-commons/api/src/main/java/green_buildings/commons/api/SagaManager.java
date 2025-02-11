package green_buildings.commons.api;

import lombok.Getter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class SagaManager {
    
    private final ConcurrentHashMap<String, CompletableFuture<Object>> pendingSagaResponses = new ConcurrentHashMap<>();
    
    /**
     * Waits for a response with the given correlation ID.
     */

}
