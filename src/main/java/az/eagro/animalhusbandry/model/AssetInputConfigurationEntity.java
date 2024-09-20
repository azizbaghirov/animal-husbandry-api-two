package az.eagro.animalhusbandry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AssetInputConfiguration")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetInputConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfigurationId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FarmTypeId", nullable = false, insertable = false, updatable = false)
    private BreedingAnimalFarmTypeEntity farmType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "AssetId", nullable = false, insertable = false, updatable = false)
    private AssetEntity asset;

    @ManyToOne
    @JoinColumn(name = "FarmSpecializationId", insertable = false, updatable = false)
    private BreedingAnimalFarmSpecializationEntity farmSpecialization;

    @Column(name = "Mandatory", nullable = false, insertable = false, updatable = false)
    private boolean mandatory;

    @Column(name = "Description", insertable = false, updatable = false)
    private String description;

}
