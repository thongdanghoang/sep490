package sep490.idp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BaseResource {
    
    @GetMapping("/test")
    public ResponseEntity<String> test(Principal principal) {
        return ResponseEntity.ok(principal.getClass().getName());
    }
    
    @PostMapping("/test")
    public ResponseEntity<String> testPost(Principal principal) {
        return ResponseEntity.ok(principal.getClass().getName());
    }
    
}
