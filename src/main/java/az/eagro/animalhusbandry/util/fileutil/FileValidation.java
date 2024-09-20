package az.eagro.animalhusbandry.util.fileutil;

import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.model.FileExtension;
import java.util.Arrays;
import java.util.EnumSet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileValidation {

    public void fileNameExtensionValidate(String fileName) {
        String ext = StringUtils.getFilenameExtension(fileName);
        permittedExtension(ext);
    }

    public void extensionValidate(String ext) {
        permittedExtension(ext);
    }

    private void permittedExtension(String ext) {
        if (ext == null) {
            throw new BusinessException("ext. can not be null ");
        }

        EnumSet.allOf(FileExtension.class).stream().filter(e -> e.getExtension().equals(ext.toLowerCase()))
                .findFirst().orElseThrow(() -> new BusinessException("Extension not found. "));
    }

}
