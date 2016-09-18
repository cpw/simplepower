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

import cpw.mods.simplepower.blocks.Transmitter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Transmitter
 */
public class TransmitterTE extends PowerTarget implements IEnergyStorage
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
        if (associates.isEmpty()) return 0;
        int amtperrelay = Math.min(1000, maxReceive) / associates.size();
        return associates.stream().collect(Collectors.summingInt(t -> ((RelayTE)t).receiveEnergy(amtperrelay, simulate)));
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
        return true;
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
