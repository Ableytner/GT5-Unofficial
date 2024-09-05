package tectech.thing.casing;

import static com.google.common.math.LongMath.pow;
import static gregtech.api.enums.GTValues.AuthorColen;
import static gregtech.api.util.GTUtility.formatNumbers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import gregtech.api.util.GTLanguageManager;
import gregtech.common.blocks.ItemCasingsAbstract;

public class ItemCasingsSpacetime extends ItemCasingsAbstract {

    public ItemCasingsSpacetime(Block par1) {
        super(par1);
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List<String> aList, boolean aF3_H) {
        switch (aStack.getItemDamage()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                aList.add(
                    GTLanguageManager.addStringLocalization(
                        "EOH.Spacetime.Standard.Tooltip.0",
                        "Supports an internal spacetime volume of up to ")
                        + formatNumbers(pow(10, 5 + aStack.getItemDamage()))
                        + "km³.");
                aList.add(
                    EnumChatFormatting.AQUA.toString() + EnumChatFormatting.BOLD
                        + GTLanguageManager.addStringLocalization(
                            "EOH.Spacetime.Standard.Tooltip.1",
                            "Capable of running recipes up to tier ")
                        + (aStack.getItemDamage() + 1));
                break;
            default:
                aList.add(
                    EnumChatFormatting.RED.toString() + EnumChatFormatting.BOLD
                        + GTLanguageManager
                            .addStringLocalization("EOH.TimeDilation.Error.Tooltip", "Error, report to GTNH team"));
        }
        aList.add(AuthorColen);
    }
}
