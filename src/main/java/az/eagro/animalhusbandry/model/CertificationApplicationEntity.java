package az.eagro.animalhusbandry.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Table(name = "CertificationApplication")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplicationId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "ApplicationNumber", nullable = false, updatable = false, unique = true)
    private String applicationNumber;

    @ManyToOne
    @JoinColumn(name = "FarmerId", nullable = false, updatable = false)
    private FarmerAccountEntity farmer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmId", nullable = false, updatable = false)
    private BreedingAnimalFarmEntity farm;

    @Enumerated(EnumType.STRING)
    @Column(name = "ApplicationStatus", nullable = false)
    private ApplicationStatus applicationStatus = ApplicationStatus.REGISTERED;

    @Enumerated(EnumType.STRING)
    @Column(name = "InitialMonitoringStatus", nullable = false)
    private InitialMonitoringStatus initialMonitoringStatus = InitialMonitoringStatus.UNDEFINED;

    @Enumerated(EnumType.STRING)
    @Column(name = "FinalMonitoringStatus", nullable = false)
    private FinalMonitoringStatus finalMonitoringStatus = FinalMonitoringStatus.UNDEFINED;

    @ManyToOne
    @JoinColumn(name = "FarmTypeId", updatable = false)
    private BreedingAnimalFarmTypeEntity farmType;

    @Transient
    private Set<AssetEntity> assets = new HashSet<>();

    @Column(name = "VeterinaryContractFilePath", nullable = false, updatable = false)
    private String filePath;

    @Column(name = "Note")
    private String note;

    @CreatedBy
    @ManyToOne(optional = false)
    @JoinColumn(name = "CreatedBy", nullable = false, updatable = false)
    private PhysicalPersonEntity createdBy;

    @CreatedDate
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "DeletedAt")
    private Instant deletedAt;

    @ElementCollection
    @CollectionTable(name = "ApplicationAnimal",
            joinColumns = @JoinColumn(name = "ApplicationId", referencedColumnName = "ApplicationId"))
    @Column(name = "AnimalId")
    private Set<Integer> animalIds = new HashSet<>();

}
