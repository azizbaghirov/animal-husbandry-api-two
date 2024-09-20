package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FileSummaryDTO;
import az.eagro.animalhusbandry.business.file.S3FileManager;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.FileType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class FileService {

    private final S3FileManager fileManager;

    public List<FileEntity> getFiles(List<NewFileDTO> files, FileType fileType) {
        return fileManager.getFiles(files, fileType);
    }

    public FileEntity getFile(NewFileDTO file, FileType fileType) {
        return fileManager.getFile(file, fileType);
    }

    public FileSummaryDTO getFileData(UUID fileId) {
        return fileManager.getFileData(fileId);
    }

}
