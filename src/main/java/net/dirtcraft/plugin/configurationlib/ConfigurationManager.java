package net.dirtcraft.plugin.configurationlib;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
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
    private static final ArrayList<Configuration<? extends IConfiguration>> configurations = new ArrayList<>();

    private static HashMap<String, Path> directoryMap = new HashMap<>();

    static void setup(PluginContainer container, Path path) {
        directoryMap.put(container.getId(), path);
        try {
            if (!Files.exists(path)) Files.createDirectory(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static void createConfiguration(Configuration<? extends IConfiguration> configuration) throws IOException, ObjectMappingException {
        Path config = directoryMap.get(configuration.getContainer().getId()).resolve(configuration.getContainer().getId() + ".conf");
        configuration.setPath(config);
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setPath(config)
                .build();
        configuration.setLoader(loader);
        CommentedConfigurationNode node = loader.load(options);
        Object configSerializable = configuration.getConfigSerializable();
        TypeToken token = TypeToken.of(configSerializable.getClass());
        configSerializable = node.getValue(token, configSerializable);
        configuration.setConfigSerializable(configSerializable);
        loader.save(node);
        configurations.add(configuration);
    }

    static void createConfiguration(Configuration<? extends IConfiguration> configuration, String fileName) throws IOException, ObjectMappingException {
        Path config = directoryMap.get(configuration.getContainer().getId()).resolve(fileName + ".conf");
        configuration.setPath(config);
        ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder()
                .setDefaultOptions(options)
                .setPath(config)
                .build();
        configuration.setLoader(loader);
        CommentedConfigurationNode node = loader.load(options);
        Object configSerializable = configuration.getConfigSerializable();
        TypeToken token = TypeToken.of(configSerializable.getClass());
        node.getValue(token, configSerializable);
        loader.save(node);
        configurations.add(configuration);
    }

    static List<Configuration<? extends IConfiguration>> getConfigurations() {
        return Collections.unmodifiableList(configurations);
    }
}
