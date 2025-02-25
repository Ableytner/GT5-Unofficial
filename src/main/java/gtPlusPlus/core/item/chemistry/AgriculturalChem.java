package gtPlusPlus.core.item.chemistry;

import static gregtech.api.recipe.RecipeMaps.centrifugeRecipes;
import static gregtech.api.recipe.RecipeMaps.compressorRecipes;
import static gregtech.api.recipe.RecipeMaps.mixerRecipes;
import static gregtech.api.util.GTRecipeBuilder.SECONDS;
import static gregtech.api.util.GTRecipeConstants.FUEL_VALUE;
import static gregtech.api.util.GTRecipeConstants.UniversalChemical;
import static gtPlusPlus.api.recipe.GTPPRecipeMaps.chemicalDehydratorRecipes;
import static gtPlusPlus.api.recipe.GTPPRecipeMaps.semiFluidFuels;
import static gtPlusPlus.core.util.minecraft.ItemUtils.hideItemFromNEI;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Optional;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.api.objects.minecraft.ItemPackage;
import gtPlusPlus.core.fluids.GTPPFluids;
import gtPlusPlus.core.item.circuit.GTPPIntegratedCircuitItem;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.minecraft.FluidUtils;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.plugin.agrichem.BioRecipes;
import gtPlusPlus.plugin.agrichem.item.algae.ItemAgrichemBase;
import gtPlusPlus.plugin.agrichem.item.algae.ItemAlgaeBase;

public class AgriculturalChem extends ItemPackage {

    /**
     * Items
     */

    // Manure Byproducts
    public static Item dustManureByproducts;
    // Organic Fertilizer
    public static Item dustOrganicFertilizer;
    // Dirt
    public static Item dustDirt;

    // Poop Juice
    // vv - Centrifuge
    // Manure Slurry && Manure Byproducts -> (Elements) Centrifuge to several tiny
    // piles
    // vv - Chem Reactor - Add Peat, Meat
    // Organic Fertilizer
    // vv - Dehydrate
    // Fertilizer

    // Poop Juice
    // vv - Mixer - Add Blood, Bone, Meat (1000L Poo, 200L Blood, x2 Bone, x3 Meat)
    // Fertile Manure Slurry
    // vv - Chem Reactor - Add Peat x1.5
    // Organic Fertilizer x3
    // vv - Dehydrate
    // Fertilizer

    public static Item mAlgae;
    public static Item mAgrichemItem1;

    /*
     * 0 - Algae Biomass 1 - Green Algae Biomass 2 - Brown Algae Biomass 3 - Golden-Brown Algae Biomass 4 - Red Algae
     * Biomass 5 - Cellulose Fiber 6 - Golden-Brown Cellulose Fiber 7 - Red Cellulose Fiber 8 - Compost 9 - Wood Pellet
     * 10 - Wood Brick 11 - Cellulose Pulp 12 - Raw Bio Resin 13 - Catalyst Carrier 14 - Green Metal Catalyst 15 -
     * Alginic Acid 16 - Alumina 17 - Aluminium Pellet 18 - Sodium Aluminate 19 - Sodium Hydroxide // Exists in Newer GT
     * 20 - Sodium Carbonate 21 - Lithium Chloride 22 - Pellet Mold 23 - Clean Aluminium Mix 24 - Pinecone
     */

    public static ItemStack mAlgaeBiosmass;
    public static ItemStack mGreenAlgaeBiosmass;
    public static ItemStack mBrownAlgaeBiosmass;
    public static ItemStack mGoldenBrownAlgaeBiosmass;
    public static ItemStack mRedAlgaeBiosmass;
    public static ItemStack mCelluloseFiber;
    public static ItemStack mGoldenBrownCelluloseFiber;
    public static ItemStack mRedCelluloseFiber;
    public static ItemStack mCompost;
    public static ItemStack mWoodPellet;
    public static ItemStack mWoodBrick;
    public static ItemStack mCellulosePulp;
    public static ItemStack mRawBioResin;
    public static ItemStack mCatalystCarrier;
    public static ItemStack mGreenCatalyst;
    public static ItemStack mAlginicAcid;
    public static ItemStack mAlumina;
    public static ItemStack mAluminiumPellet;
    public static ItemStack mSodiumAluminate;
    public static ItemStack mSodiumHydroxide;
    public static ItemStack mSodiumCarbonate;
    public static ItemStack mLithiumChloride;
    public static ItemStack mPelletMold;
    public static ItemStack mCleanAluminiumMix;
    public static ItemStack mPinecone;
    public static ItemStack mCrushedPine;

    @Override
    public void items() {
        // Nitrogen, Ammonium Nitrate, Phosphates, Calcium, Copper, Carbon
        dustManureByproducts = ItemUtils.generateSpecialUseDusts(
            "ManureByproducts",
            "Manure Byproduct",
            "(N2H4O3)N2P2Ca3CuC8",
            Utils.rgbtoHexValue(110, 75, 25))[0];

        // Basically Guano
        dustOrganicFertilizer = ItemUtils.generateSpecialUseDusts(
            "OrganicFertilizer",
            "Organic Fertilizer",
            "Ca5(PO4)3(OH)",
            Utils.rgbtoHexValue(240, 240, 240))[0];

        // Dirt Dust :)
        dustDirt = ItemUtils.generateSpecialUseDusts("Dirt", "Dried Earth", Utils.rgbtoHexValue(65, 50, 15))[0];

        mAlgae = new ItemAlgaeBase();
        mAgrichemItem1 = new ItemAgrichemBase();

        // TODO Remove after 2.8
        Item bioSelector = new GTPPIntegratedCircuitItem("BioRecipeSelector", "bioscience/BioCircuit");
        hideItemFromNEI(new ItemStack(bioSelector));

        mAlgaeBiosmass = ItemUtils.simpleMetaStack(mAgrichemItem1, 0, 1);
        mGreenAlgaeBiosmass = ItemUtils.simpleMetaStack(mAgrichemItem1, 1, 1);
        mBrownAlgaeBiosmass = ItemUtils.simpleMetaStack(mAgrichemItem1, 2, 1);
        mGoldenBrownAlgaeBiosmass = ItemUtils.simpleMetaStack(mAgrichemItem1, 3, 1);
        mRedAlgaeBiosmass = ItemUtils.simpleMetaStack(mAgrichemItem1, 4, 1);
        mCelluloseFiber = ItemUtils.simpleMetaStack(mAgrichemItem1, 5, 1);
        mGoldenBrownCelluloseFiber = ItemUtils.simpleMetaStack(mAgrichemItem1, 6, 1);
        mRedCelluloseFiber = ItemUtils.simpleMetaStack(mAgrichemItem1, 7, 1);
        mCompost = ItemUtils.simpleMetaStack(mAgrichemItem1, 8, 1);
        mWoodPellet = ItemUtils.simpleMetaStack(mAgrichemItem1, 9, 1);
        mWoodBrick = ItemUtils.simpleMetaStack(mAgrichemItem1, 10, 1);
        mCellulosePulp = ItemUtils.simpleMetaStack(mAgrichemItem1, 11, 1);
        mRawBioResin = ItemUtils.simpleMetaStack(mAgrichemItem1, 12, 1);
        mCatalystCarrier = ItemUtils.simpleMetaStack(mAgrichemItem1, 13, 1);
        mGreenCatalyst = ItemUtils.simpleMetaStack(mAgrichemItem1, 14, 1);
        mAlginicAcid = ItemUtils.simpleMetaStack(mAgrichemItem1, 15, 1);
        mAlumina = ItemUtils.simpleMetaStack(mAgrichemItem1, 16, 1);
        mAluminiumPellet = ItemUtils.simpleMetaStack(mAgrichemItem1, 17, 1);
        mSodiumAluminate = ItemUtils.simpleMetaStack(mAgrichemItem1, 18, 1);

        /*
         * If It exists, don't add a new one.
         */
        if (OreDictionary.doesOreNameExist("dustSodiumHydroxide_GT5U")
            || OreDictionary.doesOreNameExist("dustSodiumHydroxide")) {
            List<ItemStack> aTest = OreDictionary.getOres("dustSodiumHydroxide", false);
            ItemStack aTestStack;
            if (aTest.isEmpty()) {
                aTest = OreDictionary.getOres("dustSodiumHydroxide_GT5U", false);
                if (aTest.isEmpty()) {
                    aTestStack = ItemUtils.simpleMetaStack(mAgrichemItem1, 19, 1);
                } else {
                    aTestStack = aTest.get(0);
                }
            } else {
                aTestStack = aTest.get(0);
            }
            mSodiumHydroxide = aTestStack;
        } else {
            mSodiumHydroxide = ItemUtils.simpleMetaStack(mAgrichemItem1, 19, 1);
        }
        mSodiumCarbonate = ItemUtils.simpleMetaStack(mAgrichemItem1, 20, 1);
        mLithiumChloride = ItemUtils.simpleMetaStack(mAgrichemItem1, 21, 1);
        mPelletMold = ItemUtils.simpleMetaStack(mAgrichemItem1, 22, 1);
        mCleanAluminiumMix = ItemUtils.simpleMetaStack(mAgrichemItem1, 23, 1);
        mPinecone = ItemUtils.simpleMetaStack(mAgrichemItem1, 24, 1);
        mCrushedPine = ItemUtils.simpleMetaStack(mAgrichemItem1, 25, 1);

        ItemUtils.addItemToOreDictionary(mGreenAlgaeBiosmass, "biomassGreenAlgae");
        ItemUtils.addItemToOreDictionary(mBrownAlgaeBiosmass, "biomassBrownAlgae");
        ItemUtils.addItemToOreDictionary(mGoldenBrownAlgaeBiosmass, "biomassGoldenBrownAlgae");
        ItemUtils.addItemToOreDictionary(mRedAlgaeBiosmass, "biomassRedAlgae");

        ItemUtils.addItemToOreDictionary(mCelluloseFiber, "fiberCellulose");
        ItemUtils.addItemToOreDictionary(mGoldenBrownCelluloseFiber, "fiberCellulose");
        ItemUtils.addItemToOreDictionary(mGoldenBrownCelluloseFiber, "fiberGoldenBrownCellulose");
        ItemUtils.addItemToOreDictionary(mRedCelluloseFiber, "fiberCellulose");
        ItemUtils.addItemToOreDictionary(mRedCelluloseFiber, "fiberRedCellulose");

        ItemUtils.addItemToOreDictionary(mWoodPellet, "pelletWood");
        ItemUtils.addItemToOreDictionary(mWoodBrick, "brickWood");
        ItemUtils.addItemToOreDictionary(mCellulosePulp, "pulpCellulose");

        ItemUtils.addItemToOreDictionary(mCatalystCarrier, "catalystEmpty");
        ItemUtils.addItemToOreDictionary(mGreenCatalyst, "catalystAluminiumSilver");
        ItemUtils.addItemToOreDictionary(mAlginicAcid, "dustAlginicAcid");
        ItemUtils.addItemToOreDictionary(mAlumina, "dustAlumina");
        ItemUtils.addItemToOreDictionary(mAluminiumPellet, "pelletAluminium");

        ItemUtils.addItemToOreDictionary(mSodiumAluminate, "dustSodiumAluminate");
        ItemUtils.addItemToOreDictionary(mSodiumHydroxide, "dustSodiumHydroxide");
        ItemUtils.addItemToOreDictionary(mSodiumCarbonate, "dustSodiumCarbonate");
        ItemUtils.addItemToOreDictionary(mLithiumChloride, "dustLithiumChloride");
        ItemUtils.addItemToOreDictionary(mPinecone, "pinecone");
        ItemUtils.addItemToOreDictionary(mCrushedPine, "crushedPineMaterial");

        // Handle GT NaOH dusts
        List<ItemStack> NaOHSmall = OreDictionary.getOres("dustSmallSodiumHydroxide_GT5U", false);
        if (!NaOHSmall.isEmpty()) {
            ItemUtils.addItemToOreDictionary(NaOHSmall.get(0), "dustSmallSodiumHydroxide");
        }
        List<ItemStack> NaOHTiny = OreDictionary.getOres("dustTinySodiumHydroxide_GT5U", false);
        if (!NaOHTiny.isEmpty()) {
            ItemUtils.addItemToOreDictionary(NaOHTiny.get(0), "dustTinySodiumHydroxide");
        }
    }

    @Override
    public void blocks() {
        // None yet
    }

    @Override
    public void fluids() {
        // Sewage

    }

    public AgriculturalChem() {
        super();

    }

    private static final ArrayList<ItemStack> mMeats = new ArrayList<>();
    private static final ArrayList<ItemStack> mFish = new ArrayList<>();
    private static final ArrayList<ItemStack> mFruits = new ArrayList<>();
    private static final ArrayList<ItemStack> mVege = new ArrayList<>();
    private static final ArrayList<ItemStack> mNuts = new ArrayList<>();
    private static final ArrayList<ItemStack> mSeeds = new ArrayList<>();
    private static final ArrayList<ItemStack> mPeat = new ArrayList<>();
    private static final ArrayList<ItemStack> mBones = new ArrayList<>();
    private static final ArrayList<ItemStack> mBoneMeal = new ArrayList<>();

    private static final ArrayList<ItemStack> mList_Master_Meats = new ArrayList<>();
    private static final ArrayList<ItemStack> mList_Master_FruitVege = new ArrayList<>();
    private static final ArrayList<ItemStack> mList_Master_Seeds = new ArrayList<>();
    private static final ArrayList<ItemStack> mList_Master_Bones = new ArrayList<>();

    private static void processAllOreDict() {
        processOreDict("listAllmeatraw", mMeats);
        processOreDict("listAllfishraw", mFish);
        processOreDict("listAllfruit", mFruits);
        processOreDict("listAllVeggie", mVege);
        processOreDict("listAllnut", mNuts);
        processOreDict("listAllSeed", mSeeds);
        processOreDict("brickPeat", mPeat);
        processOreDict("bone", mBones);
        processOreDict("dustBone", mBoneMeal);
        // Just make a mega list, makes life easier.
        if (!mMeats.isEmpty()) {
            mList_Master_Meats.addAll(mMeats);
        }
        if (!mFish.isEmpty()) {
            for (ItemStack g : mFish) {
                boolean foundDupe = false;
                for (ItemStack old : mList_Master_Meats) {
                    if (GTUtility.areStacksEqual(g, old)) {
                        foundDupe = true;
                        break;
                    }
                }
                if (foundDupe) continue;
                mList_Master_Meats.add(g);
            }
        }
        if (!mFruits.isEmpty()) {
            mList_Master_FruitVege.addAll(mFruits);
        }
        if (!mVege.isEmpty()) {
            mList_Master_FruitVege.addAll(mVege);
        }
        if (!mNuts.isEmpty()) {
            mList_Master_FruitVege.addAll(mNuts);
        }
        if (!mSeeds.isEmpty()) {
            mList_Master_Seeds.addAll(mSeeds);
        }
        if (!mBoneMeal.isEmpty()) {
            mList_Master_Bones.addAll(mBoneMeal);
        }
        if (!mBones.isEmpty()) {
            for (ItemStack g : mBones) {
                boolean foundDupe = false;
                for (ItemStack old : mList_Master_Bones) {
                    if (GTUtility.areStacksEqual(g, old)) {
                        foundDupe = true;
                        break;
                    }
                }
                if (foundDupe) continue;
                mList_Master_Bones.add(g);
            }
        }
    }

    private static void processOreDict(String aOreName, ArrayList<ItemStack> aMap) {
        ArrayList<ItemStack> aTemp = OreDictionary.getOres(aOreName);
        if (!aTemp.isEmpty()) {
            aMap.addAll(aTemp);
        }
    }

    private static void addBasicSlurryRecipes() {

        ItemStack aManureByprod1 = ItemUtils.getItemStackOfAmountFromOreDict("dustTinyManureByproducts", 1);
        ItemStack aManureByprod2 = ItemUtils.getItemStackOfAmountFromOreDict("dustSmallManureByproducts", 1);
        ItemStack aDirtDust = ItemUtils.getSimpleStack(dustDirt, 1);

        // Poop Juice to Basic Slurry
        GTValues.RA.stdBuilder()
            .itemOutputs(aDirtDust, aDirtDust, aManureByprod1, aManureByprod1, aManureByprod1, aManureByprod1)
            .outputChances(2000, 2000, 500, 500, 250, 250)
            .fluidInputs(new FluidStack(GTPPFluids.PoopJuice, 1000))
            .fluidOutputs(new FluidStack(GTPPFluids.ManureSlurry, 250))
            .duration(10 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(centrifugeRecipes);

        // More Efficient way to get byproducts, less Slurry
        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(20))
            .itemOutputs(aDirtDust, aDirtDust, aManureByprod1, aManureByprod1, aManureByprod2, aManureByprod2)
            .outputChances(4000, 3000, 1250, 1250, 675, 675)
            .fluidInputs(new FluidStack(GTPPFluids.PoopJuice, 1000))
            .fluidOutputs(new FluidStack(GTPPFluids.ManureSlurry, 50))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_MV / 2)
            .addTo(centrifugeRecipes);
    }

    private static void addAdvancedSlurryRecipes() {

        ItemStack aCircuit = GTUtility.getIntegratedCircuit(10);
        ItemStack aBone;
        ItemStack aMeat;
        ItemStack aEmptyCells = Materials.Empty.getCells(2);
        ItemStack aInputCells = ItemUtils.getItemStackOfAmountFromOreDict("cellRawAnimalWaste", 2);
        FluidStack aOutput = new FluidStack(GTPPFluids.FertileManureSlurry, 1000);

        for (FluidStack aBloodStack : GTPPFluids.getBloodFluids()) {
            for (ItemStack aBoneStack : mList_Master_Bones) {
                aBone = ItemUtils.getSimpleStack(aBoneStack, 2);
                for (ItemStack aMeatStack : mList_Master_Meats) {
                    aMeat = ItemUtils.getSimpleStack(aMeatStack, 5);
                    // Poop Juice to Fertile Slurry
                    GTValues.RA.stdBuilder()
                        .itemInputs(aCircuit, aBone, aMeat, aInputCells)
                        .itemOutputs(aEmptyCells)
                        .fluidInputs(aBloodStack)
                        .fluidOutputs(aOutput)
                        .duration(8 * SECONDS)
                        .eut(TierEU.RECIPE_MV / 2)
                        .addTo(mixerRecipes);
                }
            }
        }
    }

    private static void addBasicOrganiseFertRecipes() {
        FluidStack aInputFluid = new FluidStack(GTPPFluids.ManureSlurry, 1000);
        ItemStack aOutputDust = ItemUtils.getSimpleStack(dustOrganicFertilizer, 3);
        ItemStack aPeat;
        ItemStack aMeat;
        for (ItemStack aPeatStack : mPeat) {
            aPeat = ItemUtils.getSimpleStack(aPeatStack, 3);
            for (ItemStack aMeatStack : mList_Master_Meats) {
                aMeat = ItemUtils.getSimpleStack(aMeatStack, 5);
                GTValues.RA.stdBuilder()
                    .itemInputs(aPeat, aMeat)
                    .itemOutputs(aOutputDust)
                    .fluidInputs(aInputFluid)
                    .duration(20 * SECONDS)
                    .eut(TierEU.RECIPE_MV)
                    .addTo(UniversalChemical);
            }
            aPeat = ItemUtils.getSimpleStack(aPeatStack, 2);
            for (ItemStack aMeatStack : mList_Master_FruitVege) {
                aMeat = ItemUtils.getSimpleStack(aMeatStack, 9);
                GTValues.RA.stdBuilder()
                    .itemInputs(aPeat, aMeat)
                    .itemOutputs(aOutputDust)
                    .fluidInputs(aInputFluid)
                    .duration(10 * SECONDS)
                    .eut(TierEU.RECIPE_MV)
                    .addTo(UniversalChemical);
            }
        }
    }

    private static void addAdvancedOrganiseFertRecipes() {
        FluidStack aInputFluid = new FluidStack(GTPPFluids.FertileManureSlurry, 1000);
        ItemStack aOutputDust = ItemUtils.getSimpleStack(dustOrganicFertilizer, 7);
        ItemStack aPeat;
        ItemStack aMeat;
        for (ItemStack aPeatStack : mPeat) {
            aPeat = ItemUtils.getSimpleStack(aPeatStack, 5);
            for (ItemStack aMeatStack : mList_Master_Meats) {
                aMeat = ItemUtils.getSimpleStack(aMeatStack, 7);
                GTValues.RA.stdBuilder()
                    .itemInputs(aPeat, aMeat)
                    .itemOutputs(aOutputDust)
                    .fluidInputs(aInputFluid)
                    .duration(10 * SECONDS)
                    .eut(140)
                    .addTo(UniversalChemical);
            }
            aPeat = ItemUtils.getSimpleStack(aPeatStack, 3);
            for (ItemStack aMeatStack : mList_Master_FruitVege) {
                aMeat = ItemUtils.getSimpleStack(aMeatStack, 12);
                GTValues.RA.stdBuilder()
                    .itemInputs(aPeat, aMeat)
                    .itemOutputs(aOutputDust)
                    .fluidInputs(aInputFluid)
                    .duration(5 * SECONDS)
                    .eut(140)
                    .addTo(UniversalChemical);
            }
        }
    }

    private static void addMiscRecipes() {

        ItemStack aDustOrganicFert = ItemUtils.getSimpleStack(dustOrganicFertilizer, 1);
        ItemStack aManureByprod = ItemUtils.getSimpleStack(dustManureByproducts, 1);

        // Dehydrate Organise Fert to Normal Fert.
        if (Mods.Forestry.isModLoaded()) {
            addMiscForestryRecipes();
        }

        /*
         * IC2 Support
         */
        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(12), ItemUtils.getSimpleStack(aDustOrganicFert, 4))
            .itemOutputs(ItemList.IC2_Fertilizer.get(3), aManureByprod, aManureByprod)
            .outputChances(100_00, 20_00, 20_00)
            .eut(240)
            .duration(20 * SECONDS)
            .addTo(chemicalDehydratorRecipes);

        // Dirt Production
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(dustDirt, 9))
            .itemOutputs(ItemUtils.getSimpleStack(Blocks.dirt))
            .duration(2 * SECONDS)
            .eut(8)
            .addTo(compressorRecipes);

        // Centrifuge Byproducts

        // Ammonium Nitrate, Phosphates, Calcium, Copper, Carbon
        GTValues.RA.stdBuilder()
            .itemInputs(ItemUtils.getSimpleStack(aManureByprod, 4), GTUtility.getIntegratedCircuit(20))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Phosphorus, 2L),
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Calcium, 2L),
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Copper, 1L),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Carbon, 1L),
                ItemUtils.getSimpleStack(dustDirt, 1),
                ItemUtils.getItemStackOfAmountFromOreDict("dustTinyAmmoniumNitrate", 1))
            .outputChances(2500, 2500, 750, 1000, 5000, 250)
            .fluidInputs(Materials.SulfuricAcid.getFluid(250))
            .fluidOutputs(FluidUtils.getFluidStack("sulfuricapatite", 50))
            .duration(20 * SECONDS)
            .eut(TierEU.RECIPE_MV / 2)
            .addTo(centrifugeRecipes);

        // Add Fuel Usages
        GTValues.RA.stdBuilder()
            .fluidInputs(new FluidStack(GTPPFluids.PoopJuice, 1000))
            .duration(0)
            .eut(0)
            .metadata(FUEL_VALUE, 12)
            .addTo(semiFluidFuels);

        GTValues.RA.stdBuilder()
            .fluidInputs(new FluidStack(GTPPFluids.ManureSlurry, 1000))
            .duration(0)
            .eut(0)
            .metadata(FUEL_VALUE, 24)
            .addTo(semiFluidFuels);

        GTValues.RA.stdBuilder()
            .fluidInputs(new FluidStack(GTPPFluids.FertileManureSlurry, 1000))
            .duration(0)
            .eut(0)
            .metadata(FUEL_VALUE, 32)
            .addTo(semiFluidFuels);

        // Red Slurry / Tailings Processing
        GTValues.RA.stdBuilder()
            .itemInputs(GTUtility.getIntegratedCircuit(10))
            .itemOutputs(
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Iron, 1L),
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Copper, 1L),
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Tin, 1L),
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Sulfur, 1L),
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Nickel, 1L),
                GTOreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lead, 1L))
            .outputChances(3000, 3000, 2000, 2000, 1000, 1000)
            .fluidInputs(new FluidStack(GTPPFluids.RedMud, 1000))
            .fluidOutputs(Materials.Water.getFluid(500))
            .duration(30 * SECONDS)
            .eut(TierEU.RECIPE_LV)
            .addTo(centrifugeRecipes);
    }

    @Optional.Method(modid = Mods.Names.FORESTRY)
    private static void addMiscForestryRecipes() {
        ItemStack aDustOrganicFert = ItemUtils.getSimpleStack(dustOrganicFertilizer, 1);
        ItemStack aManureByprod = ItemUtils.getSimpleStack(dustManureByproducts, 1);

        if (ItemList.FR_Fertilizer.hasBeenSet()) {
            GTValues.RA.stdBuilder()
                .itemInputs(GTUtility.getIntegratedCircuit(11), ItemUtils.getSimpleStack(aDustOrganicFert, 4))
                .itemOutputs(ItemList.FR_Fertilizer.get(3), aManureByprod, aManureByprod)
                .outputChances(100_00, 20_00, 20_00)
                .eut(240)
                .duration(20 * SECONDS)
                .addTo(chemicalDehydratorRecipes);
        }
    }

    @Override
    public String errorMessage() {
        return "Failed to generate recipes for AgroChem.";
    }

    @Override
    public boolean generateRecipes() {

        // Organise OreDict
        processAllOreDict();

        // Slurry Production
        addBasicSlurryRecipes();
        addAdvancedSlurryRecipes();

        // Organic Fert. Production
        addBasicOrganiseFertRecipes();
        addAdvancedOrganiseFertRecipes();

        addMiscRecipes();

        BioRecipes.init();

        return true;
    }
}
