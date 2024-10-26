package earth.terrarium.athena.neoforge.client;

import earth.terrarium.athena.impl.client.DefaultModels;
import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@Mod(value = "athena", dist = Dist.CLIENT)
public class AthenaNeoForgeClient {

    public AthenaNeoForgeClient(IEventBus bus) {
        DefaultModels.init();
        bus.addListener(AthenaNeoForgeClient::onRegisterGeometryLoaders);
        bus.addListener(AthenaNeoForgeClient::registerReloadListener);
    }

    public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(ResourceLocation.fromNamespaceAndPath("athena", "athena"), new AthenaGeometryLoader());
    }

    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(AthenaResourceLoader.INSTANCE);
    }
}