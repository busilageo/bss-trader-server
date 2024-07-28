package api.bsstrader.repo;

import api.bsstrader.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TradeRepo extends JpaRepository<Trade, UUID> {
}
