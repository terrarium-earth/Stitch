package earth.terrarium.athena.mixins;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;

@Mixin(BlockStateModelLoader.class)
public class BlockStateModelLoaderMixin {

    @Inject(method = "method_65721", at = @At("HEAD"))
    private static void onBlockStatesLoad(Function<?, ?> function, UnbakedModel unbakedModel, Executor executor, Map<?, ?> map, CallbackInfoReturnable<CompletionStage<?>> cir) {
        AthenaResourceLoader.clearBlockstateData();
    }

    @Inject(method = "method_65720", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/BlockModelDefinition;fromJsonElement(Lcom/google/gson/JsonElement;)Lnet/minecraft/client/renderer/block/model/BlockModelDefinition;"))
    private static void onBlockModelLoad(
            Map.Entry<?, ?> entry, Function<?, ?> function, UnbakedModel unbakedModel, CallbackInfoReturnable<BlockStateModelLoader.LoadedModels> cir,
            @Local ResourceLocation id,
            @Local JsonObject jsonObject
    ) {
        AthenaResourceLoader.addBlockstateData(id, jsonObject);
    }

}