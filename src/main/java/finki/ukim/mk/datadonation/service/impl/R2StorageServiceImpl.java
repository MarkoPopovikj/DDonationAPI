package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;
import java.util.UUID;

@Slf4j
@Service
public class R2StorageServiceImpl implements StorageService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String publicUrl;

    public R2StorageServiceImpl(@Value("${aws.access.key.id}") String accessKey,
                                @Value("${aws.secret.access.key}") String secretKey,
                                @Value("${aws.s3.bucket}") String bucketName,
                                @Value("${aws.s3.endpoint}") String endpoint,
                                @Value("${aws.public.url}") String publicUrl) {

        this.bucketName = bucketName;
        this.publicUrl = publicUrl;
        this.s3Client = S3Client.builder()
                .region(Region.of("auto"))
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file, String folderPrefix, String mimeType) {

        String originalFilename = file.getOriginalFilename();
        String safeName = originalFilename != null ? originalFilename.replaceAll("[^a-zA-Z0-9\\.\\-]", "_") : "file";

        String objectKey = folderPrefix + UUID.randomUUID() + "-" + safeName;

        log.info("Uploading file to R2 path: {}", objectKey);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(mimeType)
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );

            return publicUrl + "/" + objectKey;

        } catch (Exception e) {
            log.error("Failed to upload file to R2", e);
            throw new RuntimeException("Storage failure");
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }

        try {
            String objectKey = fileUrl.replace(publicUrl + "/", "");
            log.info("Deleting file from R2: {}", objectKey);

            DeleteObjectRequest deleteReq = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.deleteObject(deleteReq);

        } catch (Exception e) {
            log.error("Warning: Could not delete file from R2: {}", e.getMessage());
        }
    }
}
