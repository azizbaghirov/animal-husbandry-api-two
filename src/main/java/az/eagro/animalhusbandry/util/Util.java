package az.eagro.animalhusbandry.util;

import az.eagro.animalhusbandry.model.FileExtension;
import org.springframework.http.MediaType;

public class Util {

    private static final String BASE_FOLDER_NAME = "/storage/";

    public static String generateObjectName(String objectName) {
        return BASE_FOLDER_NAME + objectName;
    }

    public static MediaType getMediaType(String extension) {
        switch (FileExtension.valueOf(extension.toUpperCase())) {
            case PDF -> {
                return MediaType.APPLICATION_PDF;
            }
            case PNG -> {
                return MediaType.IMAGE_PNG;
            }
            case JPEG, JPG -> {
                return MediaType.IMAGE_JPEG;
            }
            default -> {
                return MediaType.APPLICATION_OCTET_STREAM;
            }
        }
    }
}
