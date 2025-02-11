package green_buildings.idp.aop;

import commons.springfw.impl.utils.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import green_buildings.idp.entity.UserEntity;

import java.util.Objects;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class UserRepositoryAdvice {
    
    @PersistenceContext
    private final EntityManager em;
    
    @Pointcut("execution(* green_buildings.idp.repository.UserRepositoryAdapter.*(..))")
    public void userRepositoryMethods() {
        // Pointcut for methods in UserRepository have to filter users base on their enterprise
    }
    
    @Around(value = "userRepositoryMethods()")
    public Object applyEnterpriseFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        Session session = em.unwrap(Session.class);
        boolean hasSession = Objects.nonNull(session);
        if (hasSession) {
            Filter filter = session.enableFilter(UserEntity.BELONG_ENTERPRISE_FILTER);
            UUID enterpriseId = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
            filter.setParameter(UserEntity.BELONG_ENTERPRISE_PARAM, enterpriseId);
        }
        
        try {
            return joinPoint.proceed();
        } finally {
            if (hasSession) {
                session.disableFilter(UserEntity.BELONG_ENTERPRISE_FILTER);
            }
        }
    }
    
}
