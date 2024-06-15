package earth.terrarium.athena.mixins.neoforge;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.athena.api.client.models.neoforge.FactoryManagerImpl;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @WrapOperation(
            method = "method_61072",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/model/ModelBakery$ModelBakerImpl;bakeUncached(Lnet/minecraft/client/resources/model/UnbakedModel;Lnet/minecraft/client/resources/model/ModelState;)Lnet/minecraft/client/resources/model/BakedModel;"
            )
    )
    private BakedModel stitch$loadModel(@Coerce ModelBaker instance, UnbakedModel unbakedModel, ModelState modelState, Operation<BakedModel> original, ModelBakery.TextureGetter spriteGetter, ModelResourceLocation id) {
        for (ResourceLocation type : FactoryManagerImpl.getTypes()) {
            UnbakedModel model = FactoryManagerImpl.get(type).loadModel(id);
            if (model != null) {
                unbakedModel = model;
                break;
            }
        }
        return original.call(instance, unbakedModel, modelState);
    }
}
