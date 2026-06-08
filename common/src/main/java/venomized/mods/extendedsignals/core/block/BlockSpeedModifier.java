package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import venomized.mods.extendedsignals.core.blockentity.CoreBlockEntities;
import venomized.mods.extendedsignals.core.blockentity.railway.BlockEntitySignalSpeedModifier;

import java.util.Optional;

public class BlockSpeedModifier extends EdgePointBlock<BlockEntitySignalSpeedModifier> implements IWrenchable {
    /**
     * @param pProperties
     */
    public BlockSpeedModifier(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public Class<BlockEntitySignalSpeedModifier> getBlockEntityClass() {
        return BlockEntitySignalSpeedModifier.class;
    }

    /**
     * @return
     */
    @Override
    public BlockEntityType<? extends BlockEntitySignalSpeedModifier> getBlockEntityType() {
        return CoreBlockEntities.MODIFIER_SPEED.get();
    }

    /**
     * @param state
     * @param context
     * @return
     */
    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        Optional<BlockEntitySignalSpeedModifier> be =
                this.getBlockEntityOptional(context.getLevel(), context.getClickedPos());
        be.ifPresent(BlockEntitySignalSpeedModifier::onWrenched);

        return be.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}
