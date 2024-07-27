package api.bsstrader.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
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

    @OneToMany(mappedBy = "author")
    private List<Trade> trades;
}