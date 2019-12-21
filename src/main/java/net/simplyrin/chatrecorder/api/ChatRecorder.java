package net.simplyrin.chatrecorder.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import net.md_5.bungee.config.Configuration;
import net.simplyrin.config.Config;

/**
 * Created by SimplyRin on 2019/12/21.
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
@AllArgsConstructor
public class ChatRecorder {

	private File dataFolder;

	public Configuration init() {
		this.dataFolder.mkdir();

		File file = new File(this.dataFolder, "config.yml");
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
			config.set("Format", "[%Time] %Player@%Server: %Message");

			Config.saveConfig(config, file);
		}

		File player = new File(this.dataFolder, "Player");
		player.mkdir();

		return Config.getConfig(file);
	}

	public void saveChat(Configuration config, FakePlayer player, String message) {
		File playerFile = new File(this.dataFolder, "Player/" + player.getUniqueId().toString());
		if (!playerFile.exists()) {
			playerFile.mkdir();
		}

		SimpleDateFormat file = new SimpleDateFormat(config.getString("File"));
		SimpleDateFormat time = new SimpleDateFormat(config.getString("Time"));

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

		String format = config.getString("Format");

		format = format.replace("%Time", time.format(date).toString());
		format = format.replace("%Player", player.getName());
		format = format.replace("%Server", player.getServer());

		if (message != null && message.length() > 0) {
			format = format.replace("%Message", message);
		} else {
			format = format.replace("%Message", "#emptyMessage");
		}

		pw.println(format);
		pw.close();
	}

}
