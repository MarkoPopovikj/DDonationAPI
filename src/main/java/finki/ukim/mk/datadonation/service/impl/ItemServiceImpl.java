package finki.ukim.mk.datadonation.service.impl;

import finki.ukim.mk.datadonation.model.Item;
import finki.ukim.mk.datadonation.model.User;
import finki.ukim.mk.datadonation.model.enums.ItemCategory;
import finki.ukim.mk.datadonation.model.enums.UserRole;
import finki.ukim.mk.datadonation.repository.ItemRepository;
import finki.ukim.mk.datadonation.request.ItemRequest;
import finki.ukim.mk.datadonation.response.ItemResponse;
import finki.ukim.mk.datadonation.service.ItemService;
import finki.ukim.mk.datadonation.service.StorageService;
import finki.ukim.mk.datadonation.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final StorageService storageService;
    private final UserService userService;

    private final Tika tika;

    public ItemServiceImpl(ItemRepository itemRepository, StorageService storageService, UserService userService) {
        this.itemRepository = itemRepository;
        this.storageService = storageService;
        this.userService = userService;
        this.tika = new Tika();
    }

    @Override
    public Page<Item> getItems(Pageable pageable) {
        return this.itemRepository.findAll(pageable);
    }

    @Override
    public Page<Item> getItemsByDonorId(UUID userId, Pageable pageable) {
        return this.itemRepository.findAllByDonorId(userId, pageable);
    }

    @Override
    public Item getItemById(UUID id, UUID userId) {
        Item item = this.itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item with id " + id + " not found"));

        User requestingUser = this.userService.getUserById(userId);

        boolean isOwner = item.getDonor().getId().equals(userId);
        boolean isAdmin = requestingUser.getRole() == UserRole.ADMIN ||
                requestingUser.getRole() == UserRole.SUPERADMIN;

        if (!isOwner && !isAdmin) {
            log.warn("Unauthorized access attempt: User {} tried to view Item {}", userId, id);
            throw new AccessDeniedException("You do not have permission to view this donation");
        }

        return item;
    }

    @Override
    @Transactional
    public Item createItem(UUID userId, ItemRequest itemRequest) {
        User user = this.userService.getUserById(userId);
        MultipartFile file = itemRequest.getFile();

        try {
            String detectedMimeType = tika.detect(file.getInputStream());
            log.info("File uploaded: {} | Tika detected MIME type: {}", file.getOriginalFilename(), detectedMimeType);

            ItemCategory category = ItemCategory.fromMimeType(detectedMimeType);
            String folderPrefix = category.getFolderName() + "/";

            String fileUrl = storageService.uploadFile(file, folderPrefix, detectedMimeType);

            Item item = new Item();
            item.setOriginalFileName(file.getOriginalFilename());
            item.setFileUrl(fileUrl);
            item.setFileSizeBytes(file.getSize());
            item.setMimeType(detectedMimeType);
            item.setCategory(category);
            item.setLanguage(itemRequest.getLanguage());
            item.setDonor(user);

            return this.itemRepository.save(item);

        } catch (IllegalArgumentException e) {
            log.error("Invalid file category attempted: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to process and upload file", e);
            throw new RuntimeException("Could not create item: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteItem(UUID itemId, UUID userId) {
        Item item = getItemById(itemId, userId);

        log.info("Deleting item: {} by User: {}", itemId, userId);

        storageService.deleteFile(item.getFileUrl());
        itemRepository.delete(item);
    }
}
