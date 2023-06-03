package com.example.apppokedex;
class Type {
    String name;
}
class Types {
    int slot;
    Type type;
}
class Sprites {
    String front_default;
}
class Stats {
    int base_stat;
}
public class Pokemon {
    String name;
    Sprites sprites;
    Types[] types;
    String weight;
    String height;
    Stats[] stats;
}
