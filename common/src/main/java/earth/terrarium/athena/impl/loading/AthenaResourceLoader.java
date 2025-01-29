package earth.terrarium.athena.impl.loading;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import earth.terrarium.athena.impl.client.DefaultModels;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AthenaResourceLoader extends SimpleJsonResourceReloadListener<JsonElement> {

    private static final String KEY = DefaultModels.MODID + ":loader";

    public static final AthenaResourceLoader INSTANCE = new AthenaResourceLoader();

    private final Map<ResourceLocation, JsonObject> blockstateData = new ConcurrentHashMap<>();
    private final Map<ResourceLocation, JsonElement> data = new HashMap<>();

    public AthenaResourceLoader() {
        super(ExtraCodecs.JSON, FileToIdConverter.json("athena"));
    }

    public static void clearBlockstateData() {
        INSTANCE.blockstateData.clear();
    }

    public static void addBlockstateData(ResourceLocation stateId, JsonObject data) {
        if (data == null) return;
        if (!data.has(KEY)) return;
        INSTANCE.blockstateData.put(stateId, data);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.data.clear();
        this.data.putAll(object);
    }

    public static JsonObject getData(ResourceLocation modelType, ResourceLocation modelId) {
        var modelData = INSTANCE.data.get(modelId);
        if (modelData != null) {
            return checkObject(modelType, modelData);
        }
        var blockstateData = INSTANCE.blockstateData.get(modelId);
        if (blockstateData != null) {
            return checkObject(modelType, blockstateData);
        }
        return null;
    }

    private static JsonObject checkObject(ResourceLocation modelType, JsonElement data) {
        if (data instanceof JsonObject object) {
            String type = GsonHelper.getAsString(object, KEY, "");
            if (modelType.toString().equals(type)) {
                return object;
            }
        }
        return null;
    }
}
