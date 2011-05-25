package org.cmine.main.plugins;

import java.io.File;
import java.util.logging.Logger;

public class Plugins {
	public File pluginsFolder = new File("plugins");
	
	public void createPluginFolder() {
		try {
			if(!this.pluginsFolder.exists()) {
				logger.warning("Plugins Folder not found. Creating Folder");
				pluginsFolder.mkdir();
			}
		} catch(Exception e) {
			logger.severe("Failed creating Plugins Folder.");
			e.printStackTrace();
		}
	}
	
	public void loadPlugins() {
		
		
	}
	public static Logger logger = Logger.getLogger("Minecraft");

}
