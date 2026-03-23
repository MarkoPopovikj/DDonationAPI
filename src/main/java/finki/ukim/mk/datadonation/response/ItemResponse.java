package finki.ukim.mk.datadonation.response;

import finki.ukim.mk.datadonation.model.enums.CountryCode;
import finki.ukim.mk.datadonation.model.enums.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ItemResponse {

    private UUID id;

    private String originalFileName;

    private String fileUrl;

    private Long fileSizeBytes;

    private String mimeType;

    private CountryCode language;

    private ItemCategory itemCategory;

    private UserResponse donor;

    private LocalDateTime createdAt;

}
