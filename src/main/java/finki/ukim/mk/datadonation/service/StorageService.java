package finki.ukim.mk.datadonation.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadFile(MultipartFile file, String folderPrefix, String mimeType);

    void deleteFile(String fileUrl);
}
