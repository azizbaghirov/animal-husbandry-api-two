package az.eagro.animalhusbandry.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "InitialMonitoringDecision")
@Where(clause = "DeletedAt IS NULL")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class InitialMonitoringDecisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InitialMonitoringDecisionId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "ApplicationId", nullable = false, updatable = false)
    private CertificationApplicationEntity application;

    @Column(name = "Compliant", nullable = false)
    private boolean compliant;

    @Column(name = "Justification")
    private String justification;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "InitialMonitoringDecisionFilePathContainer",
            joinColumns = @JoinColumn(name = "InitialMonitoringDecisionId"),
            inverseJoinColumns = @JoinColumn(name = "FileId"))
    private List<FileEntity> files = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CreatedBy", nullable = false, updatable = false)
    private OperatorEntity createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "DeletedAt")
    private Instant deletedAt;

    @ManyToOne
    @JoinColumn(name = "DeletedBy")
    private OperatorEntity deletedBy;

}
