package earth.terrarium.athena.api.client.fabric;

import earth.terrarium.athena.api.client.models.AthenaBlockModel;
import earth.terrarium.athena.api.client.models.NotNullUnbakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AthenaUnbakedModel implements NotNullUnbakedModel {

    private final Supplier<AthenaBlockModel> model;

    public AthenaUnbakedModel(Supplier<AthenaBlockModel> model) {
        this.model = model;
    }

    @Override
    public void resolveDependencies(Resolver resolver) {

    }

    @NotNull
    @Override
    public BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> function, ModelState modelState) {
        return new AthenaBakedModel(this.model.get(), function);
    }
}
