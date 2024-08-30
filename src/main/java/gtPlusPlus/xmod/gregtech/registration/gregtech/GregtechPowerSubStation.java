package gtPlusPlus.xmod.gregtech.registration.gregtech;

import static gregtech.api.enums.MetaTileEntityIDs.Hatch_Input_Battery_EV;
import static gregtech.api.enums.MetaTileEntityIDs.Hatch_Input_Battery_MV;
import static gregtech.api.enums.MetaTileEntityIDs.Hatch_Output_Battery_EV;
import static gregtech.api.enums.MetaTileEntityIDs.Hatch_Output_Battery_MV;
import static gregtech.api.enums.MetaTileEntityIDs.PowerSubStation;

import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_ModHandler;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.lib.CORE;
import gtPlusPlus.core.recipe.common.CI;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBattery;
import gtPlusPlus.xmod.gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBattery;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.storage.GregtechMetaTileEntity_PowerSubStationController;

public class GregtechPowerSubStation {

    public static void run() {
        Logger.INFO("Gregtech5u Content | Registering Power Substation Node.");
        if (CORE.ConfigSwitches.enableMultiblock_PowerSubstation) {
            run1();
        }
    }

    private static void run1() {
        // Steam Condensors
        GregtechItemList.PowerSubStation.set(
            new GregtechMetaTileEntity_PowerSubStationController(
                PowerSubStation.ID,
                "substation.01.input.single",
                "Power Station Control Node").getStackForm(1L));
        int tID = 886;
        GregtechItemList.Hatch_Input_Battery_MV.set(
            new GT_MetaTileEntity_Hatch_InputBattery(
                Hatch_Input_Battery_MV.ID,
                "hatch.input_battery.tier.00",
                "Charging Bus (MV)",
                2).getStackForm(1L));
        GregtechItemList.Hatch_Input_Battery_EV.set(
            new GT_MetaTileEntity_Hatch_InputBattery(
                Hatch_Input_Battery_EV.ID,
                "hatch.input_battery.tier.01",
                "Charging Bus (EV)",
                4).getStackForm(1L));

        GregtechItemList.Hatch_Output_Battery_MV.set(
            new GT_MetaTileEntity_Hatch_OutputBattery(
                Hatch_Output_Battery_MV.ID,
                "hatch.output_battery.tier.00",
                "Discharging Bus (MV)",
                2).getStackForm(1L));
        GregtechItemList.Hatch_Output_Battery_EV.set(
            new GT_MetaTileEntity_Hatch_OutputBattery(
                Hatch_Output_Battery_EV.ID,
                "hatch.output_battery.tier.01",
                "Discharging Bus (EV)",
                4).getStackForm(1L));

        GT_ModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Input_Battery_MV.get(1L),
            CI.bitsd,
            new Object[] { "C", "M", 'M', ItemList.Hull_MV, 'C', ItemList.Battery_Buffer_2by2_MV });
        GT_ModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Input_Battery_EV.get(1L),
            CI.bitsd,
            new Object[] { "C", "M", 'M', ItemList.Hull_EV, 'C', ItemList.Battery_Buffer_4by4_EV });
        GT_ModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Output_Battery_MV.get(1L),
            CI.bitsd,
            new Object[] { "M", "C", 'M', ItemList.Hull_MV, 'C', ItemList.Battery_Buffer_2by2_MV });
        GT_ModHandler.addCraftingRecipe(
            GregtechItemList.Hatch_Output_Battery_EV.get(1L),
            CI.bitsd,
            new Object[] { "M", "C", 'M', ItemList.Hull_EV, 'C', ItemList.Battery_Buffer_4by4_EV });
    }
}
