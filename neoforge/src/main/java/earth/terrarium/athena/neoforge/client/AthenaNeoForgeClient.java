package earth.terrarium.athena.neoforge.client;

import earth.terrarium.athena.impl.client.DefaultModels;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;

@Mod(value = "athena", dist = Dist.CLIENT)
public class AthenaNeoForgeClient {

    public AthenaNeoForgeClient(IEventBus bus) {
        DefaultModels.init();
        bus.addListener(AthenaNeoForgeClient::onRegisterGeometryLoaders);
    }

    public static void onRegisterGeometryLoaders(ModelEvent.RegisterLoaders event) {
        event.register(ResourceLocation.fromNamespaceAndPath("athena", "athena"), new AthenaGeometryLoader());
    }
}