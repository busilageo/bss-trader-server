package api.bsstrader.sticker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepo extends JpaRepository<Sticker, Long> {
}
