package com.locm.core.general.cmd;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.contentforge.formconstructor.form.CustomForm;
import ru.contentforge.formconstructor.form.SimpleForm;

import com.locm.core.Loader;
import com.locm.core.format.ChatFormat;

public class AdminCommand extends Command {

	public AdminCommand() {
		super("admin", "admin command");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (sender.hasPermission("locm.admin")) {
			if(args[0].equals("fecthgroup")) {
				ChatFormat.fecthGroup();
				sender.sendMessage("Fecth succesfully");
			}else if(args[0].equals("reload")){
				Loader.getInstance().reloadConfig();
				sender.sendMessage("Reload all config..");
			}else if(args[0].equals("citem")){
				CustomForm form = new CustomForm("Custom item");
				//TODO
			}
		}
		return false;
	}
}
