package sep490.idp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "authenticator")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserAuthenticator extends AbstractBaseEntity{

    @Column(name = "credential_id", nullable = false, unique = true)
    private String credentialId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "credential_name")
    private String credentialName;

    @Column(name = "attestation_object", nullable = false)
    private byte[] attestationObject;

    @Column(name = "sign_count")
    private long signCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof UserAuthenticator that)) { return false; }
        if (!super.equals(o)) { return false; }
        return Objects.equals(credentialId, that.credentialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), credentialId);
    }
}
