package api.bsstrader.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private StickerType type;
}
