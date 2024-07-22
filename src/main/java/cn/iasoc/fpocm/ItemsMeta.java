package cn.iasoc.fpocm;

import org.bukkit.configuration.MemorySection;

import java.util.HashMap;
import java.util.Map;

public class ItemsMeta {
    private static final Map<String, Object> cache = new HashMap<>();

    public void load(FPOCM plugin) {
        plugin.getConfig().getKeys(true).forEach(key -> {
            Object value = plugin.getConfig().get(key);
            if (value instanceof MemorySection) {
                return;
            }
            cache.put(key, value);
        });
    }

    public static double get(String key) {
        return (double) cache.get(key);
    }

}