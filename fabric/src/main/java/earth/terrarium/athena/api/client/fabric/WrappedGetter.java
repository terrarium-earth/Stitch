package earth.terrarium.athena.api.client.fabric;

import earth.terrarium.athena.api.client.utils.AppearanceAndTintGetter;
import net.fabricmc.fabric.api.blockview.v2.FabricBlockView;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public record WrappedGetter(BlockAndTintGetter getter) implements AppearanceAndTintGetter, FabricBlockView {

    @Override
    public float getShade(Direction direction, boolean bl) {
        return getter.getShade(direction, bl);
    }

    @Override
    public @NotNull LevelLightEngine getLightEngine() {
        return getter.getLightEngine();
    }

    @Override
    public int getBlockTint(BlockPos blockPos, ColorResolver colorResolver) {
        return getter.getBlockTint(blockPos, colorResolver);
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(BlockPos blockPos) {
        return getter.getBlockEntity(blockPos);
    }

    @Override
    public @NotNull BlockState getBlockState(BlockPos blockPos) {
        return getter.getBlockState(blockPos);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockPos blockPos) {
        return getter.getFluidState(blockPos);
    }

    @Override
    public int getHeight() {
        return getter.getHeight();
    }

    @Override
    public int getMinY() {
        return getter.getMinY();
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockPos pos, Direction direction, BlockState fromState, BlockPos fromPos) {
        return state.getAppearance(this, pos, direction, fromState, fromPos);
    }

    @Override
    public BlockState getAppearance(BlockPos pos, Direction direction) {
        BlockState state = getter.getBlockState(pos);
        return state.getAppearance(this, pos, direction, null, null);
    }

    public BlockState getAppearance(BlockPos pos, Direction direction, BlockState fromState, BlockPos fromPos) {
        return query(pos, direction, fromState, fromPos).appearance();
    }

    @Override
    public Query query(BlockPos pos, Direction direction, BlockState fromState, BlockPos fromPos) {
        BlockState state = getter.getBlockState(pos);
        return new Query(state, state.getAppearance(this, pos, direction, fromState, fromPos));
    }

    @Override
    public @Nullable Object getBlockEntityRenderData(BlockPos pos) {
        return getter instanceof FabricBlockView blockView ? blockView.getBlockEntityRenderData(pos) : null;
    }

    @Override
    public boolean hasBiomes() {
        return getter instanceof FabricBlockView blockView && blockView.hasBiomes();
    }

    @Override
    public @UnknownNullability Holder<Biome> getBiomeFabric(BlockPos pos) {
        return getter instanceof FabricBlockView blockView ? blockView.getBiomeFabric(pos) : null;
    }
}
