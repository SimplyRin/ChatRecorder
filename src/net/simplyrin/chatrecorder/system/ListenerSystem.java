package net.simplyrin.chatrecorder.system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.simplyrin.chatrecorder.ChatRecorder;

public class ListenerSystem implements Listener {

	@EventHandler
	public void onChat(ChatEvent event) {
		ProxiedPlayer player = (ProxiedPlayer) event.getSender();

		File p = new File(ChatRecorder.getPlugin().getDataFolder(), "Player/" + player.getUniqueId().toString());
		if(!p.exists()) {
			p.mkdir();
		}

		Calendar c = Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone(Config.getTarget("TimeZone"));

		c.setTimeZone(tz);

		SimpleDateFormat sdf = new SimpleDateFormat(Config.getTarget("File"));
		SimpleDateFormat sdff = new SimpleDateFormat(Config.getTarget("Time"));

		String day = sdf.format(c.getTime());
		String time = sdff.format(c.getTime());

		File f = new File(p, day + ".log");

		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String format = Config.getTarget("Format");

		format = format.replaceAll("%Time", time.toString());
		format = format.replaceAll("%Player", player.getName());
		format = format.replaceAll("%Server", player.getServer().getInfo().getName());
		format = format.replaceAll("%Message", event.getMessage());


		pw.println(format);
		pw.close();
	}

}
