package gregtech.api.multitileentity.interfaces;

import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;

import gregtech.api.logic.PowerLogic;

public interface IMultiBlockController extends IMultiTileEntity, IMultiBlockFluidHandler, IMultiBlockInventory {

    boolean checkStructure(boolean aForceReset);

    /** Set the structure as having changed, and trigger an update */
    void onStructureChange();

    @Override
    ChunkCoordinates getCoords();

    FluidStack getDrainableFluid(byte aSide);

    FluidStack getDrainableFluid(byte aSide, Fluid fluid);

    boolean isLiquidInput(byte aSide);

    boolean isLiquidOutput(byte aSide);

    void registerCoveredPartOnSide(final int aSide, IMultiBlockPart part);

    void unregisterCoveredPartOnSide(final int aSide, IMultiBlockPart part);

    void registerInventory(String aName, String aID, int aInventorySize, int aType);

    void unregisterInventory(String aName, String aID, int aType);

    void changeInventoryName(String aName, String aID, int aType);

    PowerLogic getPowerLogic(IMultiBlockPart part, ForgeDirection side);

    ModularWindow createWindowGUI(UIBuildContext buildContext);
}
