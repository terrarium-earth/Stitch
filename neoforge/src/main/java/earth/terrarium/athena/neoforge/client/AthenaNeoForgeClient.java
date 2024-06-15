package earth.terrarium.athena.neoforge.client;

import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class AthenaNeoForgeClient {

    public static void init(IEventBus mobEventBus) {
        mobEventBus.addListener(AthenaNeoForgeClient::onRegisterGeometryLoaders);
        mobEventBus.addListener(AthenaNeoForgeClient::registerReloadListener);
    }

    public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register(ResourceLocation.fromNamespaceAndPath("athena", "athena"), new AthenaGeometryLoader());
    }

    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(AthenaResourceLoader.INSTANCE);
    }
}