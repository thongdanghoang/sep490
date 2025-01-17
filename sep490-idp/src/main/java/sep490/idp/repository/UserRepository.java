package sep490.idp.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sep490.idp.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    
    boolean existsByEmail(String email);
    
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "select u from UserEntity u join fetch u.permissions " +
        "where LOWER(u.firstName) like LOWER(CONCAT('%', :name, '%')) " +
        "or LOWER(u.lastName) like LOWER(CONCAT('%', :name, '%')) ")
    List<UserEntity> searchByName(@Param("name") String name, Pageable pageable);

    @Query(value = "select count(u.id) from UserEntity u " +
        "where LOWER(u.firstName) like LOWER(CONCAT('%', :name, '%')) " +
        "or LOWER(u.lastName) like LOWER(CONCAT('%', :name, '%'))")
    long countAllByName(@Param("name") String name);
}
