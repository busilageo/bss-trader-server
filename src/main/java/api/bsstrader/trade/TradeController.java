package api.bsstrader.trade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    // Get all trades
    @GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return ResponseEntity.ok(trades);
    }

    // Get trade by ID
    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable UUID id) {
        Optional<Trade> trade = tradeService.getTradeById(id);
        return trade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/find_trades")
    public ResponseEntity<List<TradeInstance>> findTrades(@PathVariable UUID id) {
        List<TradeInstance> tradeInstances = tradeService.findTrades(id);
        return ResponseEntity.ok(tradeInstances);
    }

    // Create a new trade
    @PostMapping
    public ResponseEntity<Trade> createTrade(@RequestParam UUID authorId, @RequestParam Long requestedStickerId) {
        try {
            Trade createdTrade = tradeService.createTrade(authorId, requestedStickerId);
            return ResponseEntity.ok(createdTrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update an existing trade
    @PutMapping("/{id}")
    public ResponseEntity<Trade> updateTrade(@PathVariable UUID id, @RequestBody Trade updatedTrade) {
        try {
            Trade trade = tradeService.updateTrade(id, updatedTrade);
            return ResponseEntity.ok(trade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a trade
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable UUID id) {
        try {
            tradeService.deleteTrade(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Update trade status
    @PatchMapping("/{id}/status")
    public ResponseEntity<Trade> updateTradeStatus(@PathVariable UUID id, @RequestParam TradeStatus status) {
        try {
            Trade updatedTrade = tradeService.updateTradeStatus(id, status);
            return ResponseEntity.ok(updatedTrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
