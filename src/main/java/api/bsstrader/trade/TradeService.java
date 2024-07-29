package api.bsstrader.trade;

import api.bsstrader.player.Player;
import api.bsstrader.player.PlayerService;
import api.bsstrader.sticker.Sticker;
import api.bsstrader.sticker.StickerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeService {
    private final TradeRepo tradeRepo;
    private final PlayerService playerService; // Inject PlayerService for player operations
    private final StickerService stickerService;

    public TradeService(TradeRepo tradeRepo, PlayerService playerService, StickerService stickerService) {
        this.tradeRepo = tradeRepo;
        this.playerService = playerService;
        this.stickerService = stickerService;
    }

    // Get all trades
    public List<Trade> getAllTrades() {
        return tradeRepo.findAll();
    }

    // Get a trade by ID
    public Optional<Trade> getTradeById(UUID id) {
        return tradeRepo.findById(id);
    }

    // Create a new trade
    @Transactional
    public Trade createTrade(UUID authorId, Long requestedStickerId) {
        Player author = playerService.getPlayerById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + authorId + " does not exist."));

        Trade trade = new Trade();
        trade.setAuthor(author);
        trade.setRequestedStickerId(requestedStickerId);
        trade.setStatus(TradeStatus.ACTIVE); // Default status for new trades

        return tradeRepo.save(trade);
    }

    // Update an existing trade
    @Transactional
    public Trade updateTrade(UUID id, Trade updatedTrade) {
        if (!tradeRepo.existsById(id)) {
            throw new IllegalArgumentException("Trade with ID " + id + " does not exist.");
        }
        updatedTrade.setId(id); // Ensure the ID is set for the update
        return tradeRepo.save(updatedTrade);
    }

    // Delete a trade
    @Transactional
    public void deleteTrade(UUID id) {
        if (!tradeRepo.existsById(id)) {
            throw new IllegalArgumentException("Trade with ID " + id + " does not exist.");
        }
        tradeRepo.deleteById(id);
    }

    // Change the status of a trade
    @Transactional
    public Trade updateTradeStatus(UUID tradeId, TradeStatus newStatus) {
        Trade trade = tradeRepo.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade with ID " + tradeId + " does not exist."));

        trade.setStatus(newStatus);
        return tradeRepo.save(trade);
    }

    @Transactional
    public List<TradeInstance> findTrades(UUID playerId) {
        List<TradeInstance> tradeInstances = new ArrayList<>();


        Player player = playerService.getPlayerById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + playerId + " does not exist."));
        // Get trades of the player
        List<Trade> playerTrades = player.getTrades();
        // Get all inventory stickers of the player
        Set<Long> inventoryStickerIds = player.getInventory().stream()
                .map(Sticker::getId)
                .collect(Collectors.toSet());

        for (Trade trade : playerTrades) {
            Long wantedStickerId = trade.getRequestedStickerId();
            Sticker wantedSticker = stickerService.getStickerById(wantedStickerId)
                    .orElseThrow(() -> new IllegalArgumentException("Sticker with ID " + wantedStickerId + " does not exist."));

            // Iterate over owners of the wanted sticker
            wantedSticker.getOwners().stream()
                    .flatMap(owner -> owner.getTrades().stream())
                    .filter(stickerOwnerTrade -> inventoryStickerIds.contains(stickerOwnerTrade.getRequestedStickerId()))
                    .forEach(stickerOwnerTrade -> {
                        TradeInstance foundTrade = TradeInstance.builder()
                                .trader(stickerOwnerTrade.getAuthor())
                                .requestedSticker(wantedSticker)
                                .offer(player.getInventory().stream()
                                        .filter(sticker -> sticker.getId().equals(stickerOwnerTrade.getRequestedStickerId()))
                                        .findFirst()
                                        .orElse(null))
                                .build();
                        tradeInstances.add(foundTrade);
                    });
        }

        return tradeInstances;
    }
}
