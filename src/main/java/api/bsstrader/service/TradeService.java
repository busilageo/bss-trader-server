package api.bsstrader.service;

import api.bsstrader.entity.Player;
import api.bsstrader.entity.Trade;
import api.bsstrader.entity.TradeStatus;
import api.bsstrader.repo.TradeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TradeService {
    private final TradeRepo tradeRepo;
    private final PlayerService playerService; // Inject PlayerService for player operations

    public TradeService(TradeRepo tradeRepo, PlayerService playerService) {
        this.tradeRepo = tradeRepo;
        this.playerService = playerService;
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
}
