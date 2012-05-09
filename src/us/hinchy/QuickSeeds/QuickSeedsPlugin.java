package us.hinchy.QuickSeeds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class QuickSeedsPlugin extends JavaPlugin {

    public static QuickSeedsLogger log;

    public void onDisable() {
        // Output to console that plugin is disabled
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
    }

    public void onEnable() {
    	// Enable log
    	log = new QuickSeedsLogger(this);
    	
        // Load config
		this.getConfig();
		if (this.getConfig().isSet("debug") == false) {
			this.saveDefaultConfig();
			log.info("[QuickSeeds] Config did not exist or was invalid, default config saved.");
		}
        
        // Get plugin info from plugin.yml
        PluginDescriptionFile pdfFile = this.getDescription();

        // Register events
        getServer().getPluginManager().registerEvents(new QuickSeedsBlockListener(), this);

        // Output to console that plugin is enabled
        log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled!");
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
