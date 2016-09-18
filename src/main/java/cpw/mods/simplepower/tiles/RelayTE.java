package cpw.mods.simplepower.tiles;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import cpw.mods.simplepower.Objects;
import cpw.mods.simplepower.SimplePower;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Relay
 */
public class RelayTE extends PowerTarget implements IEnergyStorage, ITickable
{
    private int storedEnergy;

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int toStore = Math.min(1000, maxReceive);
        toStore = Math.min(toStore, getMaxEnergyStored() - getEnergyStored());
        if (!simulate) storedEnergy+=Math.min(toStore, getMaxEnergyStored() - toStore);
        return toStore;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        return Math.min(1000, maxExtract);
    }

    @Override
    public int getEnergyStored()
    {
        return storedEnergy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return 10000;
    }

    @Override
    public boolean canExtract()
    {
        return true;
    }

    @Override
    public boolean canReceive()
    {
        return true;
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);
        compound.setInteger("storedEnergy", storedEnergy);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("storedEnergy")) {
            storedEnergy = compound.getInteger("storedEnergy");
        }
    }

    @Override
    protected void onRemoval(PowerTarget t)
    {

    }

    @Override
    protected boolean acceptsType(PowerTarget t)
    {
        return (t instanceof ReceiverTE);
    }

    @Override
    protected void onAddition(PowerTarget t)
    {

    }

    @Override
    public void update()
    {
        if (getWorld().isRemote) return;
        List<IEnergyStorage> receivers = new ArrayList<>(associates.stream().map((pt) -> (ReceiverTE)pt)
                .filter(ReceiverTE::canReceive).collect(Collectors.toList()));

        for (EnumFacing facing: EnumFacing.VALUES) {
            TileEntity receiver = getWorld().getTileEntity(getPos().offset(facing));
            if (receiver!=null && receiver.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                final IEnergyStorage receiverCapability = receiver.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                if (receiverCapability.canReceive())
                    receivers.add(receiverCapability);
            }
        }

        if (receivers.isEmpty()) return;
        if (storedEnergy < receivers.size()) return;
        int amttosend = Math.min(100, storedEnergy/receivers.size());
        int sum = 0;
        for (IEnergyStorage e: receivers) {
            int amt = e.receiveEnergy(amttosend, true);
            amt = e.receiveEnergy(amt, false);
            sum+=amt;
        }
        storedEnergy -= sum;
    }
}
