package az.eagro.animalhusbandry.business;

import az.eagro.animalhusbandry.model.FarmerAccountEntity;
import az.eagro.animalhusbandry.model.FarmerAccountType;
import az.eagro.animalhusbandry.model.LegalPersonEntity;
import az.eagro.animalhusbandry.model.PhysicalPersonEntity;
import az.eagro.animalhusbandry.repository.FarmerAccountRepository;
import az.eagro.animalhusbandry.repository.LegalPersonRepository;
import az.eagro.animalhusbandry.repository.PhysicalPersonRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FarmerAccountManager {

    private final AuthenticatedUserInfoProvider auth;
    private final PhysicalPersonRepository physicalPersonRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final FarmerAccountRepository farmerAccountRepository;

    @Transactional
    public void checkoutFarmerAccount() {
        boolean isLegal = auth.isLegal();
        UUID userId = auth.getUserId();

        String taxPayerIdentificationNumber = isLegal ? auth.getLegalPerson().getTaxPayerIdentificationNumber() : null;

        if ((isLegal
                && farmerAccountRepository.existsByPhysicalPersonIdAndLegalPersonTaxPayerIdentificationNumber(userId, taxPayerIdentificationNumber))
                || (!isLegal && farmerAccountRepository.existsByPhysicalPersonIdAndLegalPersonIsNull(userId))) {
            return;
        }

        var farmerAccount = new FarmerAccountEntity();
        checkLegalPerson(isLegal, farmerAccount);
        checkPhysicalPerson(isLegal, userId, farmerAccount);
        farmerAccountRepository.save(farmerAccount);

    }

    private void checkPhysicalPerson(boolean isLegal, UUID userId, FarmerAccountEntity farmerAccount) {
        var physicalPerson = physicalPersonRepository.findById(userId).orElse(null);

        if (physicalPerson == null) {
            physicalPerson = PhysicalPersonEntity.builder()
                    .id(userId)
                    .name(auth.getUserName())
                    .surname(auth.getUserSurname())
                    .patronymic(auth.getUserPatronymic())
                    .personalIdentificationNumber(auth.getUserPin())
                    .build();

            if ((auth.getLegalPerson() != null) && !isLegal) {
                physicalPerson.setTaxPayerIdentificationNumber(auth.getLegalPerson().getTaxPayerIdentificationNumber());
            }
            physicalPerson = physicalPersonRepository.save(physicalPerson);
        }

        farmerAccount.setPhysicalPerson(physicalPerson);
    }

    private void checkLegalPerson(boolean isLegal, FarmerAccountEntity farmerAccount) {
        if (isLegal) {
            var legalPerson = legalPersonRepository.findByTaxPayerIdentificationNumber(
                    auth.getLegalPerson().getTaxPayerIdentificationNumber()).orElse(null);

            if (legalPerson == null) {
                legalPerson = LegalPersonEntity.builder()
                        .taxPayerIdentificationNumber(auth.getLegalPerson().getTaxPayerIdentificationNumber())
                        .name(auth.getLegalPerson().getName())
                        .hasStamp(auth.getLegalPerson().isHasStamp()).build();
                legalPerson = legalPersonRepository.save(legalPerson);
            }

            farmerAccount.setLegalPerson(legalPerson);
            farmerAccount.setFarmerAccountType(FarmerAccountType.LEGAL_PERSON);
        }
    }

    public FarmerAccountEntity findFarmerAccount() {
        UUID userId = auth.getUserId();
        return auth.isLegal()
                ? getFarmerAccountByPhysicalPersonIdAndTaxNumber(userId, auth.getLegalPerson().getTaxPayerIdentificationNumber())
                : getFarmerAccountByPhysicalPersonId(userId);

    }

    // legal person
    private FarmerAccountEntity getFarmerAccountByPhysicalPersonIdAndTaxNumber(UUID physicalPersonId, String taxNumber) {
        return farmerAccountRepository.findByPhysicalPersonIdAndLegalPersonTaxPayerIdentificationNumber(physicalPersonId, taxNumber)
                .orElseThrow(() -> new BusinessException("Hüquqi şəxs kabineti tapılmadı"));
    }

    // physical person
    private FarmerAccountEntity getFarmerAccountByPhysicalPersonId(UUID physicalPersonId) {
        return farmerAccountRepository.findByPhysicalPersonIdAndLegalPersonIsNull(physicalPersonId)
                .orElseThrow(() -> new BusinessException("Fiziki şəxs kabineti tapılmadı"));
    }

}
