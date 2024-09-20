package az.eagro.animalhusbandry.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "AdministrativeArea")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministrativeAreaEntity {

    @Id
    @Column(name = "AdministrativeAreaId", nullable = false, unique = true)
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Code", nullable = false, unique = true)
    private String code;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "RegionId", nullable = false)
    private RegionEntity region;

}
