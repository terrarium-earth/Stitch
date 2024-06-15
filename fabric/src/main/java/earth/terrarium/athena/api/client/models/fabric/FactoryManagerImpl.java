package earth.terrarium.athena.api.client.models.fabric;

import earth.terrarium.athena.api.client.fabric.AthenaUnbakedModel;
import earth.terrarium.athena.api.client.models.AthenaModelFactory;
import earth.terrarium.athena.api.client.utils.AthenaUnbakedModelLoader;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class FactoryManagerImpl {

    public static final Map<ResourceLocation, AthenaUnbakedModelLoader> LOADERS = new HashMap<>();

    public static void register(ResourceLocation type, AthenaModelFactory factory) {
        LOADERS.put(type, new AthenaUnbakedModelLoader(type, factory, AthenaUnbakedModel::new));
    }
}
