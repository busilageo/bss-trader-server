package api.bsstrader.controller;

import api.bsstrader.entity.Player;
import api.bsstrader.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    // Get all players
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    // Get player by ID
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable UUID id) {
        Optional<Player> player = playerService.getPlayerById(id);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new player
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player createdPlayer = playerService.savePlayer(player);
        return ResponseEntity.ok(createdPlayer);
    }

    // Update an existing player
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable UUID id, @RequestBody Player updatedPlayer) {
        try {
            Player player = playerService.updatePlayer(id, updatedPlayer);
            return ResponseEntity.ok(player);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a player
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a sticker to a player's inventory
    @PostMapping("/{playerId}/stickers/{stickerId}")
    public ResponseEntity<Void> addStickerToPlayer(@PathVariable UUID playerId, @PathVariable Long stickerId) {
        try {
            playerService.addStickerToPlayer(playerId, stickerId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove a sticker from a player's inventory
    @DeleteMapping("/{playerId}/stickers/{stickerId}")
    public ResponseEntity<Void> removeStickerFromPlayer(@PathVariable UUID playerId, @PathVariable Long stickerId) {
        try {
            playerService.removeStickerFromPlayer(playerId, stickerId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
