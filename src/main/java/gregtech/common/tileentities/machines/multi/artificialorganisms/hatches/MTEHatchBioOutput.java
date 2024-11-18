package gregtech.common.tileentities.machines.multi.artificialorganisms.hatches;

import java.util.HashSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEHatch;
import gregtech.api.objects.ArtificialOrganism;
import gregtech.common.tileentities.machines.multi.artificialorganisms.MTEBioPipe;
import gregtech.common.tileentities.machines.multi.artificialorganisms.util.IConnectsToBioPipe;

public class MTEHatchBioOutput extends MTEHatch implements IConnectsToBioPipe {

    public HashSet<IConnectsToBioPipe> pipenetwork;
    ArtificialOrganism currentSpecies = new ArtificialOrganism();

    public MTEHatchBioOutput(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 0, "Distributes Artificial Organisms");
    }

    public MTEHatchBioOutput(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 0, aDescription, aTextures);
    }

    @Override
    public ITexture[] getTexturesActive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier] };
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
        return new ITexture[] { aBaseTexture, Textures.BlockIcons.OVERLAYS_ENERGY_OUT_MULTI[mTier] };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new MTEHatchBioOutput(mName, mTier, mDescriptionArray, mTextures);
    }

    @Override
    public boolean canConnect(ForgeDirection side) {
        return isInputFacing(side);
    }

    private boolean rescanQueued = false;

    public void queueRescan() {
        rescanQueued = true;
    }

    private void rescan() {
        if (pipenetwork != null) {
            for (IConnectsToBioPipe node : pipenetwork) {
                if (node instanceof MTEBioPipe) ((MTEBioPipe) node).networkOutput = null;
                if (node instanceof MTEHatchBioInput) ((MTEHatchBioInput) node).networkOutput = null;
            }
        }
        pipenetwork = getConnected(this, new HashSet<>());
    }

    @Override
    public HashSet<IConnectsToBioPipe> getConnected(MTEHatchBioOutput output, HashSet<IConnectsToBioPipe> connections) {
        connections.add(this);
        IGregTechTileEntity baseTE = getBaseMetaTileEntity();
        TileEntity next = baseTE.getTileEntityAtSide(baseTE.getFrontFacing());
        if (next != null) {
            IMetaTileEntity meta = ((IGregTechTileEntity) next).getMetaTileEntity();
            if (meta instanceof IConnectsToBioPipe)
                return ((IConnectsToBioPipe) meta).getConnected(output, connections);
        }
        return null;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        rescan();
        super.onFirstTick(aBaseMetaTileEntity);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aTick % 40 == 0 && rescanQueued) {
            rescan();
            rescanQueued = false;
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public boolean isSimpleMachine() {
        return true;
    }

    @Override
    public boolean isComponentsInputFacing(ForgeDirection side) {
        return false;
    }

    @Override
    public boolean isFacingValid(ForgeDirection facing) {
        return true;
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.BIO;
    }
}
