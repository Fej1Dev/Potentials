package com.fej1fun.potentials.platform;

import com.fej1fun.potentials.capabilities.types.BlockCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.EntityCapabilityHolder;
import com.fej1fun.potentials.capabilities.types.ItemCapabilityHolder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class HolderHelper {

    @ExpectPlatform
    public static <X,Y> BlockCapabilityHolder<X,Y> createBlock(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
        {throw new NotImplementedException();}

    @ExpectPlatform
    public static <X,Y> EntityCapabilityHolder<X,Y> createEntity(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
        {throw new NotImplementedException();}

    @ExpectPlatform
    public static <X,Y> ItemCapabilityHolder<X,Y> createItem(Class<X> apiClass, Class<Y> contextClass, ResourceLocation identifier)
        {throw new NotImplementedException();}

}
