package finki.ukim.mk.datadonation.web.response;

import finki.ukim.mk.datadonation.domain.enums.CountryCode;
import finki.ukim.mk.datadonation.domain.enums.ItemCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record ItemResponse(
        UUID id,
        String originalFileName,
        String fileUrl,
        Long fileSizeBytes,
        String mimeType,
        CountryCode language,
        ItemCategory itemCategory,
        UserResponse donor,
        LocalDateTime createdAt
) {}