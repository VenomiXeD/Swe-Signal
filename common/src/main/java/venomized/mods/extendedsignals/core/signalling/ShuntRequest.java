package venomized.mods.extendedsignals.core.signalling;

import net.minecraft.world.entity.player.Player;

public record ShuntRequest(Player requester, boolean front, float shuntRequestDistance) {
}
