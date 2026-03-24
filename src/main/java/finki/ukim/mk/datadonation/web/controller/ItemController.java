package finki.ukim.mk.datadonation.web.controller;

import finki.ukim.mk.datadonation.web.mapper.ItemMapper;
import finki.ukim.mk.datadonation.web.request.ItemRequest;
import finki.ukim.mk.datadonation.web.response.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemMapper itemMapper;

    public ItemController(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ItemResponse>> getItems(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(this.itemMapper.getItems(pageable));
    }

    @GetMapping("/my-history")
    public ResponseEntity<Page<ItemResponse>> getItemsByDonor(
            Principal principal,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(this.itemMapper.getItemsByDonorId(UUID.fromString(principal.getName()), pageable));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ItemResponse> getItemById(
            @PathVariable UUID id,
            Principal principal
    ) {
        return ResponseEntity.ok(this.itemMapper.getItemById(id, UUID.fromString(principal.getName())));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> uploadItem(
            @ModelAttribute ItemRequest request,
            Principal principal
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.itemMapper.createItem(UUID.fromString(principal.getName()), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID id,
            Principal principal
    ) {
        itemMapper.deleteItem(id, UUID.fromString(principal.getName()));
        return ResponseEntity.noContent().build();
    }
}
