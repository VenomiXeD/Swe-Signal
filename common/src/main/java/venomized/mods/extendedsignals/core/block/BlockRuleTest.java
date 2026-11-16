package venomized.mods.extendedsignals.core.block;

import com.simibubi.create.foundation.utility.DistExecutor;
import net.createmod.catnip.platform.NeoForgeHooksHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import venomized.mods.extendedsignals.core.blockentity.IConfigurableRuleBlockEntity;
import venomized.mods.extendedsignals.core.client.screen.ScreenRuleEditor;

public class BlockRuleTest extends BlockWithEntityWrapper {
    public BlockRuleTest(Properties properties) {
        super(properties);
    }

    /**
     * @param state
     * @param level
     * @param pos
     * @param player
     * @param hitResult
     * @return
     */
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide && level.getBlockEntity(hitResult.getBlockPos()) instanceof IConfigurableRuleBlockEntity ruleBlockEntity) {
            Minecraft.getInstance().setScreen(new ScreenRuleEditor(ruleBlockEntity));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
