package api.bsstrader.sticker;

import api.bsstrader.player.Player;
import api.bsstrader.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StickerService {
    private final StickerRepo stickerRepo;
    private final PlayerService playerService;

    public StickerService(StickerRepo stickerRepo, PlayerService playerService) {
        this.stickerRepo = stickerRepo;
        this.playerService = playerService;
    }

    // Get all stickers
    public List<Sticker> getAllStickers() {
        return stickerRepo.findAll();
    }

    // Get a sticker by ID
    public Optional<Sticker> getStickerById(Long id) {
        return stickerRepo.findById(id);
    }

    // Create a new sticker
    @Transactional
    public Sticker createSticker(Sticker sticker) {
        return stickerRepo.save(sticker);
    }

    // Update an existing sticker
    @Transactional
    public Sticker updateSticker(Long id, Sticker updatedSticker) {
        if (!stickerRepo.existsById(id)) {
            throw new IllegalArgumentException("Sticker with ID " + id + " does not exist.");
        }
        updatedSticker.setId(id); // Ensure the ID is set for the update
        return stickerRepo.save(updatedSticker);
    }

    // Delete a sticker
    @Transactional
    public void deleteSticker(Long id) {
        if (!stickerRepo.existsById(id)) {
            throw new IllegalArgumentException("Sticker with ID " + id + " does not exist.");
        }
        // Optionally handle removing the sticker from all players' inventories before deleting
        // This will ensure consistency and avoid orphaned references
        stickerRepo.deleteById(id);
    }

    // Add an owner to a sticker
    @Transactional
    public void addOwnerToSticker(Long stickerId, UUID playerId) {
        Sticker sticker = stickerRepo.findById(stickerId)
                .orElseThrow(() -> new IllegalArgumentException("Sticker with ID " + stickerId + " does not exist."));

        Player player = playerService.getPlayerById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + playerId + " does not exist."));

        sticker.getOwners().add(player);
        player.getInventory().add(sticker);

        // Save changes
        stickerRepo.save(sticker);
        playerService.savePlayer(player); // Ensure player changes are also persisted
    }

    // Remove an owner from a sticker
    @Transactional
    public void removeOwnerFromSticker(Long stickerId, UUID playerId) {
        Sticker sticker = stickerRepo.findById(stickerId)
                .orElseThrow(() -> new IllegalArgumentException("Sticker with ID " + stickerId + " does not exist."));

        Player player = playerService.getPlayerById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + playerId + " does not exist."));

        sticker.getOwners().remove(player);
        player.getInventory().remove(sticker);

        // Save changes
        stickerRepo.save(sticker);
        playerService.savePlayer(player); // Ensure player changes are also persisted
    }
}
