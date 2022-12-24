package com.github.technus.tectech.thing.casing;

import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.util.CommonValues.EOH_TIER_FANCY_NAMES;
import static net.minecraft.util.EnumChatFormatting.RESET;

import com.github.technus.tectech.thing.CustomItemList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Textures;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Block_Casings_Abstract;
import gregtech.common.blocks.GT_Material_Casings;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SuppressWarnings("SpellCheckingInspection")
public class SpacetimeCompressionFieldCasing extends GT_Block_Casings_Abstract {
    private static IIcon textureTier0;
    private static final int maxBlockTier = 9;

    private static final byte START_INDEX = 16;

    public SpacetimeCompressionFieldCasing() {
        super(
                GT_Item_Casings_Spacetime.class,
                "gt.spacetime_compression_field_generator",
                GT_Material_Casings.INSTANCE);
        for (byte b = 0; b < 16; b = (byte) (b + 1)) {
            Textures.BlockIcons.casingTexturePages[texturePage][b + START_INDEX] =
                    new GT_CopiedBlockTexture(this, 6, b);
        }

        for (int i = 0; i < maxBlockTier; i++) {
            GT_LanguageManager.addStringLocalization(
                    getUnlocalizedName() + "." + i + ".name",
                    EOH_TIER_FANCY_NAMES[i] + RESET + " Spacetime Compression Field Generator");
        }

        CustomItemList.SpacetimeCompressionFieldGeneratorTier0.set(new ItemStack(this, 1, 0));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier1.set(new ItemStack(this, 1, 1));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier2.set(new ItemStack(this, 1, 2));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier3.set(new ItemStack(this, 1, 3));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier4.set(new ItemStack(this, 1, 4));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier5.set(new ItemStack(this, 1, 5));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier6.set(new ItemStack(this, 1, 6));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier7.set(new ItemStack(this, 1, 7));
        CustomItemList.SpacetimeCompressionFieldGeneratorTier8.set(new ItemStack(this, 1, 8));
    }

    @Override
    public void registerBlockIcons(IIconRegister aIconRegister) {
        textureTier0 = aIconRegister.registerIcon("gregtech:iconsets/EM_DIM");
    }

    @Override
    public IIcon getIcon(int aSide, int aMeta) {
        switch (aMeta) {
            case 0:
                return textureTier0;
            case 1:
                return textureTier0;
            case 2:
                return textureTier0;
            case 3:
                return textureTier0;
            case 4:
                return textureTier0;
            case 5:
                return textureTier0;
            case 6:
                return textureTier0;
            case 7:
                return textureTier0;
            case 8:
                return textureTier0;
            default:
                return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
        return getIcon(aSide, tMeta);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList) {
        for (int i = 0; i < maxBlockTier; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }
}
