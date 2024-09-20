package az.eagro.animalhusbandry.util.fileutil;


import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.feign.EagroFeignClient;
import az.eagro.animalhusbandry.model.CertificationApplicationEntity;
import feign.Feign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FileManager {

    private final EagroFeignClient eagroFeignClient;
    private final FileValidation fileValidation;
    private final MultipartFileCreator multipartFileCreator;


    public void saveBreedingAnimalFile(CertificationApplicationEntity newApplication, NewFileDTO file) {
        fileValidation.fileNameExtensionValidate(file.getOriginalFilename());
        MultipartFile multipartFile = multipartFileCreator.getMultipartFile(file);
        var filePath = eagroFeignClient.fileUpload(multipartFile);
        if (filePath.get("path") == null) {
            throw new BusinessException("File server-də xəta baş verdi");
        }
        newApplication.setFilePath(filePath.get("path"));
    }

}
