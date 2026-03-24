package finki.ukim.mk.datadonation.domain.dto;

import finki.ukim.mk.datadonation.domain.enums.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ItemDto {

    private MultipartFile file;

    private CountryCode language;

}
