package us.hinchy.QuickSeeds;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickSeedsPlugin extends JavaPlugin {

    public static QuickSeedsLogger log;

    public void onDisable() {
        // Output to console that plugin is disabled
        System.out.println(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " disabled!");
    }

    public void onEnable() {
    	// Enable log
    	log = new QuickSeedsLogger(this);
    	
        // Add default values to config
		this.saveDefaultConfig();
		processDefaultConfig();
		
        // Register events
        getServer().getPluginManager().registerEvents(new QuickSeedsBlockListener(this), this);

        // Output to console that plugin is enabled
        log.info(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " enabled!");
    }

	private void processDefaultConfig() {
		final Map<String, Object> defParams = new HashMap<String, Object>();
		this.getConfig().options().copyDefaults(true);

		defParams.put("crops.wheat", true);
		defParams.put("crops.carrot", true);
		defParams.put("crops.potato", true);

		for (final Entry<String, Object> e : defParams.entrySet())
			if (!this.getConfig().contains(e.getKey()))
				this.getConfig().set(e.getKey(), e.getValue());

		this.saveConfig();
		this.reloadConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (cmd.getName().equalsIgnoreCase("qsreload")) {
			this.reloadConfig();
			sender.sendMessage("QuickSeeds configuration reloaded.");
			return true;
		}
		return false; 
	}
}
