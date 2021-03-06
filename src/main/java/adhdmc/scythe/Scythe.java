package adhdmc.scythe;

import adhdmc.scythe.Commands.CommandHandler;
import adhdmc.scythe.Commands.SubCommands.HelpCommand;
import adhdmc.scythe.Commands.SubCommands.ReloadCommand;
import adhdmc.scythe.Commands.SubCommands.ToggleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Scythe extends JavaPlugin {
    public static Scythe plugin;

    @Override

    public void onEnable() {
        plugin = this;
        if (plugin.getServer().getPluginManager().isPluginEnabled("CoreProtect")){
            getServer().getPluginManager().registerEvents(new InteractListenerDependsCoreprotect(), this);
        } else {
            getServer().getPluginManager().registerEvents(new InteractListener(), this);
        }
        this.getCommand("scythe").setExecutor(new CommandHandler());
        this.saveDefaultConfig();
        registerCommands();
        MessageHandler.loadPluginMsgs();
    }


    private void registerCommands() {
        CommandHandler.subcommandList.put("toggle", new ToggleCommand());
        CommandHandler.subcommandList.put("reload", new ReloadCommand());
        CommandHandler.subcommandList.put("help", new HelpCommand());
    }
}

