package az.eagro.animalhusbandry.model;

import az.eagro.animalhusbandry.business.audit.AuditRevisionListener;
import java.util.Date;
import java.util.UUID;
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
import org.hibernate.annotations.Type;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Setter
@Getter
@Entity
@Table(name = "RevInfo")
@RevisionEntity(AuditRevisionListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "REV")
    private Integer id;

    @RevisionTimestamp
    @Column(name = "RevisionTimestamp")
    private Date revisionTimestamp;

    @Type(type = "uuid-char")
    @Column(name = "CreatedBy", nullable = false)
    private UUID userId;
}