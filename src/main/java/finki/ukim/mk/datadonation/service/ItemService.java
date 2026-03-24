package finki.ukim.mk.datadonation.service;

import finki.ukim.mk.datadonation.domain.dto.ItemDto;
import finki.ukim.mk.datadonation.domain.models.Item;
import finki.ukim.mk.datadonation.web.request.ItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ItemService {

    Page<Item> getItems(Pageable pageable);

    Page<Item> getItemsByDonorId(UUID userId, Pageable pageable);

    Item getItemById(UUID id, UUID userId);

    Item createItem(UUID userId, ItemDto itemDto);

    void deleteItem(UUID itemId, UUID userId);
}
