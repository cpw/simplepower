/*
 * Simple Power
 *   A minecraft mod for simple wireless transportation of Forge Energy
 * Copyright © 2016 cpw
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cpw.mods.simplepower.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

/**
 * Transmitter
 */
public class ReceiverTE extends PowerTarget implements IEnergyStorage
{
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing)
    {
        return capability == CapabilityEnergy.ENERGY && facing.getOpposite() == EnumFacing.getFront(getBlockMetadata());
    }

    @Override
    @Nonnull
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing)
    {
        return CapabilityEnergy.ENERGY.cast(this);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        final EnumFacing front = EnumFacing.getFront(getBlockMetadata());
        TileEntity te = getWorld().getTileEntity(getPos().offset(front.getOpposite()));
        if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, front))
        {
            return te.getCapability(CapabilityEnergy.ENERGY, front).receiveEnergy(maxReceive, simulate);
        }
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return 0;
    }

    @Override
    public int getEnergyStored()
    {
        return 0;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return 0;
    }

    @Override
    public boolean canExtract()
    {
        return false;
    }

    @Override
    public boolean canReceive()
    {
        final EnumFacing front = EnumFacing.getFront(getBlockMetadata());
        TileEntity te = getWorld().getTileEntity(getPos().offset(front.getOpposite()));
        return te != null && !(te instanceof ReceiverTE) && te.hasCapability(CapabilityEnergy.ENERGY, front) && te.getCapability(CapabilityEnergy.ENERGY, front).canReceive();
    }

    @Override
    protected void onRemoval(PowerTarget t)
    {

    }

    @Override
    protected boolean acceptsType(PowerTarget t)
    {
        return t instanceof RelayTE;
    }

    @Override
    protected void onAddition(PowerTarget t)
    {

    }
}
