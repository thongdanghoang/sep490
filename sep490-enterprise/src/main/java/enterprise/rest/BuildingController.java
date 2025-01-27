package enterprise.rest;

import enterprise.dtos.BuildingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BuildingController {
    
    ResponseEntity<List<BuildingDTO>> getEnterpriseBuildings();
    
}
