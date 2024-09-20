package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.OperatorDTO;
import az.eagro.animalhusbandry.business.OperatorManager;
import az.eagro.animalhusbandry.model.OperatorEntity;
import az.eagro.animalhusbandry.model.UserRole;
import com.remondis.remap.Mapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OperatorService {

    private final Mapper<OperatorEntity, OperatorDTO> operatorToDtoMapper;
    private final OperatorManager operatorManager;

    public OperatorDTO getOperatorById(String pin) {
        var operatorDTO = operatorManager.getByPin(pin) != null
                ? operatorToDtoMapper.map(operatorManager.getByPin(pin))
                : null;
        return operatorDTO;
    }

    public List<UserRole> getOperatorRoles() {
        return operatorManager.getOperatorRoles();
    }
}
