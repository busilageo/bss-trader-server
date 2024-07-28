package api.bsstrader.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepo extends JpaRepository<Player, UUID> {
}
