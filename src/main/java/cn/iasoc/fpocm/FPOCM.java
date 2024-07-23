package cn.iasoc.fpocm;

import cn.iasoc.fpocm.ocm.ModulePlayerKnockback;
import cn.iasoc.fpocm.ocm.ModuleSwordSweep;
import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import org.bukkit.plugin.java.JavaPlugin;

public final class FPOCM extends JavaPlugin {
    private static TaskScheduler scheduler;
    public static TaskScheduler getScheduler() {
        return scheduler;
    }
    @Override
    public void onEnable() {
        scheduler = UniversalScheduler.getScheduler(this);

        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        new ItemsMeta().load(this);
        getServer().getPluginManager().registerEvents(new ModuleSwordSweep(this), this);
        getServer().getPluginManager().registerEvents(new ModulePlayerKnockback(this), this);

        this.saveDefaultConfig();
        this.getLogger().info("FPOCM has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("FPOCM has been disabled!");
    }
}
