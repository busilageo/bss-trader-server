package api.bsstrader.service;

import api.bsstrader.sticker.Sticker;
import api.bsstrader.sticker.StickerRepo;
import api.bsstrader.sticker.StickerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StickerServiceTest {

    @Mock
    private StickerRepo stickerRepo;

    @InjectMocks
    private StickerService stickerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStickers() {
        Sticker sticker1 = new Sticker();
        Sticker sticker2 = new Sticker();
        List<Sticker> stickers = Arrays.asList(sticker1, sticker2);

        when(stickerRepo.findAll()).thenReturn(stickers);

        List<Sticker> result = stickerService.getAllStickers();

        assertEquals(2, result.size());
        verify(stickerRepo, times(1)).findAll();
    }

    @Test
    public void testGetStickerById() {
        Sticker sticker = new Sticker();
        when(stickerRepo.findById(1L)).thenReturn(Optional.of(sticker));

        Optional<Sticker> result = stickerService.getStickerById(1L);

        assertTrue(result.isPresent());
        verify(stickerRepo, times(1)).findById(1L);
    }

    @Test
    public void testGetStickerById_NotFound() {
        when(stickerRepo.findById(1L)).thenReturn(Optional.empty());

        Optional<Sticker> result = stickerService.getStickerById(1L);

        assertFalse(result.isPresent());
        verify(stickerRepo, times(1)).findById(1L);
    }

    @Test
    public void testCreateSticker() {
        Sticker sticker = new Sticker();
        when(stickerRepo.save(sticker)).thenReturn(sticker);

        Sticker result = stickerService.createSticker(sticker);

        assertNotNull(result);
        verify(stickerRepo, times(1)).save(sticker);
    }

    @Test
    public void testUpdateSticker() {
        Sticker updatedSticker = new Sticker();
        when(stickerRepo.existsById(1L)).thenReturn(true);
        when(stickerRepo.save(updatedSticker)).thenReturn(updatedSticker);

        Sticker result = stickerService.updateSticker(1L, updatedSticker);

        assertNotNull(result);
        verify(stickerRepo, times(1)).existsById(1L);
        verify(stickerRepo, times(1)).save(updatedSticker);
    }

    @Test
    public void testUpdateSticker_NotFound() {
        Sticker updatedSticker = new Sticker();
        when(stickerRepo.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> stickerService.updateSticker(1L, updatedSticker));

        verify(stickerRepo, times(1)).existsById(1L);
        verify(stickerRepo, times(0)).save(updatedSticker);
    }

    @Test
    public void testDeleteSticker() {
        when(stickerRepo.existsById(1L)).thenReturn(true);
        doNothing().when(stickerRepo).deleteById(1L);

        stickerService.deleteSticker(1L);

        verify(stickerRepo, times(1)).existsById(1L);
        verify(stickerRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteSticker_NotFound() {
        when(stickerRepo.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> stickerService.deleteSticker(1L));

        verify(stickerRepo, times(1)).existsById(1L);
        verify(stickerRepo, times(0)).deleteById(1L);
    }
}
