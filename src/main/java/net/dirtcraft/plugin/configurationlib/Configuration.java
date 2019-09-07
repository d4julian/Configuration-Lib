package net.dirtcraft.plugin.configurationlib;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.plugin.PluginContainer;

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

    public void setConfigSerializable(Object configSerializable) {
        this.configuration = (E) configSerializable;
    }

    void setPath(Path path) {
        this.path = path;
    }

    public PluginContainer getContainer() {
        return container;
    }
}
