package us.hinchy.QuickSeeds;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuickSeedsBlockListener implements Listener {
	
	public QuickSeedsPlugin plugin;
	 
	public QuickSeedsBlockListener(QuickSeedsPlugin instance) {
	    plugin = instance;
	}

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!(player.hasPermission("quickseeds.plant"))) return;

        Block block = event.getBlock();
        
        // is it a crop? what kind of crop is it?
        Material cropType = block.getType();
        Material seedType;
        if (cropType == Material.CROPS) seedType = Material.SEEDS;
        else if (cropType == Material.CARROT) seedType = Material.CARROT_ITEM;
        else if (cropType == Material.POTATO) seedType = Material.POTATO_ITEM;
        else return;
        
        // is the player allowed to use quickseeds with this crop?
        if ((plugin.getConfig().getBoolean("crops.wheat") == false) && (cropType == Material.CROPS)) {
        	if (plugin.getConfig().getBoolean("debug")) QuickSeedsPlugin.log.info("Can't QS " + cropType.toString() + "!");
        	return;
        }
        if ((plugin.getConfig().getBoolean("crops.carrot") == false) && (cropType == Material.CARROT)) {
        	if (plugin.getConfig().getBoolean("debug")) QuickSeedsPlugin.log.info("Can't QS " + cropType.toString() + "!");
        	return;
        }
        if ((plugin.getConfig().getBoolean("crops.potato") == false) && (cropType == Material.POTATO)) {
        	if (plugin.getConfig().getBoolean("debug")) QuickSeedsPlugin.log.info("Can't QS " + cropType.toString() + "!");
        	return;
        }
        
        // If player planted an applicable crop and has more to plant        
        if (player.getInventory().contains(seedType,8) || (player.getGameMode() == GameMode.CREATIVE)) {
            // stores how many seeds are used and thus need to be removed from the inventory
            int seeds = 1;
            // Loop around the 8 bordering blocks
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    if (x != 0 || z != 0) {
                        Block neighbour = block.getRelative(x, 0, z);
                        Block neighbourBelow = neighbour.getRelative(BlockFace.DOWN);
                        if (neighbourBelow.getType() == Material.SOIL && neighbour.getType() == Material.AIR) {
                            if (player.getInventory().contains(seedType)) {
                                neighbour.setType(cropType);
                                seeds++;
                            }
                        }
                    }
                }
            }
            if (player.getGameMode() != GameMode.CREATIVE) removeItems(player, seedType, seeds);
        }
    }

    @SuppressWarnings("deprecation")
	public void removeItems(Player player, Material material, int amount) {
    	QuickSeedsPlugin.log.debug("Amount to remove: " + Integer.toString(amount));
        int remaining = amount;
        int stackSize;
        int slot;
        while (remaining > 0) {
            slot = player.getInventory().first(material);
            stackSize = player.getInventory().getItem(slot).getAmount();
            QuickSeedsPlugin.log.debug("Stack size: " + Integer.toString(stackSize));
            if (stackSize <= remaining) {
                player.getInventory().clear(slot);
                remaining-=stackSize;
            } else {
                ItemStack stack = player.getInventory().getItem(slot);
                int newStackSize = stackSize-remaining;
                QuickSeedsPlugin.log.debug("New stack size: " + Integer.toString(newStackSize));
                stack.setAmount(newStackSize);
                player.getInventory().setItem(slot, stack);
                remaining = 0;
            }
        }
        player.updateInventory();
    }
}
