package com.locm.core.general.cmd;

import com.locm.core.Loader;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import ru.contentforge.formconstructor.form.SimpleForm;

public class MenuCommand extends Command{
    
    public MenuCommand(){
        super("menu", "Menu prison");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args){
        SimpleForm form = new SimpleForm(TextFormat.colorize("&l&ePRISON MENU"));
        Loader.getInstance().getConfig().getSection("menu").getAll().getKeys(false).forEach(key -> {
            String button = Loader.getInstance().getConfig().getString("menu." + key + ".name");
            String cmd = Loader.getInstance().getConfig().getString("menu." + key + ".cmd");
            form.addButton(TextFormat.colorize(button), (p, btn) -> Server.getInstance().dispatchCommand(p, cmd.replace("{player}", p.getName())));
        });
        form.send((Player) sender);
        return true;
    }
}
