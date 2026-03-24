package finki.ukim.mk.datadonation.web.extensions;

import finki.ukim.mk.datadonation.domain.dto.ItemDto;
import finki.ukim.mk.datadonation.domain.models.Item;
import finki.ukim.mk.datadonation.web.request.ItemRequest;
import finki.ukim.mk.datadonation.web.response.ItemResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
public final class ItemExtensions {

    public static ItemResponse toResponse(Item item) {
        if (item == null) return null;

        return new ItemResponse(
                item.getId(),
                item.getOriginalFileName(),
                item.getFileUrl(),
                item.getFileSizeBytes(),
                item.getMimeType(),
                item.getLanguage(),
                item.getCategory(),
                UserExtensions.toResponse(item.getDonor()),
                item.getCreatedAt()
        );
    }

    public static Page<ItemResponse> toResponse(Page<Item> items) {
        return items.map(ItemExtensions::toResponse);
    }

    public static ItemDto toDto(ItemRequest request) {
        if (request == null) return null;

        return new ItemDto(
                request.file(),
                request.language()
        );
    }
}
