package test.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import test.TestMain;

@Mod(TestMain.MOD_ID)
public class TestNeoMain {
    public TestNeoMain(IEventBus bus) {
        TestMain.init();
    }

}
