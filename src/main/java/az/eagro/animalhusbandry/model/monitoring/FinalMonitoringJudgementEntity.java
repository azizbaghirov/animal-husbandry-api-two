package az.eagro.animalhusbandry.model.monitoring;

import az.eagro.animalhusbandry.model.EntityRemovalSqlStatements;
import az.eagro.animalhusbandry.model.OperatorEntity;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "FinalMonitoringJudgement")
@SQLDelete(sql = EntityRemovalSqlStatements.FINAL_MONITORING_JUDGEMENT_REMOVAL_SQL_STATEMENT)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Where(clause = "DeletedAt IS NULL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FinalMonitoringJudgementEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "FinalMonitoringJudgementId", nullable = false, unique = true, updatable = false)
    private UUID id;

    @JoinColumn(name = "CertificationApplicationId", nullable = false, updatable = false)
    @NotAudited
    private Integer certificationApplicationId;

    @Column(name = "Compliant", nullable = false)
    private Boolean compliant;

    @Column(name = "Justification", nullable = false)
    private String justification;

    // todo: @CreatedByOperator
    @ManyToOne(optional = false)
    @JoinColumn(name = "CreatedBy", nullable = false, updatable = false)
    @NotAudited
    private OperatorEntity createdBy;

    @CreatedDate
    @Column(name = "CreatedAt")
    @NotAudited
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "ModifiedAt")
    @NotAudited
    private Instant modifiedAt;

    @Column(name = "DeletedAt")
    @NotAudited
    private Instant deletedAt;

    @Column(name = "InvalidatedAt", updatable = false)
    @NotAudited
    private Instant invalidatedAt;

}
