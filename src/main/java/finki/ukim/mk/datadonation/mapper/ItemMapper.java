package finki.ukim.mk.datadonation.mapper;

import finki.ukim.mk.datadonation.model.Item;
import finki.ukim.mk.datadonation.request.ItemRequest;
import finki.ukim.mk.datadonation.response.ItemResponse;
import finki.ukim.mk.datadonation.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ItemMapper {

    private final ItemService itemService;
    private final UserMapper userMapper;

    public ItemMapper(ItemService itemService, UserMapper userMapper) {
        this.itemService = itemService;
        this.userMapper = userMapper;
    }

    public Page<ItemResponse> getItems(Pageable pageable) {
        Page<Item> items = this.itemService.getItems(pageable);
        return mapToPageableResponse(items);
    }

    public Page<ItemResponse> getItemsByDonorId(UUID userId, Pageable pageable) {
        Page<Item> items = this.itemService.getItemsByDonorId(userId, pageable);
        return mapToPageableResponse(items);
    }

    public ItemResponse getItemById(UUID id, UUID userId) {
        Item item = this.itemService.getItemById(id, userId);
        return mapToResponse(item);
    }

    public ItemResponse createItem(UUID userId, ItemRequest itemRequest) {
        Item item = this.itemService.createItem(userId, itemRequest);
        return mapToResponse(item);
    }

    public void deleteItem(UUID id, UUID userId) {
        this.itemService.deleteItem(id, userId);
    }

    public ItemResponse mapToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getOriginalFileName(),
                item.getFileUrl(),
                item.getFileSizeBytes(),
                item.getMimeType(),
                item.getLanguage(),
                item.getCategory(),
                this.userMapper.mapToResponse(item.getDonor()),
                item.getCreatedAt()
        );
    }

    public Page<ItemResponse> mapToPageableResponse(Page<Item> items) {
        return items.map(this::mapToResponse);
    }
}
