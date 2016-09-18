package cpw.mods.simplepower;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import cpw.mods.simplepower.tiles.PowerTarget;
import cpw.mods.simplepower.tiles.ReceiverTE;
import cpw.mods.simplepower.tiles.RelayTE;
import cpw.mods.simplepower.tiles.TransmitterTE;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POWAH!
 */
@Mod(modid= "simplepower", name="Simple Power", dependencies="required-after:Forge@[12.18.1.2092,)")
public class SimplePower
{
    @Mod.Instance("simplepower")
    public static SimplePower instance;

    @Mod.EventBusSubscriber
    public static class WorldLoadHandler {
        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event) {
            instance.powerTargets.put(event.getWorld(), new ArrayList<>());
        }
        @SubscribeEvent
        public static void onWorldUnload(WorldEvent.Unload event) {
            instance.powerTargets.invalidate(event.getWorld());
        }
    }

    public Cache<World, List<PowerTarget>> powerTargets = CacheBuilder.newBuilder().weakKeys().softValues().build();

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent preinit) {
        GameRegistry.registerTileEntity(TransmitterTE.class, "simplepower.transmitter");
        GameRegistry.registerTileEntity(RelayTE.class, "simplepower.relay");
        GameRegistry.registerTileEntity(ReceiverTE.class, "simplepower.receiver");
    }

    @SuppressWarnings("ConstantConditions")
    @Mod.EventHandler
    public void init(FMLInitializationEvent init) {
        GameRegistry.addShapedRecipe(new ItemStack(Objects.transmitter_item, 1), new String[] { "XXX", "XXX", "XXX" }, 'X', Blocks.STONE);
    }

    @SuppressWarnings("ConstantConditions")
    @Mod.EventBusSubscriber(Side.CLIENT)
    public static class ClientRegistry {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent evt) {
            ModelLoader.setCustomModelResourceLocation(Objects.transmitter_item, 0, new ModelResourceLocation("simplepower:transmitter", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Objects.relay_item, 0, new ModelResourceLocation("simplepower:relay", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Objects.receiver_item, 0, new ModelResourceLocation("simplepower:receiver", "inventory"));
        }
    }
}
