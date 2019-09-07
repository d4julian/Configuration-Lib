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
    public <T extends IConfiguration> void onConfigurationReload(ConfigurationReloadEvent event) {
        for (Configuration<T> configuration : ConfigurationManager.getConfigurations()) {
            if (!configuration.getContainer().getId().equalsIgnoreCase(event.getContainer().getId())) continue;
            try {
                ConfigurationLoader<CommentedConfigurationNode> loader = configuration.getLoader();
                CommentedConfigurationNode node = loader.load(ConfigurationManager.options);
                T configSerializable = configuration.getConfigSerializable();
                TypeToken token = TypeToken.of(configSerializable.getClass());
                configSerializable = node.getValue((TypeToken<T>)token, configSerializable);
                loader.save(node);
                configuration.setConfigSerializable(configSerializable);
                configuration.setLoader(loader);
                configuration.setNode(node);
            } catch (IOException | ObjectMappingException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void setup(PluginContainer container, Path path) {
        ConfigurationManager.setup(container, path);
    }

}
