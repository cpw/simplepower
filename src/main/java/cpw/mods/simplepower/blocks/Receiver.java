package cpw.mods.simplepower.blocks;

import cpw.mods.simplepower.Objects;
import cpw.mods.simplepower.tiles.ReceiverTE;
import cpw.mods.simplepower.tiles.TransmitterTE;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * A transmitter. Modelled from the endrod.
 */
public class Receiver extends BlockDirectional implements ITileEntityProvider
{
    private static final AxisAlignedBB UPRIGHT = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    private static final AxisAlignedBB NS_AXIS = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
    private static final AxisAlignedBB EW_AXIS = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

    public Receiver()
    {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
        this.setUnlocalizedName("simplepower:block.receiver");
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Nonnull
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = worldIn.getBlockState(pos.offset(facing.getOpposite()));

        //noinspection ConstantConditions
        if (state.getBlock() == Objects.receiver)
        {
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == facing)
            {
                return this.getDefaultState().withProperty(FACING, facing.getOpposite());
            }
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch (state.getValue(FACING).getAxis())
        {
            case X:
            default:
                return EW_AXIS;
            case Z:
                return NS_AXIS;
            case Y:
                return UPRIGHT;
        }
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World world, int meta)
    {
        return new ReceiverTE();
    }
}
