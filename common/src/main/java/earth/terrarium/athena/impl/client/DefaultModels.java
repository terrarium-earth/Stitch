package earth.terrarium.athena.impl.client;

import earth.terrarium.athena.api.client.models.FactoryManager;
import earth.terrarium.athena.impl.client.models.*;
import net.minecraft.resources.ResourceLocation;

public class DefaultModels {

    public static final String MODID = "athena";

    private static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    public static void init() {
        FactoryManager.register(id("ctm"), ConnectedBlockModel.FACTORY);
        FactoryManager.register(id("carpet_ctm"), ConnectedCarpetBlockModel.FACTORY);
        FactoryManager.register(id("pane_ctm"), PaneConnectedBlockModel.FACTORY);
        FactoryManager.register(id("giant"), GiantBlockModel.FACTORY);
        FactoryManager.register(id("mural"), GiantBlockModel.FACTORY);
        FactoryManager.register(id("pillar"), PillarBlockModel.FACTORY);
        FactoryManager.register(id("limited_pillar"), LimitedPillarBlockModel.FACTORY);
        FactoryManager.register(id("pane_pillar"), PanePillarBlockModel.FACTORY);
    }
}
