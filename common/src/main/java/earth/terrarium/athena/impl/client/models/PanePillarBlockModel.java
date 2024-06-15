package earth.terrarium.athena.impl.client.models;

import com.google.gson.JsonObject;
import earth.terrarium.athena.api.client.models.AthenaBlockModel;
import earth.terrarium.athena.api.client.models.AthenaModelFactory;
import earth.terrarium.athena.api.client.models.AthenaQuad;
import earth.terrarium.athena.api.client.utils.AppearanceAndTintGetter;
import earth.terrarium.athena.api.client.utils.AthenaUtils;
import earth.terrarium.athena.api.client.utils.CtmUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PanePillarBlockModel implements AthenaBlockModel {

    public static final AthenaModelFactory FACTORY = new Factory();

    private static final List<AthenaQuad> MIDDLE = List.of(new AthenaQuad(6, 0.4375f, 0.5625f, 1f, 0f, Rotation.NONE, 0.4375f));

    private final Int2ObjectMap<Material> materials;


    public PanePillarBlockModel(Int2ObjectMap<Material> materials) {
        this.materials = materials;
    }

    @Override
    public List<AthenaQuad> getQuads(AppearanceAndTintGetter level, BlockState state, BlockPos pos, Direction direction) {
        if (direction.getAxis().isVertical()) {
            if (level.getBlockState(pos.relative(direction)) == state) {
                return List.of();
            }
            return getTopQuad(state, direction.getAxisDirection());
        }

        final var rightState = AthenaUtils.getFromDir(state, direction.getCounterClockWise());
        final var leftState = AthenaUtils.getFromDir(state, direction.getClockWise());

        final var upBlockState = level.getAppearance(pos.above(), direction, state, pos);
        final var downBlockState = level.getAppearance(pos.below(), direction, state, pos);

        final var upState = upBlockState.is(state.getBlock()) && AthenaUtils.getFromDir(upBlockState, direction.getCounterClockWise()) && AthenaUtils.getFromDir(upBlockState, direction.getClockWise());
        final var belowState = downBlockState.is(state.getBlock()) && AthenaUtils.getFromDir(downBlockState, direction.getCounterClockWise()) && AthenaUtils.getFromDir(downBlockState, direction.getClockWise());

        int texture = upState && belowState ? 2 : upState ? 3 : belowState ? 1 : 4;

        if (leftState && rightState) {
            final float min = AthenaUtils.getFromDir(state, direction) ? 0.4375f : 0.5f;
            return List.of(
                    new AthenaQuad(texture, 0, min, 1f, 0.5f, Rotation.NONE, 0.4375f),
                    new AthenaQuad(texture, 1 - min, 1f, 1f, 0.5f, Rotation.NONE, 0.4375f),
                    new AthenaQuad(texture, 0, min, 0.5f, 0f, Rotation.NONE, 0.4375f),
                    new AthenaQuad(texture, 1 - min, 1f, 0.5f, 0f, Rotation.NONE, 0.4375f)
            );
        } else if (leftState) {
            final float min = AthenaUtils.getFromDir(state, direction) ? 0.5625f : 0.4375f;
            return List.of(new AthenaQuad(0, 0, 1 - min, 1f, 0f, Rotation.NONE, 0.4375f));
        } else if (rightState) {
            final float min = AthenaUtils.getFromDir(state, direction) ? 0.5625f : 0.4375f;
            return List.of(new AthenaQuad(0, min, 1f, 1f, 0f, Rotation.NONE, 0.4375f));
        } else if (level.getBlockState(pos.relative(direction)).getBlock() != state.getBlock() && !AthenaUtils.getFromDir(state, direction)) {
            return MIDDLE;
        }
        return List.of();
    }

    @Override
    public Int2ObjectMap<TextureAtlasSprite> getTextures(Function<Material, TextureAtlasSprite> getter) {
        final var textures = new Int2ObjectArrayMap<TextureAtlasSprite>();
        for (var entry : this.materials.int2ObjectEntrySet()) {
            textures.put(entry.getIntKey(), getter.apply(entry.getValue()));
        }
        return textures;
    }

    private static final AthenaQuad TOP_MIDDLE = new AthenaQuad(5, 0.4375f, 0.5625f, 0.5625f, 0.4375f, Rotation.NONE, 0f, false);
    private static final AthenaQuad NORTH = new AthenaQuad(5, 0.4375f, 0.5625f, 1f, 0.5625f, Rotation.NONE, 0f, false);
    private static final AthenaQuad SOUTH = new AthenaQuad(5, 0.4375f, 0.5625f, 0.4375f, 0f, Rotation.NONE, 0f, false);
    private static final AthenaQuad EAST = new AthenaQuad(5, 0.5625f, 1f, 0.5625f, 0.4375f, Rotation.NONE, 0f, false);
    private static final AthenaQuad WEST = new AthenaQuad(5, 0f, 0.4375f, 0.5625f, 0.4375f, Rotation.NONE, 0f, false);

    private List<AthenaQuad> getTopQuad(BlockState state, Direction.AxisDirection direction) {
        boolean north = AthenaUtils.getFromDir(state, Direction.NORTH);
        boolean south = AthenaUtils.getFromDir(state, Direction.SOUTH);
        boolean east = AthenaUtils.getFromDir(state, Direction.EAST);
        boolean west = AthenaUtils.getFromDir(state, Direction.WEST);
        if (direction == Direction.AxisDirection.NEGATIVE) {
            var tempNorth = north;
            north = south;
            south = tempNorth;
        }


        final List<AthenaQuad> quads = new ArrayList<>();
        quads.add(TOP_MIDDLE);

        if (north) quads.add(NORTH);
        if (south) quads.add(SOUTH);
        if (east) quads.add(EAST);
        if (west) quads.add(WEST);

        return quads;
    }

    private static class Factory implements AthenaModelFactory {

        @Override
        public Supplier<AthenaBlockModel> create(JsonObject json) {
            final var materials = parseMaterials(GsonHelper.getAsJsonObject(json, "ctm_textures"));
            return () -> new PanePillarBlockModel(materials);
        }

        private static Int2ObjectMap<Material> parseMaterials(JsonObject json) {
            Int2ObjectMap<Material> materials = new Int2ObjectArrayMap<>();
            materials.put(0, CtmUtils.blockMat(GsonHelper.getAsString(json, "particle")));
            materials.put(4, CtmUtils.blockMat(GsonHelper.getAsString(json, "self")));

            materials.put(1, CtmUtils.blockMat(GsonHelper.getAsString(json, "top")));
            materials.put(2, CtmUtils.blockMat(GsonHelper.getAsString(json, "center")));
            materials.put(3, CtmUtils.blockMat(GsonHelper.getAsString(json, "bottom")));

            materials.put(5, CtmUtils.blockMat(GsonHelper.getAsString(json, "edge", GsonHelper.getAsString(json, "particle"))));
            materials.put(6, CtmUtils.blockMat(GsonHelper.getAsString(json, "side_edge", GsonHelper.getAsString(json, "particle"))));
            return materials;
        }
    }
}
