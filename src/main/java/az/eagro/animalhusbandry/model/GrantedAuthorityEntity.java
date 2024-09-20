package az.eagro.animalhusbandry.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GrantedAuthority")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GrantedAuthorityEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "GrantedAuthorityId", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private UserRole role;

}
