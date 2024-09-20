package az.eagro.animalhusbandry.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "InitialMonitoringData")
@Entity
public class InitialMonitoringDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InitialMonitoringDataId", nullable = false, updatable = false, unique = true)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "InitialMonitoringDecisionId", nullable = false)
    private InitialMonitoringDecisionEntity monitoringDecision;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "InitialMonitoringDataId", nullable = false, updatable = false)
    private List<DiscoveredDataEntity> discoveredData = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "InitialMonitoringDataVaccination",
            joinColumns = @JoinColumn(name = "InitialMonitoringDataId"),
            inverseJoinColumns = @JoinColumn(name = "VaccinationId"))
    private Set<VaccinationEntity> vaccinations = new HashSet<>();

    @Column(name = "Disinfected", nullable = false)
    private boolean disinfected;

}
