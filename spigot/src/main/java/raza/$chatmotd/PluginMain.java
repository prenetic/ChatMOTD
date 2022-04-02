package raza.$chatmotd;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;

public class PluginMain extends JavaPlugin implements Listener {

	private static PluginMain instance;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		PluginMain.createResourceFile("chatmotd.txt");
		try {
			PluginMain.getInstance().getLogger().info("Message of the day (MOTD) is:");
			for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
					new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
					java.nio.charset.StandardCharsets.UTF_8)) {
				PluginMain.getInstance().getLogger()
						.info(ChatColor.translateAlternateColorCodes('&', String.valueOf(FINAL_loopValue1)));
			}
			PluginMain.getInstance().getLogger().info("Plugin has been enabled.");
			new Metrics(PluginMain.getInstance(), ((int) (14685d)));
			if (PluginMain.hasSpigotUpdate("100844")) {
				PluginMain.getInstance().getLogger()
						.warning((ChatColor.translateAlternateColorCodes('&',
								"&6There is a &lChatMOTD&r&6 update available on SpigotMC. Version: ")
								+ PluginMain.getSpigotVersion("100844")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		try {
			PluginMain.getInstance().getLogger().info("Plugin has been disabled.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] commandArgs) {
		if (command.getName().equalsIgnoreCase("motd")) {
			try {
				for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
						new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
						java.nio.charset.StandardCharsets.UTF_8)) {
					commandSender
							.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(FINAL_loopValue1)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		if (command.getName().equalsIgnoreCase("motdset")) {
			String[] commandArgsStringList = null;
			Path outputPath = null;
			try {
				commandArgsStringList = String.join(" ", commandArgs).split(Pattern.quote("\\n"));
				outputPath = Path.of(String.valueOf(PluginMain.getInstance().getDataFolder()) + "/chatmotd.txt");
				Files.write(outputPath, Arrays.asList(commandArgsStringList));
				commandSender.sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&6Message of the day (MOTD) has been set!"));
				for (String message : commandArgsStringList) {
					org.bukkit.Bukkit.broadcast(ChatColor.translateAlternateColorCodes('&', message), "chatmotd.view");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}

	public static void procedure(String procedure, List procedureArgs) throws Exception {
	}

	public static Object function(String function, List functionArgs) throws Exception {
		return null;
	}

	public static void createResourceFile(String path) {
		Path file = getInstance().getDataFolder().toPath().resolve(path);
		if (Files.notExists(file)) {
			try (InputStream inputStream = PluginMain.class.getResourceAsStream("/" + path)) {
				Files.createDirectories(file.getParent());
				Files.copy(inputStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PluginMain getInstance() {
		return instance;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void event1(org.bukkit.event.player.PlayerJoinEvent event) throws Exception {
		if (event.getPlayer().hasPermission("chatmotd.view")) {
			for (Object FINAL_loopValue1 : java.nio.file.Files.readAllLines(
					new File(String.valueOf(PluginMain.getInstance().getDataFolder()), "chatmotd.txt").toPath(),
					java.nio.charset.StandardCharsets.UTF_8)) {
				((org.bukkit.command.CommandSender) (Object) ((org.bukkit.entity.Player) event.getPlayer()))
						.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(FINAL_loopValue1)));
			}
		}
	}

	public static String getSpigotVersion(String resourceId) {
		String newVersion = PluginMain.getInstance().getDescription().getVersion();
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
				java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
			if (scanner.hasNext())
				newVersion = String.valueOf(scanner.next());
		} catch (java.io.IOException ioException) {
			ioException.printStackTrace();
		}
		return newVersion;
	}

	public static boolean hasSpigotUpdate(String resourceId) {
		boolean hasUpdate = false;
		try (java.io.InputStream inputStream = new java.net.URL(
				"https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
				java.util.Scanner scanner = new java.util.Scanner(inputStream)) {
			if (scanner.hasNext())
				hasUpdate = !PluginMain.getInstance().getDescription().getVersion().equalsIgnoreCase(scanner.next());
		} catch (java.io.IOException ioException) {
			ioException.printStackTrace();
			hasUpdate = false;
		}
		return hasUpdate;
	}
}
