package com.ConorGrocock.src;

import java.io.File;
import java.io.IOException;



import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Kits extends JavaPlugin {

	public void onEnable() {
		try {
			saveConfig();
			setupconfig(getConfig());
			saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (c.getName().equalsIgnoreCase("kit")) {
			if (s instanceof Player) {
				Player p = (Player) s;

				if (a.length == 0) {
					String[] classes = getConfig().getString("Kits.Names").split(",");
					for (String st : classes){
						if(st != null){
							p.sendMessage("[" + ChatColor.AQUA + st + ChatColor.WHITE + "] " + ChatColor.GRAY + ": " + ChatColor.DARK_GRAY + "Adds you to the " + st + " Class");
						}
					}
				}else{
					
					for (String st : getConfig().getConfigurationSection("Kits").getKeys(false)){
						if(a[0].equalsIgnoreCase(st)){
							p.getInventory().clear();
							try {
								String Items = getConfig().getString("Kits." + s + "Items");
								String[] indiItems = Items.split(",");
								for (String s1 : indiItems){
									String[] itemAmounts = s1.split("-");
									ItemStack is = new ItemStack(Material.getMaterial(itemAmounts[0]), Integer.valueOf(itemAmounts[1]));
									p.getInventory().addItem(is);
									
								}
								p.updateInventory();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return false;
	}

	private void setupconfig(FileConfiguration config) throws IOException {
		if (new File(getDataFolder(), "RESET.FILE").exists()) {

			config.set("Kits.Mage.Items", "50-64,278-1,277-1");
			config.set("Kits.Archer.Items", "64-1-1,5-1");

			config.set("Kits.Names", "Mage,Archer");
			new File(getDataFolder(), "RESET.FILE").createNewFile();
		}

	}

	public void onDisable() {
	}

}