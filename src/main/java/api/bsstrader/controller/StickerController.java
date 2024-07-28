package api.bsstrader.controller;

import api.bsstrader.entity.Sticker;
import api.bsstrader.service.StickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stickers")
public class StickerController {

    private final StickerService stickerService;

    @Autowired
    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }

    // Get all stickers
    @GetMapping
    public ResponseEntity<List<Sticker>> getAllStickers() {
        List<Sticker> stickers = stickerService.getAllStickers();
        return ResponseEntity.ok(stickers);
    }

    // Get sticker by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sticker> getStickerById(@PathVariable Long id) {
        Optional<Sticker> sticker = stickerService.getStickerById(id);
        if (sticker.isPresent()) {
            return ResponseEntity.ok(sticker.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new sticker
    @PostMapping
    public ResponseEntity<Sticker> createSticker(@RequestBody Sticker sticker) {
        Sticker createdSticker = stickerService.createSticker(sticker);
        return ResponseEntity.ok(createdSticker);
    }

    // Update an existing sticker
    @PutMapping("/{id}")
    public ResponseEntity<Sticker> updateSticker(@PathVariable Long id, @RequestBody Sticker updatedSticker) {
        try {
            Sticker sticker = stickerService.updateSticker(id, updatedSticker);
            return ResponseEntity.ok(sticker);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a sticker
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSticker(@PathVariable Long id) {
        try {
            stickerService.deleteSticker(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add an owner to a sticker
    @PostMapping("/{stickerId}/owners/{playerId}")
    public ResponseEntity<Void> addOwnerToSticker(@PathVariable Long stickerId, @PathVariable UUID playerId) {
        try {
            stickerService.addOwnerToSticker(stickerId, playerId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove an owner from a sticker
    @DeleteMapping("/{stickerId}/owners/{playerId}")
    public ResponseEntity<Void> removeOwnerFromSticker(@PathVariable Long stickerId, @PathVariable UUID playerId) {
        try {
            stickerService.removeOwnerFromSticker(stickerId, playerId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
