package net.dirtcraft.plugin.configurationlib;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.plugin.PluginContainer;

public class ConfigurationReloadEvent extends AbstractEvent {

    private final PluginContainer container;

    public ConfigurationReloadEvent(PluginContainer container) {
        this.container = container;
    }

    public PluginContainer getContainer() {
        return container;
    }

    @Override
    public Cause getCause() {
        return Cause.builder().build(EventContext.empty());
    }
}
