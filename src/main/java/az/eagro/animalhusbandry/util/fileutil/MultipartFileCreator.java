package az.eagro.animalhusbandry.util.fileutil;

import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MultipartFileCreator {

    public MultipartFile getMultipartFile(NewFileDTO file) {
        String name = file.getOriginalFilename().split("[.]")[0];
        byte[] bytes = Base64.decode(file.getContent());

        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getOriginalFilename() {
                return file.getOriginalFilename();
            }

            @Override
            public String getContentType() {
                return file.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return bytes == null || bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                //ToDo  FileUtils.writeByteArrayToFile(dest, bytes);
            }

        };
        return multipartFile;
    }

}
