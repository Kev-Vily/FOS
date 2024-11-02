package fos.maps.planet;

import arc.graphics.Color;
import arc.math.geom.Vec3;
import fos.content.*;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.*;

public class CaldemoltStarGenerator extends PlanetGenerator {
    //these are required to override, but have no actual use here.
    @Override
    public float getHeight(Vec3 position) {
        return 0;
    }

    @Override
    public Color getColor(Vec3 position) {
        return null;
    }

    @Override
    public void generate(Tiles tiles) {
        pass((x, y) -> floor = Blocks.slag);

        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                Tile t = tiles.get(500 + x, 500 + y);
                t.setFloor(Blocks.metalFloor.asFloor());
                if (t.x == 500 && t.y == 500) {
                    t.setBlock(FOSBlocks.coreCity, FOSTeams.corru);
                }
            }
        }
    }

    @Override
    public int getSectorSize(Sector sector) {
        return 1000;
    }
}
