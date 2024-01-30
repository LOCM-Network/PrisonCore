package com.locm.core.general.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.element.*;

import com.locm.core.Loader;
import com.locm.core.format.ChatFormat;

public class AdminCommand extends Command {

	public AdminCommand() {
		super("admin", "admin command");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender.hasPermission("locm.admin")) {
			switch (args[0]) {
				case "fecthgroup":
					ChatFormat.fecthGroup();
					sender.sendMessage("Fecth succesfully");
					break;
				case "reload":
					Loader.getInstance().reloadConfig();
					sender.sendMessage("Reload all config..");
					break;
				case "citem":
					CustomForm form = new CustomForm("Custom item");
					form.addElement("name", new Input("Ten"));
					form.addElement("lore", new Input("lore # de xuong hang"));
					form.setHandler((p, response) -> {
						Item item = ((Player) sender).getInventory().getItemInHand();
						String name = response.getInput("name").getValue();
						String lore = response.getInput("lore").getValue();
						if (!name.equals("")) {
							item.setCustomName(TextFormat.colorize(name));
						}
						if (!lore.equals("")) {
							item.setLore(lore.replace("#", "\n"));
						}
						((Player) sender).getInventory().setItemInHand(item);
					});
					form.send((Player) sender);
					break;
			}
		}
		return true;
	}
}
