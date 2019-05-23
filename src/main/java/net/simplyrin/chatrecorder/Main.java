package net.simplyrin.chatrecorder;

import java.io.File;
import java.io.IOException;

import org.bstats.bungeecord.Metrics;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.simplyrin.chatrecorder.listener.EventListener;
import net.simplyrin.config.Config;

public class Main extends Plugin implements Listener {

	@Getter
	private Configuration config;

	@Override
	public void onEnable() {
		this.getProxy().getPluginManager().registerListener(this, new EventListener(this));

		File folder = this.getDataFolder();
		folder.mkdir();

		File file = new File(folder, "config.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Configuration config = Config.getConfig(file);

			config.set("TimeZone", "Asia/Tokyo");
			config.set("File", "yyyy-MM-dd");
			config.set("Time", "HH:mm:ss");
			config.set("Format", "%Time: %Player@%Server: %Message");

			Config.saveConfig(config, file);
		}

		this.config = Config.getConfig(file);

		File player = new File(this.getDataFolder(), "Player");
		player.mkdir();

		new Metrics(this);
	}

}
