package earth.terrarium.athena.api.client.fabric;

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
    public void resolveDependencies(Resolver resolver) {

    }

    @Override
    public @NotNull BakedModel bake(ModelBaker modelBaker) {
        return new AthenaBakedModel(this.model.get(), modelBaker.sprites()::get);
    }

    @Override
    public @NotNull Object visualEqualityGroup(BlockState blockState) {
        return this;
    }
}
