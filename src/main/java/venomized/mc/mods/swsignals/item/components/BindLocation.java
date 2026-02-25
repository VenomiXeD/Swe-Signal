package venomized.mc.mods.swsignals.item.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;

public record BindLocation(BlockPos bindStart) {
    public static final Codec<BindLocation> CODEC = BlockPos.CODEC.xmap(BindLocation::new, BindLocation::bindStart);
    public static final StreamCodec<ByteBuf, BindLocation> STREAM_CODEC = BlockPos.STREAM_CODEC.map(BindLocation::new, BindLocation::bindStart);
}
