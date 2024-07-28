package api.bsstrader.trade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TradeRepo extends JpaRepository<Trade, UUID> {
}
