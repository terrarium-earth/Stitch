package earth.terrarium.athena.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ModelManager.class)
public class ModelManagerMixin {

    // Force load our definitions before loading the vanilla ones
    @WrapMethod(method = "reload")
    private CompletableFuture<Void> wrapReload(
            PreparableReloadListener.PreparationBarrier preparationBarrier,
            ResourceManager resourceManager, Executor executor, Executor executor2,
            Operation<CompletableFuture<Void>> original
    ) {
        return AthenaResourceLoader.INSTANCE.reload(preparationBarrier, resourceManager, executor, executor2)
                .thenCompose(v -> original.call(preparationBarrier, resourceManager, executor, executor2));
    }
}
