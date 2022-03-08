package cc.carm.plugin.commanditem.configuration;

import cc.carm.lib.easyplugin.configuration.values.ConfigValue;

public class PluginConfig {

    public static final ConfigValue<Boolean> DEBUG = new ConfigValue<>(
            "debug", Boolean.class, false
    );

    public static final ConfigValue<Boolean> METRICS = new ConfigValue<>(
            "metrics", Boolean.class, true
    );

    public static final ConfigValue<Boolean> CHECK_UPDATE = new ConfigValue<>(
            "check-update", Boolean.class, true
    );

    public static final ConfigValue<Boolean> LOG_STORAGE = new ConfigValue<>(
            "log-storage.enable", Boolean.class, true
    );

}