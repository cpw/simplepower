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

package cpw.mods.simplepower;

import cpw.mods.simplepower.blocks.Receiver;
import cpw.mods.simplepower.blocks.Relay;
import cpw.mods.simplepower.blocks.Transmitter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.event.RegistryEvent;

/**
 * Object store
 */
@GameRegistry.ObjectHolder(value = "simplepower")
public class Objects
{
    public static final Transmitter transmitter = null;
    @GameRegistry.ObjectHolder("simplepower:transmitter")
    public static final ItemBlock transmitter_item = null;

    public static final Relay relay = null;
    @GameRegistry.ObjectHolder("simplepower:relay")
    public static final ItemBlock relay_item = null;

    public static final Receiver receiver = null;
    @GameRegistry.ObjectHolder("simplepower:receiver")
    public static final ItemBlock receiver_item = null;

    @SuppressWarnings("ConstantConditions")
    @Mod.EventBusSubscriber
    public static class RegistrationHandler
    {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> evt)
        {
            evt.getRegistry().registerAll(
                    new ItemBlock(Objects.transmitter).setRegistryName("simplepower","transmitter"),
                    new ItemBlock(Objects.relay).setRegistryName("simplepower", "relay"),
                    new ItemBlock(Objects.receiver).setRegistryName("simplepower","receiver")
            );
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> evt)
        {
            evt.getRegistry().registerAll(
                    new Transmitter().setRegistryName("simplepower","transmitter"),
                    new Relay().setRegistryName("simplepower","relay"),
                    new Receiver().setRegistryName("simplepower", "receiver")
            );
        }
    }
}
