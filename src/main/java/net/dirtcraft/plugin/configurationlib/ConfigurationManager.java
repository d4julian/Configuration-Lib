package net.dirtcraft.plugin.configurationlib;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ConfigurationManager {

    public static final ConfigurationOptions options = ConfigurationOptions.defaults().setShouldCopyDefaults(true);
    private static final ArrayList<Configuration> configurations = new ArrayList<>();

    private static HashMap<String, Path> directoryMap = new HashMap<>();

    static void setup(PluginContainer container, Path path) {
        directoryMap.put(container.getId(), path);
        try {
            if (!Files.exists(path)) Files.createDirectory(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static <E extends IConfiguration> void createConfiguration(Configuration<E> configuration) throws IOException {
        Path config = directoryMap.get(configuration.getContainer().getId()).resolve(configuration.getContainer().getId() + ".conf");
        configuration.setPath(config);
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setPath(config)
                .build();
        CommentedConfigurationNode node = loader.load(options);
        E configSerializable = configuration.getConfigSerializable();
        configSerializable = (E) node.getValue(configSerializable);
        loader.save(node);
        configuration.setConfigSerializable(configSerializable);
        configuration.setLoader(loader);
        configuration.setNode(node);
        configurations.add(configuration);
    }

    static <E extends IConfiguration> void createConfiguration(Configuration<E> configuration, String fileName) throws IOException {
        Path config = directoryMap.get(configuration.getContainer().getId()).resolve(fileName + ".conf");
        configuration.setPath(config);
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setPath(config)
                .build();
        CommentedConfigurationNode node = loader.load(options);
        E configSerializable = configuration.getConfigSerializable();
        configSerializable = (E) node.getValue(configSerializable);
        loader.save(node);
        configuration.setConfigSerializable(configSerializable);
        configuration.setLoader(loader);
        configuration.setNode(node);
        configurations.add(configuration);
    }

    static List<Configuration> getConfigurations() {
        return Collections.unmodifiableList(configurations);
    }
}
