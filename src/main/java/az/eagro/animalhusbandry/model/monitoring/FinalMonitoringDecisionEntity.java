package az.eagro.animalhusbandry.model.monitoring;

import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.OperatorEntity;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Where(clause = "InvalidatedAt IS NULL")
@Table(name = "FinalMonitoringDecision")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@EntityListeners(AuditingEntityListener.class)
public class FinalMonitoringDecisionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FinalMonitoringDecisionId", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "CertificationApplicationId", nullable = false, updatable = false)
    @NotAudited
    private Integer certificationApplicationId;

    @Column(name = "Certified", nullable = false)
    private Boolean certified;

    @Column(name = "Justification", nullable = false)
    private String justification;

    // todo: @CreatedByOperator
    @ManyToOne(optional = false)
    @JoinColumn(name = "CreatedBy", nullable = false, updatable = false)
    @NotAudited
    private OperatorEntity createdBy;

    @CreatedDate
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @NotAudited
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "InvalidatedBy")
    @NotAudited
    private OperatorEntity invalidatedBy;

    @Column(name = "InvalidatedAt")
    @NotAudited
    private Instant invalidatedAt;

    @Column(name = "RegisteredAt")
    private Instant registeredAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "FinalMonitoringDecisionFilePathContainer",
            joinColumns = @JoinColumn(name = "FinalMonitoringDecisionId"),
            inverseJoinColumns = @JoinColumn(name = "FileId"))
    @NotAudited
    private List<FileEntity> files = new ArrayList<>();

}
