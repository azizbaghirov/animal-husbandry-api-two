package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.DocumentRequestDTO;
import az.eagro.animalhusbandry.api.service.model.FieldDocumentSummaryDTO;
import az.eagro.animalhusbandry.business.FieldDocumentManager;
import az.eagro.animalhusbandry.feign.model.FieldDocumentDTO;
import az.eagro.animalhusbandry.model.FieldDocumentEntity;
import com.remondis.remap.Mapper;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FieldDocumentService {

    private final FieldDocumentManager fieldDocumentManager;
    private final Mapper<FieldDocumentEntity, FieldDocumentSummaryDTO> fieldDocumentSummaryModelDTOMapper;

    public Set<FieldDocumentDTO> getFieldDocuments(Integer areaId, DocumentRequestDTO document) {
        return fieldDocumentManager.getFieldDocumentsByAreaId(areaId, document);
    }

    public List<FieldDocumentSummaryDTO> getFieldDocumentListByFarmId(Integer farmId) {
        return fieldDocumentSummaryModelDTOMapper.map(fieldDocumentManager.getFieldDocumentListByFarmId(farmId));
    }

}
