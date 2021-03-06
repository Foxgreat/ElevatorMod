package xyz.vsngamer.elevator;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.vsngamer.elevator.init.ModConfig;
import xyz.vsngamer.elevator.init.Registry;
import xyz.vsngamer.elevator.network.NetworkHandler;
import xyz.vsngamer.elevator.proxy.CommonProxy;

@Mod(modid = Ref.MOD_ID, name = Ref.NAME, version = Ref.VERSION, acceptedMinecraftVersions = Ref.ACCPEPTED_VERSIONS, guiFactory = Ref.GUI_FACTORY)
public class ElevatorMod {

	@Instance
	public static ElevatorMod instance;

	@SidedProxy(clientSide = Ref.CLIENT_PROXY_CLASS, serverSide = Ref.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs CREATIVE_TAB = new ElevatorModTab();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();

		NetworkHandler.init();

		File configDir = new File(event.getModConfigurationDirectory(), "ElevatorMod");
		ModConfig.init(new File(configDir, "ElevatorMod.cfg"));

		MinecraftForge.EVENT_BUS.register(new ModConfig());

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}
	
	
	
	
	@EventHandler
	public void onMissingMappingsI(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> missing : event.getMappings()) {
			EnumDyeColor color = null;
			if (missing.key.getResourceDomain().equals(Ref.MOD_ID + ":BlockElevator")) {
				color = EnumDyeColor.WHITE;
			} else if (missing.key.getResourceDomain().startsWith(Ref.MOD_ID + ":BlockElevator")) {
				String name = missing.key.getResourceDomain().substring((Ref.MOD_ID + ":BlockElevator").length());
				for (EnumDyeColor candidate : EnumDyeColor.values()) {
					if (candidate.getUnlocalizedName().equalsIgnoreCase(name)) {
						color = candidate;
						break;
					}
				}
			}
			if (color != null) {
				missing.remap(Registry.elevatorsItems.get(color));
				break;
			}
		}
	}

	@EventHandler
	public void onMissingMappingsB(RegistryEvent.MissingMappings<Block> event) {
		for (RegistryEvent.MissingMappings.Mapping<Block> missing : event.getMappings()) {
			EnumDyeColor color = null;
			if (missing.key.getResourceDomain().equals(Ref.MOD_ID + ":BlockElevator")) {
				color = EnumDyeColor.WHITE;
			} else if (missing.key.getResourceDomain().startsWith(Ref.MOD_ID + ":BlockElevator")) {
				String name = missing.key.getResourceDomain().substring((Ref.MOD_ID + ":BlockElevator").length());
				for (EnumDyeColor candidate : EnumDyeColor.values()) {
					if (candidate.getUnlocalizedName().equalsIgnoreCase(name)) {
						color = candidate;
						break;
					}
				}
			}
			if (color != null) {
				missing.remap(Registry.elevatorsBlocks.get(color));
				break;
			}
		}
	}
}
