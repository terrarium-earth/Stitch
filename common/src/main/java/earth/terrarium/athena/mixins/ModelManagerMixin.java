package earth.terrarium.athena.mixins;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

@Mixin(ModelManager.class)
public class ModelManagerMixin {

    @Inject(method = "loadBlockStates", at = @At("HEAD"))
    private static void onBlockStatesLoad(BlockStateModelLoader blockStateModelLoader, ResourceManager resourceManager, Executor executor, CallbackInfoReturnable<CompletableFuture<BlockStateModelLoader.LoadedModels>> cir) {
        AthenaResourceLoader.clearBlockstateData();
    }

    @Inject(method = "method_62660", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockModelDefinition;fromJsonElement(Lcom/google/gson/JsonElement;)Lnet/minecraft/client/renderer/block/model/BlockModelDefinition;"))
    private static void onBlockModelLoad(
            Map.Entry<ResourceLocation, List<Resource>> entry,
            Function<ResourceLocation, StateDefinition<Block, BlockState>> function,
            BlockStateModelLoader blockStateModelLoader,
            CallbackInfoReturnable<BlockStateModelLoader.LoadedModels> cir,
            @Local JsonObject json
    ) {
        AthenaResourceLoader.addBlockstateData(entry.getKey(), json);
    }

}