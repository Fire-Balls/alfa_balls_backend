package org.fireballs.alfaballs.app.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${yandex.s3.access-key}")
    private String accessKey;

    @Value("${yandex.s3.secret-key}")
    private String secretKey;

    @Value("${yandex.s3.bucket-name}")
    private String bucketName;

    @Bean
    public YandexS3Client yandexS3Client() {
        return new YandexS3Client(accessKey, secretKey, bucketName);
    }
}
