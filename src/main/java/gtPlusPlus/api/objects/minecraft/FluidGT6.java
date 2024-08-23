package gtPlusPlus.api.objects.minecraft;

import static gregtech.api.enums.Mods.GTPlusPlus;

import net.minecraftforge.fluids.Fluid;

import gregtech.api.GregTech_API;

public class FluidGT6 extends Fluid implements Runnable {

    private final short[] mRGBa;
    public final String mTextureName;

    public FluidGT6(final String aName, final String aTextureName, final short[] aRGBa) {
        super(aName);
        this.mRGBa = aRGBa;
        this.mTextureName = aTextureName;
        if (GregTech_API.sGTBlockIconload != null) {
            GregTech_API.sGTBlockIconload.add(this);
        }
    }

    @Override
    public int getColor() {
        return (Math.max(0, Math.min(255, this.mRGBa[0])) << 16) | (Math.max(0, Math.min(255, this.mRGBa[1])) << 8)
            | Math.max(0, Math.min(255, this.mRGBa[2]));
    }

    @Override
    public void run() {
        this.setIcons(GregTech_API.sBlockIcons.registerIcon(GTPlusPlus.ID + ":" + "fluids/fluid." + this.mTextureName));
    }
}
