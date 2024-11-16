package com.absolutelyaryan.capabilities;


public class CapabilityData<X, Y> {
    private final Class<X> capabilityClass;
    private final Class<Y> contextClass;
    private final CapabilityProvider provider;

    public CapabilityData(Class<X> capabilityClass, Class<Y> contextClass, CapabilityProvider provider) {
        this.capabilityClass = capabilityClass;
        this.contextClass = contextClass;
        this.provider = provider;
    }

    public Class<X> getCapabilityClass() {
        return capabilityClass;
    }

    public Class<Y> getContextClass() {
        return contextClass;
    }

    public CapabilityProvider getProvider() {
        return provider;
    }
}
