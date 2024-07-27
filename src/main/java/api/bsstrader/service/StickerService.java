package api.bsstrader.service;

import api.bsstrader.entity.Sticker;
import api.bsstrader.repo.StickerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StickerService {
    private final StickerRepo stickerRepo;

    public StickerService(StickerRepo stickerRepo) {
        this.stickerRepo = stickerRepo;
    }

    // Get all stickers
    public List<Sticker> getAllStickers() {
        return stickerRepo.findAll();
    }

    // Get a sticker by ID
    public Optional<Sticker> getStickerById(Long id) {
        return stickerRepo.findById(id);
    }

    // Create a new sticker
    @Transactional
    public Sticker createSticker(Sticker sticker) {
        return stickerRepo.save(sticker);
    }

    // Update an existing sticker
    @Transactional
    public Sticker updateSticker(Long id, Sticker updatedSticker) {
        if (!stickerRepo.existsById(id)) {
            throw new IllegalArgumentException("Sticker with ID " + id + " does not exist.");
        }
        updatedSticker.setId(id); // Ensure the ID is set for the update
        return stickerRepo.save(updatedSticker);
    }

    // Delete a sticker
    @Transactional
    public void deleteSticker(Long id) {
        if (!stickerRepo.existsById(id)) {
            throw new IllegalArgumentException("Sticker with ID " + id + " does not exist.");
        }
        stickerRepo.deleteById(id);
    }
}
