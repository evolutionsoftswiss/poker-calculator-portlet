package ch.evolutionsoft.poker.calculator.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.tomcat.jni.Library;

public class PokersourceLibraryLoader {

	static boolean initialized = false;

	public synchronized static void init() {

		if (!initialized) {

			Properties liferayPluginPackageProperties = new Properties();
			try {
				liferayPluginPackageProperties.load(PokersourceLibraryLoader.class.getResourceAsStream("/library.properties"));
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			String libraryBasePath = liferayPluginPackageProperties.getProperty("libraryPath");

			Library.load(libraryBasePath + "libpoker-eval.so");
			Library.load(libraryBasePath + "libpokerjni.so");

			initialized = true;
		}
	}
}
