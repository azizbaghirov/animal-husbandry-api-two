package az.eagro.animalhusbandry.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "Operator")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorEntity {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "OperatorId", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Column(name = "PersonalIdentificationNumber", nullable = false, updatable = false, unique = true)
    private String personalIdentificationNumber;

    @Column(name = "Name", nullable = false, updatable = false)
    private String name;

    @Column(name = "Surname", nullable = false, updatable = false)
    private String surname;

    @Column(name = "Patronymic", nullable = false, updatable = false)
    private String patronymic;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "OperatorId", nullable = false)
    private Set<GrantedAuthorityEntity> grantedAuthorities = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "OperatorRegion",
            joinColumns = @JoinColumn(name = "OperatorId"),
            inverseJoinColumns = @JoinColumn(name = "RegionId"))
    private Set<RegionEntity> regions = new HashSet<>();

}