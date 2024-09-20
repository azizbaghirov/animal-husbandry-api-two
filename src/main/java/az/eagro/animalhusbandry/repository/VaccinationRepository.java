package az.eagro.animalhusbandry.repository;

import az.eagro.animalhusbandry.model.VaccinationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationRepository extends JpaRepository<VaccinationEntity, Integer> {

    @Query("SELECT v FROM InitialMonitoringDataEntity imd "
            + " JOIN imd.vaccinations v "
            + " WHERE  imd.id = :dataId ")
    List<VaccinationEntity> getByInitialMonitoringDataId(Integer dataId);

    List<VaccinationEntity> findByFarmTypeId(Integer farmTypeId);

}
