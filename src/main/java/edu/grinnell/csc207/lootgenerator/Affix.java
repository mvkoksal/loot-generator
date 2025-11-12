package edu.grinnell.csc207.lootgenerator;

public class Affix {
    String name;
    String mod1code;
    int mod1min;
    int mod1max;

    public Affix(String name, String mod1code, int mod1min, int mod1max) {
        this.name = name;
        this.mod1code = mod1code;
        this.mod1min = mod1min;
        this.mod1max = mod1max;
    }
}
