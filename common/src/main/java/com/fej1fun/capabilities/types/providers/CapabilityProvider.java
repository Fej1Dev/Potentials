package com.fej1fun.capabilities.types.providers;

@FunctionalInterface
public interface CapabilityProvider<O, X, Y> {
   X getCapability(O object, Y context);
}
