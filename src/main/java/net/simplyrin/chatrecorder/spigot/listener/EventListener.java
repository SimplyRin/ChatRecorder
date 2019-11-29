package net.simplyrin.chatrecorder.spigot.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import lombok.AllArgsConstructor;
import net.simplyrin.chatrecorder.spigot.Main;

@AllArgsConstructor
public class EventListener implements Listener {

	private Main instance;

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		File playerFile = new File(this.instance.getDataFolder(), "Player/" + player.getUniqueId().toString());
		if (!playerFile.exists()) {
			playerFile.mkdir();
		}

		SimpleDateFormat file = new SimpleDateFormat(this.instance.getPluginConfig().getString("File"));
		SimpleDateFormat time = new SimpleDateFormat(this.instance.getPluginConfig().getString("Time"));

		Date date = new Date();

		File logFile = new File(playerFile, file.format(date) + ".log");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String format = this.instance.getPluginConfig().getString("Format");

		format = format.replace("%Time", time.format(date).toString());
		format = format.replace("%Player", player.getName());
		format = format.replace("%Message", event.getMessage());

		pw.println(format);
		pw.close();
	}

}