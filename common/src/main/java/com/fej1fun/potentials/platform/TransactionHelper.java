package com.fej1fun.potentials.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;

public class TransactionHelper {

    @ExpectPlatform
    public static boolean isOpen() {
        throw new NotImplementedException();
    }
}
