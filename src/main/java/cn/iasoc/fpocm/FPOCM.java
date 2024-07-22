package cn.iasoc.fpocm;

import org.bukkit.plugin.java.JavaPlugin;

public final class FPOCM extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        new ItemsMeta().load(this);
        this.saveDefaultConfig();
        this.getLogger().info("FPOCM has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("FPOCM has been disabled!");
    }
}
