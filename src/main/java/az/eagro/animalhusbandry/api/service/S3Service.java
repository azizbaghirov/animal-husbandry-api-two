package az.eagro.animalhusbandry.api.service;

import az.eagro.animalhusbandry.api.service.model.NewFileDTO;
import az.eagro.animalhusbandry.util.fileutil.MultipartFileCreator;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class S3Service {

    private final AmazonS3 s3Client;

    private final MultipartFileCreator multipartFileCreator;

    @Value("${minio.bucket-name}")
    public String bucketName;


    public List<Bucket> listBuckets() {
        return s3Client.listBuckets();
    }

    public void createBucket(String bucketName) {
        s3Client.createBucket(new CreateBucketRequest(bucketName).withCannedAcl(CannedAccessControlList.Private));
    }

    public void deleteBucket(String bucketName) {
        s3Client.deleteBucket(bucketName);
    }

    public List<String> listObjects(String bucketName) {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries().stream().map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    public void uploadFile(NewFileDTO file, String objectName) {
        MultipartFile multipartFile = multipartFileCreator.getMultipartFile(file);
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(multipartFile.getContentType());
        data.setContentLength(multipartFile.getSize());
        PutObjectResult objectResult = null;
        try {
            objectResult = s3Client.putObject(bucketName, objectName, multipartFile.getInputStream(), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // todo:  objectResult.getContentMd5(); -  can verify MD5
    }

    public InputStream downloadObject(String objectName) {
        S3Object s3object = s3Client.getObject(bucketName, objectName);

        return s3object.getObjectContent();
    }

    public void moveObject(String bucketSourceName, String objectName, String bucketTargetName) {
        s3Client.copyObject(
                bucketSourceName,
                objectName,
                bucketTargetName,
                objectName
        );
    }

    public void deleteObject(String objectName) {
        s3Client.deleteObject(bucketName, objectName);
    }

    public void deleteMultipleObjects(String bucketName, List<String> objects) {
        DeleteObjectsRequest delObjectsRequests = new DeleteObjectsRequest(bucketName)
                .withKeys(objects.toArray(new String[0]));
        s3Client.deleteObjects(delObjectsRequests);
    }
}
