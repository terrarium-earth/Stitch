package earth.terrarium.athena.api.client.fabric;

import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AthenaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new Listener());
        ModelLoadingPlugin.register(new AthenaModelLoadingPlugin());
    }

    private static class Listener implements IdentifiableResourceReloadListener {
        @Override
        public @NotNull CompletableFuture<Void> reload(
                PreparationBarrier preparationBarrier, ResourceManager resourceManager,
                ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2,
                Executor executor, Executor executor2
        ) {
            return AthenaResourceLoader.INSTANCE.reload(
                    preparationBarrier, resourceManager,
                    profilerFiller, profilerFiller2,
                    executor, executor2
            );
        }

        @Override
        public ResourceLocation getFabricId() {
            return ResourceLocation.fromNamespaceAndPath("athena", "athena");
        }
    }
}
