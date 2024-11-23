package com.absolutelyaryan.neoforge.capabilities.types;

import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import java.util.ArrayList;
import java.util.List;

/* If anyone from Terrarium Earth LLC has a problem with this contact me (Fej1Fun/Fej1Dev) on Discord.
MIT License

Copyright (c) 2024 Terrarium Earth LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
*/

public interface Registerable {
    List<Registerable> REGISTERABLES = new ArrayList<>();

    static void registerAll(RegisterCapabilitiesEvent event) {
        REGISTERABLES.forEach(registerable -> registerable.register(event));
    }

    void register(RegisterCapabilitiesEvent event);

    default void registerSelf() {
        REGISTERABLES.add(this);
    }
}
