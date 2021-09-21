package com.locm.core.mines.cmd;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import com.locm.core.Loader;
import com.locm.core.general.entity.CustomModel;
import com.locm.core.mines.FormStorage;
import com.locm.core.mines.obj.Mine;
import com.locm.core.utils.ItemStorage;
import com.locm.core.utils.MineUtils;
import com.locm.core.utils.ModelUtils;
import com.locm.core.utils.RankUtils;
import com.locm.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class MineCommand extends Command {
	public static Config mines = Loader.getLoader().getMinesCfg();
	public static File minesFile = Loader.getLoader().getMinesFile();
	public static HashMap<Player, String> playersInSetupModeMine = new HashMap<>();
	public MineCommand() {
		super("mines", "Mines cmd", "/mines", new String[] { "mine" });
	}
    
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (args.length != 2 && args.length != 0) {
			sendHelpMessage(sender);
			return false;
		}
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				FormStorage.sendForm(p, "&l&fKhu mỏ hiện tại:&e " + RankUtils.getRankByPlayer(p).getName());
			}
		}

		if (sender.hasPermission("locm.admin")) {
			if (args.length == 2) {
				if (args[0].equals("setup")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (playersInSetupModeMine.values().size() < 1) {
							playersInSetupModeMine.put(p, args[1]);
							p.sendMessage(StringUtils.getPrefix() + "Now in Mine Setup Mode!");
							p.sendMessage(StringUtils.getPrefix()
									+ "Please break two blocks to mark the region for mine: " + args[1] + ".");
							p.sendMessage(StringUtils.getPrefix()
									+ StringUtils.translateColors("&cType 'cancel' without '' to cancel setup."));
							p.getInventory().addItem(ItemStorage.mineSetupWand());
							mines.set("Mines." + args[1] + ".name", args[1]);
							mines.set("Mines." + args[1] + ".tpLocX", p.getLocation().getX());
							mines.set("Mines." + args[1] + ".tpLocY", p.getLocation().getY());
							mines.set("Mines." + args[1] + ".tpLocZ", p.getLocation().getZ());
							mines.set("Mines." + args[1] + ".tpYaw", p.getLocation().getYaw());
							mines.set("Mines." + args[1] + ".tpPitch", p.getLocation().getPitch());
							mines.set("Mines." + args[1] + ".tpLocLevelName", p.getLevel().getName());
							mines.save(minesFile);
						} else {
							p.sendMessage(StringUtils.getPrefix() + "Another player is already setting up mines!");
						}
					}
				} else if (args[0].equals("reset")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (MineUtils.getMineByName(args[1]) != null) {
							Mine m = MineUtils.getMineByName(args[1]);
							m.resetMine();
							p.sendMessage(StringUtils.getPrefix() + "Reset mine!");
						} else {
							p.sendMessage(StringUtils.getPrefix() + "Mine non existant!");
						}
					}
				} else if (args[0].equals("settp")) {
					if (sender instanceof Player) {
						Player p = (Player) sender;
						if (MineUtils.getMineByName(args[1]) != null) {
							mines.set("Mines." + args[1] + ".tpLocX", p.getLocation().getX());
							mines.set("Mines." + args[1] + ".tpLocY", p.getLocation().getY());
							mines.set("Mines." + args[1] + ".tpLocZ", p.getLocation().getZ());
							mines.set("Mines." + args[1] + ".tpYaw", p.getLocation().getYaw());
							mines.set("Mines." + args[1] + ".tpPitch", p.getLocation().getPitch());
							mines.set("Mines." + args[1] + ".tpLocLevelName", p.getLevel().getName());
							mines.save(minesFile);
							p.sendMessage(StringUtils.getPrefix() + "Set tp location!");
						} else {
							p.sendMessage(StringUtils.getPrefix() + "Mine non existant!");
						}
					}
				}else if(args[0].equals("setnpc")){
					String geometry = "button";
					Path path = Loader.getInstance().getDataFolder().toPath();
					Path skinPath = path.resolve("button.png");
					Path geometryPath = path.resolve("geometry.json");
					try{
						Skin skin = ModelUtils.createSkin(geometry, skinPath, geometryPath);
						CustomModel model = ModelUtils.createModel((Player) sender, skin);
						model.spawnToAll();
						model.setNameTag(TextFormat.colorize(args[1]));
						model.setNameTagAlwaysVisible();
					}catch(IOException exception){}
				}
			}
		}
		return false;
	}

	public void sendHelpMessage(CommandSender sender) {
		sender.sendMessage(StringUtils.translateColors("&cIncorrect Usage!"));
	}
}
