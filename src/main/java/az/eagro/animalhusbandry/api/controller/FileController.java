package az.eagro.animalhusbandry.api.controller;

import az.eagro.animalhusbandry.api.service.FileService;
import az.eagro.animalhusbandry.api.service.model.certification.FileSummaryDTO;
import az.eagro.animalhusbandry.business.BusinessException;
import com.amazonaws.util.IOUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@SecurityRequirement(name = "SID")
@Tag(name = "File Controller")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Download file by id")
    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable("fileId") UUID fileId) throws BusinessException, IOException {
        FileSummaryDTO file = fileService.getFileData(fileId);
        InputStream in = file.getInputStream();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName())
                .contentType(file.getMediaType())
                .body(IOUtils.toByteArray(in));
    }

    @Operation(summary = "Show file by id")
    @GetMapping(value = "/show/{fileId}")
    public ResponseEntity<byte[]> show(@PathVariable("fileId") UUID fileId) throws BusinessException, IOException {
        FileSummaryDTO file = fileService.getFileData(fileId);
        InputStream in = file.getInputStream();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getFileName())
                .contentType(file.getMediaType())
                .body(IOUtils.toByteArray(in));
    }

}


