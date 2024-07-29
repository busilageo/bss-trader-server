package api.bsstrader.trade;

import api.bsstrader.player.Player;
import api.bsstrader.sticker.Sticker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeInstance {
    Player trader;
    Sticker requestedSticker;
    Sticker offer;
}
