package net.dirtcraft.plugin.configurationlib;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Path;

public class Configuration<E extends IConfiguration> {

    private E configuration;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode node;
    private Path path;
    private PluginContainer container;

    public Configuration(E configuration, PluginContainer container) {
        this.configuration = configuration;
        this.container = container;
        try {
            ConfigurationManager.createConfiguration(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Configuration(E configuration, PluginContainer container, String fileName) {
        this.configuration = configuration;
        this.container = container;
        try {
            ConfigurationManager.createConfiguration(this, fileName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
        return loader;
    }

    public CommentedConfigurationNode getNode() {
        return node;
    }

    public Path getPath() {
        return path;
    }

    public void setLoader(ConfigurationLoader<CommentedConfigurationNode> loader) {
        this.loader = loader;
    }

    public void setNode(CommentedConfigurationNode node) {
        this.node = node;
    }

    public E getConfigSerializable() {
        return configuration;
    }

    public void setConfigSerializable(E configSerializable) {
        this.configuration = configSerializable;
    }

    void setPath(Path path) {
        this.path = path;
    }

    public PluginContainer getContainer() {
        return container;
    }

    public void save(E configSerializable) {
        try {
            ConfigurationLoader<CommentedConfigurationNode> loader = this.loader;
            CommentedConfigurationNode node = loader.load(ConfigurationManager.options);
            TypeToken token = TypeToken.of(configSerializable.getClass());
            node.setValue(token, configSerializable);
            loader.save(node);
            this.setConfigSerializable(configSerializable);
            this.setLoader(loader);
            this.setNode(node);
        } catch (IOException | ObjectMappingException exception) {
            exception.printStackTrace();
        }
    }

    public void load() {
        try {
            ConfigurationLoader<CommentedConfigurationNode> loader = this.loader;
            CommentedConfigurationNode node = loader.load(ConfigurationManager.options);
            E configSerializable = this.getConfigSerializable();
            TypeToken token = TypeToken.of(configSerializable.getClass());
            configSerializable = node.getValue((TypeToken<E>) token, configSerializable);
            loader.save(node);
            this.setConfigSerializable(configSerializable);
            this.setLoader(loader);
            this.setNode(node);
        } catch (IOException | ObjectMappingException exception) {
            exception.printStackTrace();
        }
    }
}
