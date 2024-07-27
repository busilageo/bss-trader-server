package api.bsstrader.repo;

import api.bsstrader.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepo extends JpaRepository<Sticker, Long> {
}
