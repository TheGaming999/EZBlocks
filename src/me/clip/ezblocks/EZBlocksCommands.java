package me.clip.ezblocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EZBlocksCommands implements CommandExecutor {
	
	EZBlocks plugin;
	
	public EZBlocksCommands(EZBlocks i) {
		plugin = i;
	}
	
	private void sms(CommandSender p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
		
		if (!(s instanceof Player)) {
			/*
			 * blocks
			 */
			if (args.length == 0) {

				sms(s, "&c&lEZ&f&lB&flocks &7version &f" + plugin.getDescription().getVersion());
				sms(s, "&7Created by: &cextended_clip");
			
			} else if (args.length > 0) {
				/*
				 * blocks help
				 */
				if (args[0].equalsIgnoreCase("help")) {
					sms(s, "&c&lEZ&f&lB&flocks &7Help");
					sms(s, "&f/blocks check <player> &7- &cView others blocks broken");
					sms(s, "&f/blocks set <player> <blocks>&7- &cSet a players blocks broken");
					sms(s, "&f/blocks version &7- &cView plugin version");
					sms(s, "&f/blocks reload &7- &cReload the ezblocks config");
					return true;
				}
				else if (args[0].equalsIgnoreCase("reload")) {
					plugin.reload();
					sms(s, "&cConfiguration successfully reloaded!");
					return true;
				}
				else if (args[0].equalsIgnoreCase("version")) {

					sms(s, "&c&lEZ&f&lB&flocks &7version &f" + plugin.getDescription().getVersion());
					sms(s, "&7Created by: &cextended_clip");
					return true;
				}
				else if (args[0].equalsIgnoreCase("check")) {

					if (args.length >= 2) {
						
						
						Player target = Bukkit.getServer().getPlayer(args[1]);
						
						if (target == null) {
							sms(s, "&f"+args[1]+" &cis not online!");
							return true;
						}
						
						String uid = target.getUniqueId().toString();
						
						if (BreakHandler.breaks.containsKey(uid)) {
							sms(s, EZBlocks.options.getBrokenMsg().replace("%player%", target.getName())
									.replace("%blocksbroken%", BreakHandler.breaks.get(uid)+""));
							return true;
						}
						else {
							if (plugin.playerconfig.hasData(uid)) {
								BreakHandler.breaks.put(uid, plugin.playerconfig.getBlocksBroken(uid));
							}
							else {
								BreakHandler.breaks.put(uid, 0);
							}
							sms(s, EZBlocks.options.getBrokenMsg().replace("%player%", target.getName())
									.replace("%blocksbroken%", BreakHandler.breaks.get(uid)+""));	
						}
						
						
						
					}
					else {
						sms(s, "&cIncorrect usage! &7/blocks check <player>");
					}
					
					return true;
				} else if (args[0].equalsIgnoreCase("set")) {
					
					
					if (args.length != 3) {
						sms(s, "&cIncorrect usage! &7/blocks set <player> <amount>");
						return true;
					}
						
					Player target = Bukkit.getServer().getPlayer(args[1]);
						
					if (target == null) {
						sms(s, "&f"+args[1]+" &cis not online!");
						return true;
					}
					
					if (!plugin.breakhandler.isInt(args[2])) {
						sms(s, "&f"+args[2]+" &cis not a valid amount!");
						return true;
					}
					
					int b = Integer.parseInt(args[2]);
						
					String uid = target.getUniqueId().toString();
						
					BreakHandler.breaks.put(uid, b);
					
					sms(s, "&aBlocks broken set to &f"+b+" &afor &f"+target.getName());
					
					return true;
				}
				else {
					sms(s, "&cIncorrect usage! &7/blocks help");
				}
				
			}
			return true;
		}
		
		Player p = (Player) s;
		
		String uuid = p.getUniqueId().toString();

		/*
		 * blocks
		 */
		if (args.length == 0) {
			
			if (EZBlocks.options.useBlocksCommand()) {
				if (BreakHandler.breaks.containsKey(uuid)) {
					sms(s, EZBlocks.options.getBrokenMsg().replace("%player%", p.getName())
								.replace("%blocksbroken%", BreakHandler.breaks.get(uuid) + ""));
					return true;
				} else {
					if (plugin.playerconfig.hasData(uuid)) {
						BreakHandler.breaks.put(uuid, plugin.playerconfig.getBlocksBroken(uuid));
					} else {
						BreakHandler.breaks.put(uuid, 0);
					}
					sms(s, EZBlocks.options.getBrokenMsg().replace("%player%", p.getName())
								.replace("%blocksbroken%", BreakHandler.breaks.get(uuid) + ""));
				}
			}
			return true;
		}
		
		else if (args.length > 0) {
			/*
			 * blocks help
			 */
			if (args[0].equalsIgnoreCase("help")) {
				if (!p.hasPermission("ezblocks.admin")) {
					if (EZBlocks.options.useBlocksCommand()) {
						sms(s, "&cEZBlocks &7Help");
						sms(s, "&f/blocks &7- &cView your blocks broken");
					}
					return true;
				}
				sms(s, "&c&lEZ&f&lB&flocks &7Help");
				if (EZBlocks.options.useBlocksCommand()) {
					sms(s, "&f/blocks &7- &cView your blocks broken");
				}
				sms(s, "&f/blocks check <player> &7- &cView others blocks broken");
				sms(s, "&f/blocks set <player> <blocks>&7- &cSet a players blocks broken");
				sms(s, "&f/blocks version &7- &cView plugin version");
				sms(s, "&f/blocks reload &7- &cReload the ezblocks config");
				return true;
			}
			else if (args[0].equalsIgnoreCase("reload")) {
				if (!p.hasPermission("ezblocks.admin")) {
					sms(s, "&cYou don't have permission to do that!");
					return true;
				}
				plugin.reload();
				sms(s, "&cConfiguration successfully reloaded!");
				return true;
			}
			else if (args[0].equalsIgnoreCase("version")) {
				if (!p.hasPermission("ezblocks.admin")) {
					sms(s, "&cYou don't have permission to do that!");
					return true;
				}
				sms(s, "&c&lEZ&f&lB&flocks &7version &f" + plugin.getDescription().getVersion());
				sms(s, "&7Created by: &cextended_clip");
				return true;
			}
			else if (args[0].equalsIgnoreCase("check")) {
				if (!p.hasPermission("ezblocks.check")) {
					sms(s, "&cYou don't have permission to do that!");
					return true;
				}
				if (args.length >= 2) {
					
					
					Player target = Bukkit.getServer().getPlayer(args[1]);
					
					if (target == null) {
						sms(s, "&f"+args[1]+" &cis not online!");
						return true;
					}
					
					String uid = target.getUniqueId().toString();
					
					if (BreakHandler.breaks.containsKey(uid)) {
						sms(s, EZBlocks.options.getBrokenMsg().replace("%player%", target.getName())
								.replace("%blocksbroken%", BreakHandler.breaks.get(uid)+""));
						return true;
					}
					else {
						if (plugin.playerconfig.hasData(uid)) {
							BreakHandler.breaks.put(uid, plugin.playerconfig.getBlocksBroken(uid));
						}
						else {
							BreakHandler.breaks.put(uid, 0);
						}
						sms(s, EZBlocks.options.getBrokenMsg().replace("%player%", target.getName())
								.replace("%blocksbroken%", BreakHandler.breaks.get(uid)+""));	
					}
					
					
					
				}
				else {
					sms(s, "&cIncorrect usage! &7/blocks check <player>");
				}
				
				return true;
			}
			else if (args[0].equalsIgnoreCase("set")) {
				
				if (!p.hasPermission("ezblocks.set")) {
					sms(s, "&cYou don't have permission to do that!");
					return true;
				}
				
				if (args.length != 3) {
					sms(s, "&cIncorrect usage! &7/blocks set <player> <amount>");
					return true;
				}
					
				Player target = Bukkit.getServer().getPlayer(args[1]);
					
				if (target == null) {
					sms(s, "&f"+args[1]+" &cis not online!");
					return true;
				}
				
				if (!plugin.breakhandler.isInt(args[2])) {
					sms(s, "&f"+args[2]+" &cis not a valid amount!");
					return true;
				}
				
				int b = Integer.parseInt(args[2]);
				
				if (b < 0) {
					sms(s, "&f"+args[2]+" &cis not a valid amount!");
					return true;
				}
					
				String uid = target.getUniqueId().toString();
					
				BreakHandler.breaks.put(uid, b);
				
				sms(s, "&aBlocks broken set to &f"+b+" &afor &f"+target.getName());
				
				return true;
			}
			else {
				sms(s, "&cIncorrect usage! &7/blocks help");
			}
			
		}
		
		return true;
	}
}