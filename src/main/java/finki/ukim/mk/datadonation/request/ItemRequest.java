package finki.ukim.mk.datadonation.request;

import finki.ukim.mk.datadonation.model.enums.CountryCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ItemRequest {

    private final MultipartFile file;
    private final CountryCode language;

}
