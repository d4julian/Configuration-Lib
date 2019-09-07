package net.dirtcraft.plugin.configurationlib;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

@Plugin(
        id = "configuration-lib",
        name = "Configuration Lib",
        description = "An easy way for developers to create configurations!",
        url = "https://dirtcraft.net/",
        authors = "juliann"
)
public class ConfigurationLib {

    @Listener
    public void onConfigurationReload(ConfigurationReloadEvent event) {
        for (Configuration configuration : ConfigurationManager.getConfigurations()) {
            if (configuration.getContainer().getId().equalsIgnoreCase(event.getContainer().getId())) configuration.load();
        }
    }

    public static void setup(PluginContainer container, Path path) {
        ConfigurationManager.setup(container, path);
    }

}
