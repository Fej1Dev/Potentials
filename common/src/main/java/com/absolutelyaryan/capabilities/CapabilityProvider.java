   package com.absolutelyaryan.capabilities;

   public interface CapabilityProvider<O> {
      <T> T getCapability(O object, Object context);
   }
