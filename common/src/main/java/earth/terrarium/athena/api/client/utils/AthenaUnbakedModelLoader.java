package earth.terrarium.athena.api.client.utils;

import com.google.gson.JsonObject;
import earth.terrarium.athena.api.client.models.AthenaBlockModel;
import earth.terrarium.athena.api.client.models.AthenaModelFactory;
import earth.terrarium.athena.api.client.models.NotNullUnbakedModel;
import earth.terrarium.athena.impl.loading.AthenaDataLoader;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class AthenaUnbakedModelLoader {

    private final ResourceLocation id;
    private final AthenaModelFactory factory;
    private final Function<Supplier<AthenaBlockModel>, NotNullUnbakedModel> loader;

    public AthenaUnbakedModelLoader(ResourceLocation id, AthenaModelFactory factory, Function<Supplier<AthenaBlockModel>, NotNullUnbakedModel> loader) {
        this.id = id;
        this.factory = factory;
        this.loader = loader;
    }

    public @Nullable NotNullUnbakedModel loadModel(ModelResourceLocation modelId) {
        if ("inventory".equals(modelId.getVariant())) return null;
        JsonObject json = AthenaDataLoader.getData(this.id, modelId);
        return this.loadModel(json);
    }

    public NotNullUnbakedModel loadModel(JsonObject json) {
        if (json != null) {
            return this.loader.apply(this.factory.create(json));
        }
        return null;
    }
}
