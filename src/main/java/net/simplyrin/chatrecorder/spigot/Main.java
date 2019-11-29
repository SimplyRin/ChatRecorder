package net.simplyrin.chatrecorder.spigot;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.simplyrin.chatrecorder.spigot.listener.EventListener;
import net.simplyrin.config.Config;

/**
 * Created by SimplyRin on 2019/11/29.
 *
 * Copyright (c) 2019 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Main extends JavaPlugin implements Listener {

	@Getter
	private Configuration pluginConfig;

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

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
			config.set("Format", "%Time: %Player: %Message");

			Config.saveConfig(config, file);
		}

		this.pluginConfig = Config.getConfig(file);

		File player = new File(this.getDataFolder(), "Player");
		player.mkdir();
	}

}
