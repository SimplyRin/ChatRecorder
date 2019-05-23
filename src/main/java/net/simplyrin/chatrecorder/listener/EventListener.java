package net.simplyrin.chatrecorder.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.simplyrin.chatrecorder.Main;

@AllArgsConstructor
public class EventListener implements Listener {

	private Main instance;

	@EventHandler
	public void onChat(ChatEvent event) {
		ProxiedPlayer player = (ProxiedPlayer) event.getSender();

		File playerFile = new File(this.instance.getDataFolder(), "Player/" + player.getUniqueId().toString());
		if (!playerFile.exists()) {
			playerFile.mkdir();
		}

		SimpleDateFormat file = new SimpleDateFormat(this.instance.getConfig().getString("File"));
		SimpleDateFormat time = new SimpleDateFormat(this.instance.getConfig().getString("Time"));

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

		String format = this.instance.getConfig().getString("Format");

		format = format.replace("%Time", time.format(date).toString());
		format = format.replace("%Player", player.getName());
		format = format.replace("%Server", player.getServer().getInfo().getName());
		format = format.replace("%Message", event.getMessage());

		pw.println(format);
		pw.close();
	}

}
