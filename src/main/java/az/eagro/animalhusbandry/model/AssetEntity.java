package az.eagro.animalhusbandry.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Asset")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssetEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssetId", nullable = false, insertable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "Name", unique = true, insertable = false, updatable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CreatedBy", nullable = false, updatable = false)
    private PhysicalPersonEntity createdBy;

    @CreatedDate
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Instant createdAt;

}
