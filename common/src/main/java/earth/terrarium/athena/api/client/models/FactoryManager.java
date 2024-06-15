package earth.terrarium.athena.api.client.models;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;

public class FactoryManager {

    /**
     * Registers a new model factory, which will be used to create models for the given json.
     * @param id The id of the model factory
     * @param factory The factory to use
     */
    @Contract(pure = true)
    @ExpectPlatform
    public static void register(ResourceLocation id, AthenaModelFactory factory) {
        throw new AssertionError();
    }
}
