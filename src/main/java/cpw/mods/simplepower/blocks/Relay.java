package cpw.mods.simplepower.blocks;

import cpw.mods.simplepower.tiles.RelayTE;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by cpw on 17/09/16.
 */
public class Relay extends Block implements ITileEntityProvider
{
    public Relay() {
        super(Material.IRON);
        setUnlocalizedName("simplepower:block.relay");
        setDefaultState(this.blockState.getBaseState());
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new RelayTE();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            playerIn.addChatMessage(new TextComponentTranslation("Power level %s", ((RelayTE)worldIn.getTileEntity(pos)).getEnergyStored()));
            return true;
        }
        return false;
    }
}
