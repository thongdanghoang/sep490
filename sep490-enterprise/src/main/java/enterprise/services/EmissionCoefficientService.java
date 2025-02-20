package enterprise.services;

import org.springframework.web.multipart.MultipartFile;

public interface EmissionCoefficientService {
    void uploadCoefficientExcel(MultipartFile file);
}
