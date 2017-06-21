package net.simplyrin.chatrecorder;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.simplyrin.chatrecorder.system.ListenerSystem;

public class ChatRecorder extends Plugin implements Listener {

	private static ChatRecorder plugin;

	public void onEnable() {
		plugin = this;

		plugin.getProxy().getPluginManager().registerListener(this, this);
		plugin.getProxy().getPluginManager().registerListener(this, new ListenerSystem());

		ChatRecorder.Configuration();

		File f = new File(plugin.getDataFolder(), "Player");

		if(!f.exists()) {
			f.mkdir();
		}
	}

	private static void Configuration() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		File file = new File(plugin.getDataFolder(), "config.yml");

		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Configuration config = null;
			try {
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			config.set("TimeZone", "Asia/Tokyo");
			config.set("File", "yyyy-MM-dd");
			config.set("Time", "HH:mm:ss");
			config.set("Format", "%Time: %Player@%Server: %Message");

			try {
				ConfigurationProvider.getProvider( YamlConfiguration.class).save(config, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ChatRecorder getPlugin() {
		return plugin;
	}

}
