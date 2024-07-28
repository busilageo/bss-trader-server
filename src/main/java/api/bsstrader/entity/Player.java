package api.bsstrader.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long robloxId;
    private String username;

    @ManyToMany
    @JoinTable(
            name = "sticker_owners",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "sticker_id")
    )
    private Set<Sticker> inventory;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Trade> trades;
}