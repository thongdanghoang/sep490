package greenbuildings.idp.repository;

import greenbuildings.idp.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter {
    private final UserRepository userRepo;
    
    public List<UserEntity> findByIDs(Set<UUID> ids) {
        return userRepo.findByIDs(ids);
    }

}
