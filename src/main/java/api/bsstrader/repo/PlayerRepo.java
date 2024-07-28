package api.bsstrader.repo;

import api.bsstrader.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepo extends JpaRepository<Player, UUID> {
}
