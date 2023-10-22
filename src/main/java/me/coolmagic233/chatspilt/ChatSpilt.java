package me.coolmagic233.chatspilt;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import tip.utils.Api;

import java.util.ArrayList;
import java.util.List;

public class ChatSpilt extends PluginBase implements Listener {
    public static Config config;
    public static List<String> splitWorld = new ArrayList<>();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        splitWorld.addAll(config.getStringList("spilt_world"));
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getCommandMap().register("ChatSplit", new Command("chatsplit") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                switch (strings[0]){
                    case "reload":
                        if (commandSender.isOp()){
                            commandSender.sendMessage("Reloaded config.");
                            config.reload();
                        }
                        break;
                    default: break;
                }
                return false;
            }
        });
    }

    @EventHandler
    public void onChat(PlayerChatEvent e){
        Player player = e.getPlayer();
        if (splitWorld.contains(player.getLevel().getName())){
            e.setCancelled();
            for (Player p : player.getLevel().getPlayers().values()) {
                p.sendMessage(Api.strReplace(config.getString("chat_format").replace("@p",player.getName()).replace("@m",e.getMessage()),player));
            }
        }
    }

}