package greenbuildings.enterprise.adapters.geocoding;

import greenbuildings.enterprise.dtos.AddressSuggestionDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeocodingAdapter {
    
    private final RestTemplate restTemplate;
    private final GeocodingMapper mapper;
    
    @Value("${geocoding.key}")
    private String key;
    
    @Value("${geocoding.url}")
    private String url;
    
    public AddressSuggestionDTO reverse(double latitude, double longitude) {
        var requestUrl = StringSubstitutor
                .replace("${url}?lat=${lat}&lon=${lon}&api_key=${key}",
                         Map.of("url", this.url,
                                "lat", latitude,
                                "lon", longitude,
                                "key", key));
        var response = restTemplate.getForObject(requestUrl, GeocodingReverseResponse.class);
        return mapper.toAddressSuggestion(response);
    }
}
