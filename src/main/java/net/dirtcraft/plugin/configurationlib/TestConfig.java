package net.dirtcraft.plugin.configurationlib;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;

@ConfigSerializable
public class TestConfig implements IConfiguration {

    @Setting
    private String string = "This is a string!";

    @Setting
    private boolean bool = true;

    @Setting
    private int integer = 0;

    @Setting
    private ArrayList<String> strings = new ArrayList<>();

    public String getString() {
        return string;
    }

    public boolean isBool() {
        return bool;
    }

    public int getInteger() {
        return integer;
    }

    public ArrayList<String> getStrings() {
        return strings;
    }
}
