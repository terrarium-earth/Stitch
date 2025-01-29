package earth.terrarium.athena.api.client.utils;

import com.google.gson.JsonObject;
import earth.terrarium.athena.api.client.models.AthenaBlockModel;
import earth.terrarium.athena.api.client.models.AthenaModelFactory;
import earth.terrarium.athena.impl.loading.AthenaResourceLoader;
import net.minecraft.client.renderer.block.model.UnbakedBlockStateModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class AthenaUnbakedModelLoader {

    private final ResourceLocation id;
    private final AthenaModelFactory factory;
    private final Function<Supplier<AthenaBlockModel>, UnbakedBlockStateModel> loader;

    public AthenaUnbakedModelLoader(ResourceLocation id, AthenaModelFactory factory, Function<Supplier<AthenaBlockModel>, UnbakedBlockStateModel> loader) {
        this.id = id;
        this.factory = factory;
        this.loader = loader;
    }

    public @Nullable UnbakedBlockStateModel loadModel(ModelResourceLocation modelId) {
        if (modelId == null || "inventory".equals(modelId.getVariant())) return null;
        JsonObject json = AthenaResourceLoader.getData(this.id, modelId.id());
        return this.loadModel(json);
    }

    public UnbakedBlockStateModel loadModel(JsonObject json) {
        if (json != null) {
            return this.loader.apply(this.factory.create(json));
        }
        return null;
    }
}
