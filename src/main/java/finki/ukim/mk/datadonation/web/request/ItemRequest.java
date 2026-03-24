package finki.ukim.mk.datadonation.web.request;

import finki.ukim.mk.datadonation.domain.enums.CountryCode;
import org.springframework.web.multipart.MultipartFile;

public record ItemRequest(
        MultipartFile file,
        CountryCode language
) {}