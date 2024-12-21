package sep490.idp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authenticator")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserAuthenticator {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private UserEntity user;

    private String credentialsName;

    private byte[] attestationObject;

    private long signCount;
}
