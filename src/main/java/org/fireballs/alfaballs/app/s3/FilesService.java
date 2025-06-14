package org.fireballs.alfaballs.app.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilesService {
    private final YandexS3Client s3Client;

    public List<String> uploadFilesToYandex(List<MultipartFile> files) {
        List<String> fileUrls = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    File tempFile = File.createTempFile("upload_", "_" + file.getOriginalFilename());
                    file.transferTo(tempFile);

                    String s3Key = "issues/" + file.getOriginalFilename();
                    s3Client.uploadFile(s3Key, tempFile.toPath());

                    String fileUrl = "https://storage.yandexcloud.net/" + s3Client.getBucketName() + "/" + s3Key;
                    fileUrls.add(fileUrl);

                    tempFile.delete();
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
                }
            }
        }

        return fileUrls;
    }
}
