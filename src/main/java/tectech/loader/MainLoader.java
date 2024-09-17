package tectech.loader;

import static gregtech.api.enums.Mods.NewHorizonsCoreMod;
import static gregtech.api.enums.Mods.TwilightForest;
import static tectech.TecTech.LOGGER;
import static tectech.TecTech.creativeTabTecTech;
import static tectech.TecTech.proxy;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import cpw.mods.fml.common.ProgressManager;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTRecipe;
import tectech.TecTech;
import tectech.loader.gui.CreativeTabTecTech;
import tectech.loader.recipe.BaseRecipeLoader;
import tectech.loader.recipe.ResearchStationAssemblyLine;
import tectech.loader.thing.CoverLoader;
import tectech.loader.thing.MachineLoader;
import tectech.loader.thing.ThingsLoader;
import tectech.thing.casing.TTCasingsContainer;
import tectech.thing.metaTileEntity.Textures;

@SuppressWarnings("deprecation")
public final class MainLoader {

    public static DamageSource microwaving;

    private MainLoader() {}

    public static void preLoad() {
        creativeTabTecTech = new CreativeTabTecTech("TecTech");

        // set expanded texture arrays for tiers
        try {
            Textures.run();
        } catch (Throwable t) {
            LOGGER.error("Loading textures...", t);
        }
    }

    public static void load() {
        ProgressManager.ProgressBar progressBarLoad = ProgressManager.push("TecTech Loader", 6);

        progressBarLoad.step("Regular Things");
        new ThingsLoader().run();
        LOGGER.info("Block/Item Init Done");

        progressBarLoad.step("Machine Things");
        new MachineLoader().run();
        LOGGER.info("Machine Init Done");

        progressBarLoad.step("Cover Things");
        new CoverLoader().run();
        LOGGER.info("Cover Init Done");

        progressBarLoad.step("Add damage types");
        microwaving = new DamageSource("microwaving").setDamageBypassesArmor();
        LOGGER.info("Damage types addition Done");

        progressBarLoad.step("Register Packet Dispatcher");
        new NetworkDispatcher();
        LOGGER.info("Packet Dispatcher registered");

        progressBarLoad.step("Register GUI Handler");
        proxy.registerRenderInfo();
        LOGGER.info("GUI Handler registered");

        ProgressManager.pop(progressBarLoad);
    }

    public static void postLoad() {
        ProgressManager.ProgressBar progressBarPostLoad = ProgressManager.push("TecTech Post Loader", 4);

        progressBarPostLoad.step("Dreamcraft Compatibility");
        if (NewHorizonsCoreMod.isModLoaded()) {
            try {
                Class<?> clazz = Class.forName("com.dreammaster.gthandler.casings.GT_Container_CasingsNH");
                TTCasingsContainer.sBlockCasingsNH = (Block) clazz.getField("sBlockCasingsNH")
                    .get(null);

                if (TTCasingsContainer.sBlockCasingsNH == null) {
                    throw new NullPointerException("sBlockCasingsNH Is not set at this time");
                }
            } catch (Exception e) {
                throw new Error("Unable to get NH casings", e);
            }
        }

        progressBarPostLoad.step("Recipes");
        new BaseRecipeLoader().run();
        TecTech.LOGGER.info("Recipe Init Done");

        if (!ConfigHandler.features.DISABLE_BLOCK_HARDNESS_NERF) {
            progressBarPostLoad.step("Nerf blocks blast resistance");
            adjustTwilightBlockResistance();
            TecTech.LOGGER.info("Blocks nerf done");
        } else {
            progressBarPostLoad.step("Do not nerf blocks blast resistance");
            TecTech.LOGGER.info("Blocks were not nerfed");
        }
    }

    private static void safeSetResistance(Block block, float resistance) {
        if (block != null) {
            block.setResistance(resistance);
        }
    }

    private static void adjustTwilightBlockResistance() {
        if (TwilightForest.isModLoaded()) {
            safeSetResistance(GameRegistry.findBlock("TwilightForest", "tile.TFShield"), 30);
            safeSetResistance(GameRegistry.findBlock("TwilightForest", "tile.TFThorns"), 10);
            safeSetResistance(GameRegistry.findBlock("TwilightForest", "tile.TFTowerTranslucent"), 30);
            safeSetResistance(GameRegistry.findBlock("TwilightForest", "tile.TFDeadrock"), 5);
        }
    }

    public static void onLoadCompleted() {
        new ResearchStationAssemblyLine().runLateRecipes();
    }
}
