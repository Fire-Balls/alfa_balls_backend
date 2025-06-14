package org.fireballs.alfaballs.app.s3;

import lombok.Getter;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;
import java.nio.file.Path;

public class YandexS3Client {

    private final S3Client s3Client;
    @Getter
    private final String bucketName;

    public YandexS3Client(String accessKey, String secretKey, String bucketName) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .region(Region.of("ru-central1")) // фиктивно, требуется SDK
                .endpointOverride(URI.create("https://storage.yandexcloud.net")) // URL Яндекс S3
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true) // ОБЯЗАТЕЛЬНО!
                                .build()
                )
                .build();
    }

    public void uploadFile(String key, Path filePath) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(request, filePath);
    }
}

