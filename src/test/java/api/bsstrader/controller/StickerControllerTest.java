package api.bsstrader.controller;

import api.bsstrader.entity.Sticker;
import api.bsstrader.entity.StickerType;
import api.bsstrader.service.StickerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StickerController.class)
public class StickerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StickerService stickerService;

    @InjectMocks
    private StickerController stickerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stickerController).build();
    }

    @Test
    void testGetAllStickers() throws Exception {
        Sticker sticker1 = new Sticker(1L, "Sticker1", "Description1", StickerType.HIVE_STICKER);
        Sticker sticker2 = new Sticker(2L, "Sticker2", "Description2", StickerType.CUB_SKIN);
        when(stickerService.getAllStickers()).thenReturn(Arrays.asList(sticker1, sticker2));

        mockMvc.perform(get("/api/v1/stickers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Sticker1"))
                .andExpect(jsonPath("$[1].name").value("Sticker2"));

        verify(stickerService, times(1)).getAllStickers();
    }

    @Test
    void testGetStickerByIdFound() throws Exception {
        Sticker sticker = new Sticker(1L, "Sticker1", "Description1", StickerType.HIVE_STICKER);
        when(stickerService.getStickerById(1L)).thenReturn(Optional.of(sticker));

        mockMvc.perform(get("/api/v1/stickers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Sticker1"));

        verify(stickerService, times(1)).getStickerById(1L);
    }

    @Test
    void testGetStickerByIdNotFound() throws Exception {
        when(stickerService.getStickerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/stickers/1"))
                .andExpect(status().isNotFound());

        verify(stickerService, times(1)).getStickerById(1L);
    }

    @Test
    void testCreateSticker() throws Exception {
        Sticker sticker = new Sticker(1L, "Sticker1", "Description1", StickerType.HIVE_STICKER);
        when(stickerService.createSticker(any(Sticker.class))).thenReturn(sticker);

        mockMvc.perform(post("/api/v1/stickers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Sticker1\",\"description\":\"Description1\",\"type\":\"HIVE_STICKER\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Sticker1"));

        verify(stickerService, times(1)).createSticker(any(Sticker.class));
    }

    @Test
    void testUpdateStickerSuccess() throws Exception {
        Sticker updatedSticker = new Sticker(1L, "UpdatedSticker", "UpdatedDescription", StickerType.HIVE_STICKER);
        when(stickerService.updateSticker(eq(1L), any(Sticker.class))).thenReturn(updatedSticker);

        mockMvc.perform(put("/api/v1/stickers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedSticker\",\"description\":\"UpdatedDescription\",\"type\":\"HIVE_STICKER\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("UpdatedSticker"));

        verify(stickerService, times(1)).updateSticker(eq(1L), any(Sticker.class));
    }

    @Test
    void testUpdateStickerNotFound() throws Exception {
        when(stickerService.updateSticker(eq(1L), any(Sticker.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(put("/api/v1/stickers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedSticker\",\"description\":\"UpdatedDescription\",\"type\":\"TYPE1\"}"))
                .andExpect(status().isNotFound());

        verify(stickerService, times(1)).updateSticker(eq(1L), any(Sticker.class));
    }

    @Test
    void testDeleteStickerSuccess() throws Exception {
        doNothing().when(stickerService).deleteSticker(1L);

        mockMvc.perform(delete("/api/v1/stickers/1"))
                .andExpect(status().isNoContent());

        verify(stickerService, times(1)).deleteSticker(1L);
    }

    @Test
    void testDeleteStickerNotFound() throws Exception {
        doThrow(new IllegalArgumentException()).when(stickerService).deleteSticker(1L);

        mockMvc.perform(delete("/api/v1/stickers/1"))
                .andExpect(status().isNotFound());

        verify(stickerService, times(1)).deleteSticker(1L);
    }
}
