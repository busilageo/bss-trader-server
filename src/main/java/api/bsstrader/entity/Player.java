package api.bsstrader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    private Set<Sticker> inventory;
    private List<Trade> trades;
}