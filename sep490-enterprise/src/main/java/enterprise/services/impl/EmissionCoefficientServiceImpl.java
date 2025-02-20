package enterprise.services.impl;

import commons.springfw.impl.utils.ExcelUtils;
import enterprise.services.EmissionCoefficientService;
import green_buildings.commons.api.exceptions.client.RestClientBusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Throwable.class)
public class EmissionCoefficientServiceImpl implements EmissionCoefficientService {
    
    
    @Override
    public void uploadCoefficientExcel(MultipartFile file) {
        if (!ExcelUtils.validateExcel(file)) {
            throw new RestClientBusinessException("file", "validation.uploadFile", null);
        }
        // Read from file to list of TempEmissionCoefficient
    }
}
