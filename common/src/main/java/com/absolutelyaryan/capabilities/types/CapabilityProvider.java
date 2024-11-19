package com.absolutelyaryan.capabilities.types;

@FunctionalInterface
public interface CapabilityProvider<O, X, Y> {
   X getCapability(O object, Y context);
}
