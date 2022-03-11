package cc.carm.plugin.commanditem;

import cc.carm.lib.easyplugin.EasyPlugin;
import cc.carm.lib.easyplugin.i18n.EasyPluginMessageProvider;
import cc.carm.plugin.commanditem.command.CMDItemsCommand;
import cc.carm.plugin.commanditem.configuration.PluginConfig;
import cc.carm.plugin.commanditem.hooker.GHUpdateChecker;
import cc.carm.plugin.commanditem.listener.ItemListener;
import cc.carm.plugin.commanditem.manager.ConfigManager;
import cc.carm.plugin.commanditem.manager.ItemsManager;
import cc.carm.plugin.commanditem.util.JarResourceUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;

import java.util.Optional;

public class Main extends EasyPlugin {

    private static Main instance;

    public Main() {
        super(new EasyPluginMessageProvider.zh_CN());
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

    protected ItemsManager itemsManager;

    @Override
    protected boolean initialize() {

        info("加载配置文件...");
        if (!ConfigManager.initConfig()) {
            severe("配置文件初始化失败，请检查。");
            setEnabled(false);
            return false;
        }

        info("加载物品配置...");
        this.itemsManager = new ItemsManager();
        this.itemsManager.initialize();

        info("注册指令...");
        registerCommand("CommandItem", new CMDItemsCommand());

        info("注册监听器...");
        regListener(new ItemListener());

        if (PluginConfig.METRICS.get()) {
            info("启用统计数据...");
            new Metrics(this, 14459);
        }

        if (PluginConfig.CHECK_UPDATE.get()) {
            log("开始检查更新...");
            GHUpdateChecker checker = new GHUpdateChecker(getLogger(), "CarmJos", "CommandItem");
            getScheduler().runAsync(() -> checker.checkUpdate(getDescription().getVersion()));
        } else {
            log("已禁用检查更新，跳过。");
        }

        return true;
    }

    @Override
    protected void shutdown() {

        log("卸载监听器...");
        Bukkit.getServicesManager().unregisterAll(this);

    }

    @Override
    public boolean isDebugging() {
        return PluginConfig.DEBUG.get();
    }

    @Override
    public void outputInfo() {
        Optional.ofNullable(JarResourceUtils.readResource(this.getResource("PLUGIN_INFO"))).ifPresent(this::log);
    }

    public static void info(String... messages) {
        getInstance().log(messages);
    }

    public static void severe(String... messages) {
        getInstance().error(messages);
    }

    public static void debugging(String... messages) {
        getInstance().debug(messages);
    }
}
