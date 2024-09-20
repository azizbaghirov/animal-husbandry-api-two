package az.eagro.animalhusbandry.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "BreedingAnimalFarm")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreedingAnimalFarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FarmId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "CurrentAddress", updatable = false)
    @NotAudited
    private String currentAddress;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmerId", nullable = false, updatable = false)
    @NotAudited
    private FarmerAccountEntity farmerAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmTypeId", nullable = false, updatable = false)
    @NotAudited
    private BreedingAnimalFarmTypeEntity farmType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "FarmSpecializationId", updatable = false)
    @NotAudited
    private BreedingAnimalFarmSpecializationEntity farmSpecialization;

    @Enumerated(EnumType.STRING)
    @Column(name = "BreedingAnimalFarmActivityType", nullable = false, updatable = false)
    @NotAudited
    private BreedingAnimalFarmActivityType activityType;

    @ManyToOne(optional = false)  //cascade = CascadeType.REFRESH
    @JoinColumn(name = "AdministrativeAreaId", nullable = false)
    @NotAudited
    private AdministrativeAreaEntity administrativeArea;

    @ManyToOne(optional = false)
    @JoinColumn(name = "RegionId", nullable = false)
    @NotAudited
    private RegionEntity region;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "FarmId", nullable = false)
    @NotAudited
    private Set<FieldDocumentEntity> fieldDocuments = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "AnimalTypeId")
    @NotAudited
    private AnimalTypeEntity animalType;

    @Column(name = "Deletable", nullable = false)
    @NotAudited
    private Boolean deletable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AnimalSortId", nullable = false)
    @NotAudited
    private AnimalSortEntity animalSort;

    @CreatedBy
    @ManyToOne(optional = false)
    @JoinColumn(name = "CreatedBy", nullable = false)
    @NotAudited
    private PhysicalPersonEntity createdBy;

    @CreatedDate
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @NotAudited
    private Instant createdAt;

    @Column(name = "DeletedAt")
    @NotAudited
    private Instant deletedAt;

}
