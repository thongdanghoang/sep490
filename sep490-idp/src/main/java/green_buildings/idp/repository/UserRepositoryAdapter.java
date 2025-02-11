package green_buildings.idp.repository;

import green_buildings.idp.entity.UserEntity;
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
