package fos.world.blocks.campaign;

import arc.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;

public class NukeLauncher extends Block {
    public NukeLauncher(String name){
        super(name);
    }

    @Override
    protected TextureRegion[] icons() {
        return teamRegion.found() ? new TextureRegion[]{region, teamRegions[Team.sharded.id]} : new TextureRegion[]{region};
    }

    @SuppressWarnings("unused")
    public class NukeLauncherBuild extends Building {
        Sector chosen;
        @Override
        public void buildConfiguration(Table table) {
            table.button(Icon.effect, Styles.clearTogglei, () -> {
                if (Vars.state.isCampaign()){
                    Vars.ui.planet.showSelect(Vars.state.rules.sector, s -> chosen = s);
                } else {
                    Vars.ui.showInfo("@silo.campaignonly");
                }
                this.deselect();
            }).size(40);

            table.button(Icon.upOpen, Styles.clearTogglei, () -> {
                if (Vars.state.isCampaign() && chosen != null && canConsume() && potentialEfficiency == 1){

                    Vars.ui.showConfirm("@silo.launch-warning", () -> {
                        items.clear();
                        Fx.launchPod.at(this);
                        Time.runTask(60f, () -> {
                            Vars.state.getSector().save.save();
                            chosen.save.load();
                        });
                    });
                } else {
                    if (!Vars.state.isCampaign()){
                        Vars.ui.showInfo("@silo.campaignonly");
                        return;
                    }
                    if (chosen == null){
                        Vars.ui.showInfo("@silo.nosector");
                    }
                }
            }).size(40);
        }

        @Override
        public void draw() {
            super.draw();
            Draw.rect(Core.atlas.find(name), x, y);

            if (canConsume()){
                Drawf.light(x, y, 320f, Pal.accent, 0.6f);
                Draw.rect(Core.atlas.find("launchpod"), x, y);
                float rad = 20 * 0.74f;
                float scl = 2;
                Draw.z(Layer.bullet - 0.0001f);
                Lines.stroke(1.75f, team.color);
                Lines.square(x, y, rad * 1.22f, 45);
                Lines.stroke(3f, team.color);
                Lines.poly(x, y, 6, rad, Time.time / scl);
                Lines.poly(x, y, 6, rad, -Time.time / scl);
            }
            Draw.color(team.color);
            Draw.rect(Core.atlas.find(name + "-team"), x, y);
        }
    }
}
