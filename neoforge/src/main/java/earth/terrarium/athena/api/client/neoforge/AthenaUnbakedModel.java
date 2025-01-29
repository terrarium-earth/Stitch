package earth.terrarium.athena.api.client.neoforge;

import earth.terrarium.athena.api.client.models.AthenaBlockModel;
import net.minecraft.client.renderer.block.model.UnbakedBlockStateModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AthenaUnbakedModel implements UnbakedBlockStateModel {

    private final Supplier<AthenaBlockModel> model;

    public AthenaUnbakedModel(Supplier<AthenaBlockModel> model) {
        this.model = model;
    }

    @Override
    public void resolveDependencies(@NotNull Resolver arg) {

    }

    @Override
    public @NotNull BakedModel bake(ModelBaker baker) {
        return new AthenaBakedModel(this.model.get(), baker.sprites()::get);
    }

    @Override
    public @NotNull Object visualEqualityGroup(@NotNull BlockState arg) {
        return this;
    }
}
