package net.simplyrin.chatrecorder.system;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.simplyrin.chatrecorder.ChatRecorder;

public class Config {

	public static File file = new File(ChatRecorder.getPlugin().getDataFolder(), "config.yml");
	public static Configuration config = null;

	public static File getFile() {
		return file;
	}

	public static Configuration getConfig() {
		return config;
	}

	public static String getTarget(String target) {

		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ChatColor.translateAlternateColorCodes('&', config.getString(target));
	}

}
