package ten3.core.machine.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import ten3.core.machine.CableBased;
import ten3.lib.capability.net.BatteryWayFinding;
import ten3.lib.tile.mac.CmTileMachine;
import ten3.lib.tile.option.Type;

public class CableTile extends CmTileMachine {

    public CableTile(BlockPos pos, BlockState state) {

        super(pos, state);

    }

    @Override
    public Type typeOf() {
        return Type.CABLE;
    }

    public int getCapacity() {
        return kFE(1);
    }

    boolean eneWFS;
    int hangtime;

    @Override
    public void update() {

        if(getTileAliveTime() % 10 == 0) {
            ((CableBased) getBlockState().getBlock()).update(level, worldPosition);
            BatteryWayFinding.updateNet(this);
        }

        eneWFS = hangtime > 0;
        hangtime--;

        reflection.setActive(eneWFS && signalAllowRun());

    }

    public void hangUp() {
        hangtime = 15;
    }

    @Override
    public void initHandlers()
    {
        handlerEnergyNull = new BatteryWayFinding(null, this);
        for(Direction d : Direction.values()) {
            handlerEnergy.put(d, new BatteryWayFinding(d, this));
        }
    }

    @Override
    protected boolean hasFaceCapability(Capability<?> cap, Direction d) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return false;
        }
        return super.hasFaceCapability(cap, d);
    }

}
