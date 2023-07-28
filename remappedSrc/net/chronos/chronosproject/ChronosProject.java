package net.cpd.noparticles;

import net.cpd.noparticles.block.ModBlocks;
import net.cpd.noparticles.item.ModItemGroup;
import net.cpd.noparticles.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChronosProject implements ModInitializer {
	public static final String MOD_ID = "chronosproject";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}
