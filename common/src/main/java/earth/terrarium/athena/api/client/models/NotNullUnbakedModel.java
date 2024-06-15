package earth.terrarium.athena.api.client.models;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface NotNullUnbakedModel extends UnbakedModel {

    @NotNull
    @Override
    BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> function, ModelState modelState);
}
