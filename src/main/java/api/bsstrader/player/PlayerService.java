package api.bsstrader.player;

import api.bsstrader.sticker.Sticker;
import api.bsstrader.sticker.StickerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerRepo playerRepo;
    private final StickerRepo stickerRepo;

    public PlayerService(PlayerRepo playerRepo, StickerRepo stickerRepo) {
        this.playerRepo = playerRepo;
        this.stickerRepo = stickerRepo;
    }

    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepo.findAll();
    }

    // Get a player by ID
    public Optional<Player> getPlayerById(UUID id) {
        return playerRepo.findById(id);
    }

    // Create a new player
    @Transactional
    public Player createPlayer(Player player) {
        return playerRepo.save(player);
    }

    // Update an existing player
    @Transactional
    public Player updatePlayer(UUID id, Player updatedPlayer) {
        if (!playerRepo.existsById(id)) {
            throw new IllegalArgumentException("Player with ID " + id + " does not exist.");
        }
        updatedPlayer.setId(id); // Ensure the ID is set for the update
        return playerRepo.save(updatedPlayer);
    }

    // Delete a player
    @Transactional
    public void deletePlayer(UUID id) {
        if (!playerRepo.existsById(id)) {
            throw new IllegalArgumentException("Player with ID " + id + " does not exist.");
        }
        playerRepo.deleteById(id);
    }

    // Add a sticker to a player's inventory
    @Transactional
    public void addStickerToPlayer(UUID playerId, Long stickerId) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + playerId + " does not exist."));

        Sticker sticker = stickerRepo.findById(stickerId)
                .orElseThrow(() -> new IllegalArgumentException("Sticker with ID " + stickerId + " does not exist."));

        player.getInventory().add(sticker);
        sticker.getOwners().add(player);

        playerRepo.save(player);
        stickerRepo.save(sticker); // Ensure the sticker is saved with updated owners
    }

    // Remove a sticker from a player's inventory
    @Transactional
    public void removeStickerFromPlayer(UUID playerId, Long stickerId) {
        Player player = playerRepo.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + playerId + " does not exist."));

        Sticker sticker = stickerRepo.findById(stickerId)
                .orElseThrow(() -> new IllegalArgumentException("Sticker with ID " + stickerId + " does not exist."));

        player.getInventory().remove(sticker);
        sticker.getOwners().remove(player);

        playerRepo.save(player);
        stickerRepo.save(sticker); // Ensure the sticker is saved with updated owners
    }

    // Save or update a player
    @Transactional
    public Player savePlayer(Player player) {
        return playerRepo.save(player);
    }
}
