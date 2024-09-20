package az.eagro.animalhusbandry.model.certification;

import az.eagro.animalhusbandry.model.AdministrativeAreaEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmActivityType;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmSpecializationEntity;
import az.eagro.animalhusbandry.model.BreedingAnimalFarmTypeEntity;
import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.RegionEntity;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.hibernate.envers.NotAudited;

@Getter
@Setter
@Entity
@Where(clause = "InvalidatedAt IS NULL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BreedingAnimalFarmCertificate")
public class BreedingAnimalFarmCertificateEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "BreedingAnimalFarmCertificateId", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "CertificationApplicationId", nullable = false, updatable = false)
    private Integer certificationApplicationId;

    @Column(name = "FinalMonitoringDecisionId", nullable = false, updatable = false)
    private UUID finalMonitoringDecisionId;

    @Column(name = "RegistrationNumber", nullable = false, updatable = false)
    private String registrationNumber;

    @Column(name = "ApplicationNumber", nullable = false, updatable = false)
    private String applicationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "BreedingAnimalFarmActivityType", nullable = false, updatable = false)
    @NotAudited
    private BreedingAnimalFarmActivityType activityType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmerAccountId", nullable = false, updatable = false)
    private FarmerAccountEntity farmerAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BreedingAnimalFarmTypeId", nullable = false, updatable = false)
    private BreedingAnimalFarmTypeEntity farmType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BreedingAnimalFarmSpecializationId", nullable = false, updatable = false)
    private BreedingAnimalFarmSpecializationEntity farmSpecialization;

    @ManyToOne(optional = false)
    @JoinColumn(name = "RegionId", nullable = false, updatable = false)
    private RegionEntity region;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AdministrativeAreaId", nullable = false, updatable = false)
    private AdministrativeAreaEntity administrativeArea;

    @Column(name = "CertificationDate", nullable = false, updatable = false)
    private LocalDate certificationDate;

    @Column(name = "ExpirationDate")
    private LocalDate expirationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RegistryFileId")
    private FileEntity registryFile;

    @Column(name = "InvalidatedAt")
    private Instant invalidatedAt;

}
