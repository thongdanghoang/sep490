package greenbuildings.enterprise.rest;

import greenbuildings.enterprise.adapters.geocoding.GeocodingAdapter;
import greenbuildings.enterprise.dtos.AddressSuggestionDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geocoding")
@RequiredArgsConstructor
public class GeocodingResource {
    private final GeocodingAdapter geocodingAdapter;
    
    @GetMapping("/reverse")
    public ResponseEntity<AddressSuggestionDTO> reverse(@RequestParam @NotNull Double latitude,
                                                        @RequestParam @NotNull Double longitude) {
        return ResponseEntity.ok(geocodingAdapter.reverse(latitude, longitude));
    }
    
}
