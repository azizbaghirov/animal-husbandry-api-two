package az.eagro.animalhusbandry.business.file;


import az.eagro.animalhusbandry.api.service.S3Service;
import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import az.eagro.animalhusbandry.api.service.model.certification.FileSummaryDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import az.eagro.animalhusbandry.model.DocumentTypeEntity;
import az.eagro.animalhusbandry.model.FileEntity;
import az.eagro.animalhusbandry.model.FileType;
import az.eagro.animalhusbandry.repository.FileRepository;
import az.eagro.animalhusbandry.util.Util;
import az.eagro.animalhusbandry.util.fileutil.FileValidation;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class S3FileManager {

    private final S3Service fileService;
    private final FileRepository fileRepository;
    private final FileValidation fileValidation;

    public List<FileEntity> getFiles(List<NewFileDTO> files, FileType fileType) {
        List<FileEntity> fileList = new ArrayList<>();
        if (!files.isEmpty()) {
            files.forEach(file -> {
                String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
                fileValidation.extensionValidate(extension);
                String objectName = fileType.getLabel() + "/" + UUID.randomUUID() + "." + extension;
                //toDo: check upload result
                fileService.uploadFile(file, objectName);
                fileList.add(FileEntity
                        .builder()
                        .documentType(DocumentTypeEntity.builder().id(file.getDocumentTypeId()).build())
                        .path(objectName)
                        .contentType(file.getContentType())
                        .build());
            });
        }
        return fileList;
    }

    public FileEntity getFile(NewFileDTO file, FileType fileType) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        fileValidation.extensionValidate(extension);
        String objectName = fileType.getLabel() + "/" + UUID.randomUUID() + "." + extension;

        //toDo: check upload result
        fileService.uploadFile(file, objectName);
        return FileEntity
                .builder()
                .documentType(DocumentTypeEntity.builder().id(file.getDocumentTypeId()).build())
                .path(objectName)
                .contentType(file.getContentType())
                .build();
    }

    public FileSummaryDTO getFileData(UUID fileId) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new BusinessException("File not found"));
        InputStream inputStream = fileService.downloadObject(file.getPath());

        String fileName = Paths.get(file.getPath()).getFileName().toString();
        String extension = StringUtils.getFilenameExtension(fileName);
        MediaType mediaType = Util.getMediaType(extension);

        return FileSummaryDTO.builder().inputStream(inputStream).fileName(fileName).mediaType(mediaType).build();

    }

}
