package earth.terrarium.athena.neoforge.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import earth.terrarium.athena.api.client.models.neoforge.FactoryManagerImpl;
import earth.terrarium.athena.api.client.utils.AthenaUnbakedModelLoader;
import earth.terrarium.athena.impl.client.DefaultModels;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;
import org.jetbrains.annotations.NotNull;

public class AthenaGeometryLoader implements UnbakedModelLoader<AthenaGeometryLoader.Unbaked> {

    @Override
    public @NotNull Unbaked read(@NotNull JsonObject json, @NotNull JsonDeserializationContext context) throws JsonParseException {
        String id = GsonHelper.getAsString(json, DefaultModels.MODID + ":loader");
        ResourceLocation loaderId = ResourceLocation.tryParse(id);
        if (loaderId == null) throw new JsonParseException("Invalid loader id: " + id);
        AthenaUnbakedModelLoader loader = FactoryManagerImpl.get(loaderId);
        if (loader == null) throw new JsonParseException("Unknown loader: " + loaderId);
        return new Unbaked(loader, json);
    }

    public record Unbaked(AthenaUnbakedModelLoader loader, JsonObject json) implements UnbakedModel {

        @Override
        public @NotNull BakedModel bake(@NotNull TextureSlots slots, @NotNull ModelBaker baker, @NotNull ModelState state, boolean bl, boolean bl2, @NotNull ItemTransforms arg4) {
            return loader.loadModel(json).bake(baker);
        }

        @Override
        public void resolveDependencies(@NotNull Resolver arg) {

        }
    }
}