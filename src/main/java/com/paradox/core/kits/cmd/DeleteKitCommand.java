package com.paradox.core.kits.cmd;

import java.io.File;

import com.paradox.core.Loader;
import com.paradox.core.kits.KitHandler;
import com.paradox.core.kits.obj.Kit;
import com.paradox.core.utils.StringUtils;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

public class DeleteKitCommand extends Command {
	public static Config kits = Loader.getLoader().getKitsCfg();
	public static File kitsFile = Loader.getLoader().getKitsFile();

	public DeleteKitCommand() {
		super("deletekit");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("paradox.owner")) {
				if (args.length != 1) {
					p.sendMessage(StringUtils.translateColors("Proper usage: /deletekit <name>"));
					return false;
				}
				for (Kit kit : KitHandler.getAllKitsFromConfig()) {
					if (args[0].equals(kit.getName())) {
						kits.set("Kits." + args[0], null);
						kits.save(kitsFile);
						p.sendMessage(StringUtils.getPrefix() + "Deleted the kit" + args[0] + ".");
					}
				}
			}
		}
		return false;
	}

}
