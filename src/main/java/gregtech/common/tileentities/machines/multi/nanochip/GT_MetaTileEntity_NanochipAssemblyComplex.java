package gregtech.common.tileentities.machines.multi.nanochip;

import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PROCESSING_ARRAY;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PROCESSING_ARRAY_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PROCESSING_ARRAY_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_PROCESSING_ARRAY_GLOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.ISurvivalBuildEnvironment;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_ExtendedPowerMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_HatchElementBuilder;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.api.util.IGT_HatchAdder;

public class GT_MetaTileEntity_NanochipAssemblyComplex
    extends GT_MetaTileEntity_ExtendedPowerMultiBlockBase<GT_MetaTileEntity_NanochipAssemblyComplex>
    implements ISurvivalConstructable {

    public static final String STRUCTURE_PIECE_MAIN = "main";
    public static final int STRUCTURE_OFFSET_X = 6;
    public static final int STRUCTURE_OFFSET_Y = 1;
    public static final int STRUCTURE_OFFSET_Z = 5;

    public static final int CASING_INDEX_BASE = GregTech_API.getCasingTextureIndex(GregTech_API.sBlockCasings4, 0);

    public static final String[][] structure = new String[][] {
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" },
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" },
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" },
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" },
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" },
        { "     AAA     ", "     A~A     ", "     AAA     ", "BBBBBBBBBBBBB" },
        { "     AAA     ", "     A A     ", "     AAA     ", "BBBBBBBBBBBBB" },
        { "     AAA     ", "     AAA     ", "     AAA     ", "BBBBBBBBBBBBB" },
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" },
        { "             ", "             ", " AAA     AAA ", "BBBBBBBBBBBBB" },
        { "             ", "             ", " AAA     AAA ", "BBBBBBBBBBBBB" },
        { "             ", "             ", " AAA     AAA ", "BBBBBBBBBBBBB" },
        { "             ", "             ", "             ", "BBBBBBBBBBBBB" } };

    public static final IStructureDefinition<GT_MetaTileEntity_NanochipAssemblyComplex> STRUCTURE_DEFINITION = StructureDefinition
        .<GT_MetaTileEntity_NanochipAssemblyComplex>builder()
        .addShape(STRUCTURE_PIECE_MAIN, structure)
        .addElement('A', ofBlock(GregTech_API.sBlockCasings4, 0))
        .addElement('B', ofBlock(GregTech_API.sBlockCasings8, 10))
        .addElement(
            'I',
            GT_HatchElementBuilder.<GT_MetaTileEntity_NanochipAssemblyComplex>builder()
                .atLeast(AssemblyModuleElement.AssemblyModule)
                .casingIndex(CASING_INDEX_BASE)
                .dot(2)
                // Base casing or assembly module
                .buildAndChain(GregTech_API.sBlockCasings4, 0))
        .build();

    private final ArrayList<GT_MetaTileEntity_NanochipAssemblyModuleBase<?>> modules = new ArrayList<>();

    public GT_MetaTileEntity_NanochipAssemblyComplex(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    protected GT_MetaTileEntity_NanochipAssemblyComplex(String aName) {
        super(aName);
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece(
            STRUCTURE_PIECE_MAIN,
            trigger,
            hintsOnly,
            STRUCTURE_OFFSET_X,
            STRUCTURE_OFFSET_Y,
            STRUCTURE_OFFSET_Z);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int elementBudget, ISurvivalBuildEnvironment env) {
        return survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            trigger,
            STRUCTURE_OFFSET_X,
            STRUCTURE_OFFSET_Y,
            STRUCTURE_OFFSET_Z,
            elementBudget,
            env,
            false,
            true);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        return checkPiece(STRUCTURE_PIECE_MAIN, STRUCTURE_OFFSET_X, STRUCTURE_OFFSET_Y, STRUCTURE_OFFSET_Z);
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_NanochipAssemblyComplex> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        return new GT_Multiblock_Tooltip_Builder().toolTipFinisher("GregTech");
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_NanochipAssemblyComplex(this.mName);
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.casingTexturePages[0][48], TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_PROCESSING_ARRAY_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PROCESSING_ARRAY_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.casingTexturePages[0][48], TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_PROCESSING_ARRAY)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_PROCESSING_ARRAY_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.casingTexturePages[0][48] };
    }

    /**
     * Add an assembly module to the module list
     *
     * @param aTileEntity      Project module
     * @param aBaseCasingIndex Index of the casing texture it should take
     * @return True if input entity is a valid module and could be added, else false
     */
    public boolean addModuleToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null) {
            return false;
        }
        if (aMetaTileEntity instanceof GT_MetaTileEntity_NanochipAssemblyModuleBase<?>) {
            return modules.add((GT_MetaTileEntity_NanochipAssemblyModuleBase<?>) aMetaTileEntity);
        }
        return false;
    }

    // Hatch adder for modules
    public enum AssemblyModuleElement implements IHatchElement<GT_MetaTileEntity_NanochipAssemblyComplex> {

        AssemblyModule(GT_MetaTileEntity_NanochipAssemblyComplex::addModuleToMachineList,
            GT_MetaTileEntity_NanochipAssemblyComplex.class) {

            @Override
            public long count(GT_MetaTileEntity_NanochipAssemblyComplex tileEntity) {
                return tileEntity.modules.size();
            }
        };

        private final List<Class<? extends IMetaTileEntity>> mteClasses;
        private final IGT_HatchAdder<GT_MetaTileEntity_NanochipAssemblyComplex> adder;

        @SafeVarargs
        AssemblyModuleElement(IGT_HatchAdder<GT_MetaTileEntity_NanochipAssemblyComplex> adder,
            Class<? extends IMetaTileEntity>... mteClasses) {
            this.mteClasses = Collections.unmodifiableList(Arrays.asList(mteClasses));
            this.adder = adder;
        }

        @Override
        public List<? extends Class<? extends IMetaTileEntity>> mteClasses() {
            return mteClasses;
        }

        public IGT_HatchAdder<? super GT_MetaTileEntity_NanochipAssemblyComplex> adder() {
            return adder;
        }
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
}