package ru.moongl.minecraft.dataheadfix;

import org.bukkit.plugin.java.JavaPlugin;
import ru.moongl.minecraft.dataheadfix.events.HeadFixListener;


public final class Dataheadfix extends JavaPlugin {

    private static Dataheadfix instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new HeadFixListener(), getInstance());

    }

    @Override
    public void onDisable() {

    }

    public static Dataheadfix getInstance() {
        return instance;
    }
}
