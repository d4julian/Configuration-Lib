package net.dirtcraft.plugin.configurationlib;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
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
        for (Configuration<? extends IConfiguration> configuration : ConfigurationManager.getConfigurations()) {
            if (configuration.getContainer() != event.getContainer()) continue;
            try {
                ConfigurationLoader<CommentedConfigurationNode> loader = configuration.getLoader();
                CommentedConfigurationNode node = loader.load(ConfigurationManager.options);
                Object configSerializable = configuration.getConfigSerializable();
                TypeToken token = TypeToken.of(configSerializable.getClass());
                configSerializable = node.getValue(token, configSerializable);
                configuration.setConfigSerializable(configSerializable);
                configuration.setNode(node);
                loader.save(configuration.getNode());
                configuration.setLoader(loader);
            } catch (IOException | ObjectMappingException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void setup(PluginContainer container, Path path) {
        ConfigurationManager.setup(container, path);
    }

}
