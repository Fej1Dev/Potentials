package com.absolutelyaryan.capabilities;

@FunctionalInterface
public interface CapabilityProvider<O, X, Y> {
   X getCapability(O object, Y context);
}
