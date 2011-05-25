package org.cmine.main;

import java.io.IOException;
import java.util.logging.Logger;

import org.cmine.main.plugins.Plugins;

public class CMine implements Runnable {

	public CMine() {
		this.run();

	}

	/**
	 * Start Loading up Methods for CMine.
	 */
	public void run() {
		logger.info("Implemented CMine Build #600");
	}

	public Plugins plugins;
	public static Logger logger = Logger.getLogger("Minecraft");
}
