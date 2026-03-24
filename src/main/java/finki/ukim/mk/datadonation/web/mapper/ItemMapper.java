package finki.ukim.mk.datadonation.web.mapper;

import finki.ukim.mk.datadonation.domain.models.Item;
import finki.ukim.mk.datadonation.web.extensions.ItemExtensions;
import finki.ukim.mk.datadonation.web.request.ItemRequest;
import finki.ukim.mk.datadonation.web.response.ItemResponse;
import finki.ukim.mk.datadonation.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ItemMapper {

    private final ItemService itemService;

    public ItemMapper(ItemService itemService) {
        this.itemService = itemService;
    }

    public Page<ItemResponse> getItems(Pageable pageable) {
        Page<Item> items = this.itemService.getItems(pageable);
        return ItemExtensions.toResponse(items);
    }

    public Page<ItemResponse> getItemsByDonorId(UUID userId, Pageable pageable) {
        Page<Item> items = this.itemService.getItemsByDonorId(userId, pageable);
        return ItemExtensions.toResponse(items);
    }

    public ItemResponse getItemById(UUID id, UUID userId) {
        Item item = this.itemService.getItemById(id, userId);
        return ItemExtensions.toResponse(item);
    }

    public ItemResponse createItem(UUID userId, ItemRequest itemRequest) {
        Item item = this.itemService.createItem(userId, ItemExtensions.toDto(itemRequest));
        return ItemExtensions.toResponse(item);
    }

    public void deleteItem(UUID id, UUID userId) {
        this.itemService.deleteItem(id, userId);
    }

}
