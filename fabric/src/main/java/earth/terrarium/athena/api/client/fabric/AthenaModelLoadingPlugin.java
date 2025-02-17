package earth.terrarium.athena.api.client.fabric;

import earth.terrarium.athena.api.client.models.fabric.FactoryManagerImpl;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

import java.util.Objects;

public class AthenaModelLoadingPlugin implements ModelLoadingPlugin {

    @Override
    public void initialize(Context context) {
        FactoryManagerImpl.LOADERS.forEach((id, loader) ->
                context.modifyBlockModelBeforeBake().register((model, ctx) ->
                        Objects.requireNonNullElse(loader.loadModel(ctx.id()), model)
                )
        );
    }
}
