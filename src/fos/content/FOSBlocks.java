package fos.content;

import arc.graphics.*;
import arc.struct.*;
import fos.type.blocks.distribution.*;
import fos.type.blocks.environment.*;
import fos.type.blocks.power.*;
import fos.type.blocks.production.*;
import fos.type.blocks.special.*;
import fos.type.blocks.storage.*;
import fos.type.blocks.units.*;
import fos.type.draw.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import multicraft.*;

import static fos.content.FOSItems.*;
import static mindustry.content.Items.*;
import static mindustry.type.ItemStack.*;

public class FOSBlocks {
    public static Block
    //crafting
    resourceExtractor, cuberiumSynthesizer, sublimer,
    //production
    rockCrusher, drillBase2, tinDrill, oreDetectorSmall, oreDetector,
    //distribution
    spaceDuct, spaceRouter, spaceBridge, itemCatapult, tinBelt,
    //liquids
    gasPipe,
    //power
    windTurbine, heatGenerator, plasmaLauncher, solarPanelMedium,
    //defense
    tinWall, tinWallLarge, silverWall, silverWallLarge, particulator, pulse, thunder,
    //environment & ores
    cyanium, cyaniumWall, crimsonStone, crimsonStoneWall, elithite, elithiteWall, elbium, elbiumWall, nethratium, nethratiumWall,
    annite, anniteWall, blublu, blubluWall, purpur, purpurWall,
    oreTin, oreTinSurface, oreSilver, oreLithium,
    bugSpawn,
    //units
    upgradeCenter, hovercraftFactory,
    //storage
    coreColony, coreFortress, coreCity, coreMetropolis,
    //special
    nukeLauncher, bigBoy, cliffDetonator, orbitalAccelerator;

    public static void load() {
        //region crafting
        resourceExtractor = new MultiCrafter("resource-extractor"){{
            itemCapacity = 15;
            size = 3;
            hasItems = acceptsItems = true;
            configurable = true;
            envRequired = envEnabled = Env.space;
            drawer = new DrawMulti(
                new DrawRegion("-bottom"),
                new DrawDiagonalPistons(){{
                    sides = 8;
                    sinScl = 6f;
                    lenOffset = 7f;
                }},
                new DrawDefault()
            );
            requirements(Category.crafting, with(rawNethratium, 50));
            consumePower(2f);

            resolvedRecipes = Seq.with(
                new Recipe(
                    new IOEntry(
                        Seq.with(with(rawNethratium, 2)),
                        Seq.with()
                    ),
                    new IOEntry(
                        Seq.with(with(aluminium, 1)),
                        Seq.with()
                    ),
                    60f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(with(rawElbium, 4)),
                        Seq.with()
                    ),
                    new IOEntry(
                        Seq.with(with(tin, 1, lithium, 1)),
                        Seq.with()
                    ),
                    90f
                ),
                new Recipe(
                    new IOEntry(
                        Seq.with(with(rawElithite, 6)),
                        Seq.with()
                    ),
                    new IOEntry(
                        Seq.with(with(silver, 1, titanium, 1)),
                        Seq.with()
                    ),
                    120f
                )
            );
        }};
        cuberiumSynthesizer = new GenericCrafter("cuberium-synthesizer"){{
            scaledHealth = 10;
            size = 3;
            hasItems = true;
            hasPower = consumesPower = true;
            envEnabled = envRequired = Env.space;
            consumePower(4f);
            consumeItems(with(tin, 10, silver, 10, titanium, 5));
            consumeLiquid(FOSLiquids.oxygen, 3f/60f);
            outputItems = with(cuberium, 5);
            craftTime = 60f;
            requirements(Category.crafting, with(aluminium, 100, tin, 75, silver, 75, titanium, 100));
        }};
        sublimer = new AttributeCrafter("sublimer"){{
            scaledHealth = 10;
            size = 3;
            rotate = invertFlip = true;
            attribute = Attribute.water;
            baseEfficiency = 0f;
            minEfficiency = 0.1f;
            boostScale = 0.3f;
            consumePower(0.8f);
            outputLiquids = LiquidStack.with(Liquids.hydrogen, 6f/60f, FOSLiquids.oxygen, 3f/60f);
            liquidOutputDirections = new int[]{1, 3};
            craftTime = 10f;
            envEnabled = envRequired = Env.space;
            requirements(Category.crafting, with(aluminium, 150, tin, 100, titanium, 100));
        }};
        //endregion
        //region production
        rockCrusher = new HeatProducerDrill("rock-crusher"){{
            health = 300;
            size = 2;
            tier = 2;
            heatOutput = 4f;
            requirements(Category.production, with(rawNethratium, 30));
            envRequired = envEnabled = Env.space;
        }};
        drillBase2 = new DrillBase("drill-base-2"){{
            health = 120;
            size = 2;
            envEnabled |= Env.space;
            requirements(Category.production, with(tin, 10));
        }};
        tinDrill = new UndergroundDrill("tin-drill"){{
            health = 480;
            size = 2;
            tier = 3;
            envEnabled |= Env.space;
            requirements(Category.production, with(tin, 5));
        }};
        oreDetectorSmall = new OreDetector("ore-detector-small"){{
            health = 480;
            size = 2;
            range = 8*8f;
            envRequired = envEnabled = Env.space;
            requirements(Category.production, with(aluminium, 25, lithium, 30));
            consumePower(0.3f);
        }};
        oreDetector = new OreDetector("ore-detector"){{
            health = 960;
            size = 3;
            requirements(Category.production, with(tin, 50));
            consumePower(0.5f);
        }};
        //endregion
        //region defense
        tinWall = new Wall("tin-wall"){{
            scaledHealth = 400;
            size = 1;
            requirements(Category.defense, with(tin, 6));
        }};
        tinWallLarge = new Wall("tin-wall-large"){{
            scaledHealth = 400;
            size = 2;
            requirements(Category.defense, with(tin, 24));
        }};
        silverWall = new Wall("silver-wall"){{
            scaledHealth = 600;
            size = 1;
            requirements(Category.defense, with(silver, 6));
        }};
        silverWallLarge = new Wall("silver-wall-large"){{
            scaledHealth = 600;
            size = 2;
            requirements(Category.defense, with(silver, 24));
        }};

        particulator = new ItemTurret("particulator"){{
            health = 2400;
            size = 3;
            range = 120;
            targetAir = targetGround = true;
            recoil = 2;
            reload = 40;
            inaccuracy = 5;
            shootSound = Sounds.pew;
            ammo(
                tin, new BasicBulletType(2f, 80){{
                    lifetime = 60f;
                    width = 16f; height = 24f;
                    backColor = Color.valueOf("347043");
                    frontColor = trailColor = lightColor = Color.valueOf("85b374");
                    trailEffect = Fx.artilleryTrail;
                    trailWidth = 16;
                    trailLength = 20;
                    ammoMultiplier = 1;
                    splashDamage = 10f;
                    splashDamageRadius = 24f;
                    knockback = 3.2f;
                    fragOnHit = true;
                    hitEffect = despawnEffect = Fx.explosion;
                    fragBullets = 6;
                    fragBullet = new BasicBulletType(0.8f, 10){{
                        lifetime = 60f * 30; //frags will stay for pretty long
                        drag = 0.024f;
                        width = height = 6f;
                        backColor = Color.valueOf("347043");
                        frontColor = trailColor = Color.valueOf("85b374");
                        trailEffect = Fx.artilleryTrail;
                        trailLength = 8;
                        pierceArmor = true;
                        collidesAir = false;
                        collidesTiles = true;
                        hitEffect = Fx.hitBulletSmall;
                        despawnEffect = Fx.none;
                    }};
                }},
                silver, new BasicBulletType(2f, 120){{
                    lifetime = 60f;
                    width = 16f; height = 24f;
                    backColor = Color.valueOf("813ba1");
                    frontColor = trailColor = lightColor = Color.valueOf("b38bb3");
                    trailEffect = Fx.artilleryTrail;
                    trailWidth = 16;
                    trailLength = 20;
                    ammoMultiplier = 1;
                    splashDamage = 25f;
                    splashDamageRadius = 24f;
                    knockback = 4f;
                    fragOnHit = true;
                    hitEffect = despawnEffect = Fx.explosion;
                    fragBullets = 7;
                    fragBullet = new BasicBulletType(0.8f, 16){{
                        lifetime = 60f * 30; //frags will stay for pretty long
                        drag = 0.024f;
                        width = height = 6f;
                        backColor = Color.valueOf("813ba1");
                        frontColor = trailColor = Color.valueOf("b38bb3");
                        trailEffect = Fx.artilleryTrail;
                        trailLength = 8;
                        pierceArmor = true;
                        collidesAir = false;
                        collidesTiles = true;
                        hitEffect = Fx.hitBulletSmall;
                        despawnEffect = Fx.none;
                    }};
                }}
            );
            requirements(Category.turret, with(tin, 200, silver, 75));
        }};
        pulse = new TractorBeamTurret("pulse"){{
            health = 2400;
            size = 3;
            range = 160;
            targetAir = targetGround = true;
            retargetTime = 60;
            status = StatusEffects.disarmed;
            statusDuration = 60;
            damage = 0;
            force = 0;
            scaledForce = 0;
            consumePower(4);
            requirements(Category.turret, with(copper, 5));
        }};
        /*TODO currently crashes the game
        thunder = new LaserTurret("thunder"){{
            health = 3200;
            size = 5;
            shake = 4;
            recoil = 5;
            range = 190;
            reload = 50;
            shootCone = 5;
            firingMoveFract = 0.5f;
            shootDuration = 220;
            shootType = FOSBullets.thunderLaser;
            shootEffect = Fx.shootBigSmoke2;
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            requirements(Category.turret, with(Items.silicon, 5));
        }};*/
        //endregion
        //region distribution
        spaceDuct = new SpaceDuct("space-duct"){{
            health = 10;
            size = 1;
            requirements(Category.distribution, with(rawNethratium, 1));
            envRequired = Env.space;
        }};
        spaceRouter = new DuctRouter("space-router"){{
            health = 10;
            size = 1;
            requirements(Category.distribution, with(rawNethratium, 3));
            envRequired = Env.space;
        }};
        spaceBridge = new DuctBridge("space-bridge"){{
            health = 10;
            size = 1;
            requirements(Category.distribution, with(rawNethratium, 5));
            envRequired = Env.space;
        }};
        itemCatapult = new MassDriver("item-catapult"){{
            health = 480;
            size = 2;
            range = 120f * 8;
            bullet = new MassDriverBolt(){{
                speed = 0.2f;
                lifetime = 2400f;
                damage = 1f;
            }};
            consumePower(1f / 6f);
            requirements(Category.distribution, with(aluminium, 120, lithium, 75, silver, 100, titanium, 125));
            envRequired = envEnabled = Env.space;
        }};
        tinBelt = new StackConveyor("tin-belt"){{
            health = 10;
            size = 1;
            speed = 0.2f;
            itemCapacity = 5;
            consumesPower = true;
            conductivePower = true;
            baseEfficiency = 1f;
            consumePower(1f / 60f);
            requirements(Category.distribution, with(tin, 1));
        }};
        //endregion
        //region liquids
        gasPipe = new Conduit("gas-pipe"){{
            health = 5;
            size = 1;
            liquidCapacity = 20f;
            requirements(Category.liquid, with(aluminium, 1));
        }};
        //endregion
        //region power
        windTurbine = new WindTurbine("wind-turbine"){{
            health = 480;
            size = 2;
            powerProduction = 0.8f;
            rotateSpeed = 1.8f;
            requirements(Category.power, with(tin, 40));
        }};
        heatGenerator = new HeatGenerator("heat-generator"){{
            health = 480;
            size = 2;
            heatInput = 14f;
            powerProduction = 3f;
            envEnabled |= Env.space;
            requirements(Category.power, with(rawNethratium, 45));
        }};
        plasmaLauncher = new PlasmaLauncher("plasma-launcher"){{
            health = 1500;
            size = 3;
            envRequired = envEnabled = Env.space;
            requirements(Category.power, with(aluminium, 125, lithium, 90, titanium, 75, cuberium, 50));
        }};
        solarPanelMedium = new SolarGenerator("solar-panel-medium"){{
            size = 2;
            powerProduction = 0.5f;
            requirements(Category.power, with(lithium, 50, silver, 75));
        }};
        //endregion
        //region environment & ores
        cyanium = new Floor("cyanium"){{
            variants = 4;
        }};
        cyaniumWall = new StaticWall("cyanium-wall"){{
            variants = 4;
        }};
        crimsonStone = new Floor("crimson-stone"){};
        crimsonStoneWall = new StaticWall("crimson-stone-wall"){{
            variants = 1;
        }};
        elithite = new Floor("elithite"){{
            itemDrop = rawElithite;
            variants = 4;
        }};
        elithiteWall = new StaticWall("elithite-wall"){};
        elbium = new Floor("elbium"){{
            itemDrop = rawElbium;
            variants = 4;
        }};
        elbiumWall = new StaticWall("elbium-wall"){};
        nethratium = new Floor("nethratium"){{
            itemDrop = rawNethratium;
            variants = 4;
        }};
        nethratiumWall = new StaticWall("nethratium-wall"){};
        annite = new Floor("annite"){{
            variants = 4;
        }};
        anniteWall = new StaticWall("annite-wall"){};
        blublu = new Floor("blublu"){{
            variants = 4;
        }};
        blubluWall = new StaticWall("blublu-wall"){};
        purpur = new Floor("purpur"){{
            variants = 4;
        }};
        purpurWall = new StaticWall("purpur-wall"){};
        oreTin = new UndergroundOreBlock("ore-tin"){{
            drop = tin;
            variants = 3;
        }};
        oreTinSurface = new OreBlock("ore-tin-surface"){{
            itemDrop = tin;
        }};
        oreSilver = new UndergroundOreBlock("ore-silver"){{
            drop = silver;
            variants = 3;
        }};
        oreLithium = new OreBlock("ore-lithium"){{
            itemDrop = lithium;
        }};
        bugSpawn = new BugSpawnBlock("bug-spawn"){{
            solid = false;
            size = 3;
        }};
        //endregion
        //region units
        upgradeCenter = new UpgradeCenter("upgrade-center"){{
            health = 1500;
            size = 3;
            consumePower(3f);
            requirements(Category.units, with(tin, 250, silver, 200));
        }};
        hovercraftFactory = new UnitFactory("hovercraft-factory"){{
            scaledHealth = 120;
            size = 3;
            consumePower(5f);
            requirements(Category.units, with(tin, 100, silver, 75));
            plans.add(
                new UnitPlan(FOSUnits.vulture, 20f * 60, with(tin, 35))
            );
        }};
        //endregion
        //region storage
        coreColony = new LuminaCoreBlock("core-colony"){{
            //no radar
            radarRange = 0f;
            configurable = false;

            health = 1920;
            size = 2;
            breakable = true;
            itemCapacity = 250;
            unitCapModifier = 0;
            requirements(Category.effect, with(tin, 1500));
        }};
        coreFortress = new LuminaCoreBlock("core-fortress"){{
            health = 2800;
            size = 3;
            unitCapModifier = 5;
            itemCapacity = 2500;
            unitType = FOSUnits.temp;
            requirements(Category.effect, with(tin, 2000, silver, 1250));
        }};
        coreCity = new LuminaCoreBlock("core-city"){{
            health = 4600;
            size = 4;
            unitCapModifier = 7;
            itemCapacity = 5000;
            unitType = FOSUnits.temp;
            requirements(Category.effect, with(tin, 2500, silver, 2000 /*TODO more items soon(tm)*/));
        }};
        coreMetropolis = new LuminaCoreBlock("core-metropolis"){{
            health = 8000;
            size = 5;
            unitCapModifier = 10;
            itemCapacity = 8000;
            unitType = FOSUnits.temp;
            requirements(Category.effect, with(tin, 4500, silver, 3500 /*TODO*/));
        }};
        //endregion
        //region special
        nukeLauncher = new NukeLauncher("rocket-silo"){{
            health = 7500;
            size = 5;
            hasItems = true;
            itemCapacity = 500;
            breakable = true;
            solid = true;
            update = true;
            configurable = true;
            consumePower(15);
            consumeItems(with(lead, 500, graphite, 500, silicon, 500, thorium, 500, surgeAlloy, 500));
            requirements(Category.effect, BuildVisibility.campaignOnly, with(lead, 5000, silicon, 5000, titanium, 3500, thorium, 2500, phaseFabric, 1500, surgeAlloy, 1500));
        }};
        bigBoy = new GiantNukeLauncher("big-boy"){{
            health = 20000;
            size = 9;
            hasItems = true;
            itemCapacity = 5000;
            breakable = true;
            solid = true;
            update = true;
            configurable = true;
            buildCostMultiplier = 0.5f;
            consumePower(60);
            consumeItems(with(titanium, 5000, tin, 5000, silver, 5000));
            requirements(Category.effect, with(aluminium, 10000, tin, 10000, silver, 10000, titanium, 10000, cuberium, 10000));
        }};
        cliffDetonator = new CliffExplosive("cliff-detonator"){{
            health = 40;
            size = 1;
            requirements(Category.effect, with(titanium, 75, lithium, 150));
        }};
        orbitalAccelerator = new OrbitalAccelerator("orbital-accelerator"){{
            health = 5000;
            size = 5;
            envEnabled |= Env.space;
            hasPower = true;
            hasLiquids = true;
            liquidCapacity = 300;
            buildCostMultiplier = 0.2f;
            consumePower(10f);
            consumeLiquid(Liquids.hydrogen, 300);
            launching = coreFortress;
            requirements(Category.effect, BuildVisibility.campaignOnly, with(aluminium, 5000, titanium, 3000, lithium, 2500, tin, 2500, silver, 2000, cuberium, 2000));
        }};
    }
}
