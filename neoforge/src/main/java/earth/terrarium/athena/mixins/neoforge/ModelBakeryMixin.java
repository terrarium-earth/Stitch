package earth.terrarium.athena.mixins.neoforge;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.athena.api.client.models.neoforge.FactoryManagerImpl;
import net.minecraft.client.renderer.block.model.UnbakedBlockStateModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @WrapOperation(
            method = "method_65737",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/block/model/UnbakedBlockStateModel;bake(Lnet/minecraft/client/resources/model/ModelBaker;)Lnet/minecraft/client/resources/model/BakedModel;"
            )
    )
    private BakedModel stitch$loadModel(UnbakedBlockStateModel instance, ModelBaker baker, Operation<BakedModel> original, @Local(argsOnly = true) ModelResourceLocation id) {
        for (ResourceLocation type : FactoryManagerImpl.getTypes()) {
            UnbakedBlockStateModel model = FactoryManagerImpl.get(type).loadModel(id);
            if (model != null) {
                instance = model;
                break;
            }
        }
        return original.call(instance, baker);
    }
}
