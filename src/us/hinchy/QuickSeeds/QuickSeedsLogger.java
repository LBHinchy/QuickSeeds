package us.hinchy.QuickSeeds;

import java.util.logging.Level;
import java.util.logging.Logger;

public class QuickSeedsLogger {

    public static final Logger logger = Logger.getLogger("Minecraft");

    private final QuickSeedsPlugin plugin;

    public QuickSeedsLogger(QuickSeedsPlugin plugin) {
        this.plugin = plugin;
    }


    public void debug( String s ) {
	    if (plugin.getConfig().getBoolean("debug")) {
	    	logger.log(Level.INFO, "[QuickSeeds DEBUG] " + s);
		}
    }

    public void info( String s ) {
    	logger.log(Level.INFO, "[QuickSeeds] " + s);
    }

    public void severe( String s ) {
    	logger.log(Level.SEVERE, "[QuickSeeds] " + s);
    }

    public void warning( String s ) {
    	logger.log(Level.WARNING, "[QuickSeeds] " + s);
    }

}