package api.bsstrader.repo;

import api.bsstrader.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepo extends JpaRepository<Sticker, Long> {
}
