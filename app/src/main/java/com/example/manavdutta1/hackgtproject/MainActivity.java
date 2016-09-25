package com.example.manavdutta1.hackgtproject;

import android.content.Intent;
import android.graphics.PointF;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.color.Color;
import org.andengine.util.math.MathUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.sprite.AnimatedSprite;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private float firstX;
    private PhysicsWorld world;
    private ITextureRegion trumpTowerReg, leftPawReg, rightPawReg, fishboneReg, lightbulbReg;
    private ITextureRegion canReg, eggReg, pizzaReg, hotDogReg;
    private int camWidth, camHeight;
    private Sprite leftPawSprite, rightPawSprite;
    private Scene theScene;
    private SoundPool sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    //private BitmapTextureAtlas texCat, rightCat;
    //private TiledTextureRegion regCat, rightRegCat;
    private List<Sprite> fishboneSprites = new ArrayList<Sprite>();
    private List<Sprite> lightbulbSprites = new ArrayList<Sprite>();
    private List<Sprite> canSprites = new ArrayList<Sprite>();
    private List<Sprite> eggSprites = new ArrayList<Sprite>();
    private List<Sprite> pizzaSprites = new ArrayList<Sprite>();
    private List<Sprite> hotDogSprites = new ArrayList<Sprite>();
    private int backgroundId;
    private int garbageId;
    private int foodId1;
    private int foodId2;
    private int lossId;
    private int score;
    private int strike;
    @Override
    public EngineOptions onCreateEngineOptions() {
        // Background music
        backgroundId = sp.load(this, R.raw.background_game_music, 3);
        // Garbage
        garbageId = sp.load(this, R.raw.sad_cat_meow, 1);
        // Food
        foodId1 = sp.load(this, R.raw.food_get_meow, 1);
        foodId2 = sp.load(this, R.raw.food_get_meow2, 1);
        // Loss
        lossId = sp.load(this, R.raw.cat_lost_hiss, 2);
        DisplayMetrics metrics = new DisplayMetrics();
        fishboneSprites = new ArrayList<>();
        lightbulbSprites = new ArrayList<>();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        camWidth = metrics.widthPixels;
        camHeight = metrics.heightPixels;
        final Camera camera = new Camera(0, 0, 400, 600);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR,
            new RatioResolutionPolicy(400, 600), camera);
    }

    private void createSpriteSpawnTimeHandler() {
        TimerHandler spriteTimerHandler;
        float mEffectSpawnDelay = 2f;

        spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        //position for random sprite
                        float xPos = MathUtils.random(30.0f,
                                (400 - 30.0f));
                        float yPos = 10.0f;
                        int random = new Random().nextInt(3);
                        //below method call for new sprite
                        addTarget(xPos, yPos, random);
                        xPos = MathUtils.random(30.0f,
                                (400 - 30.0f));
                        yPos = 10.0f;
                        random = new Random().nextInt(3);
                        addGoodTarget(xPos, yPos, random);
                    }

                });
        getEngine().registerUpdateHandler(spriteTimerHandler);
    }
    public void addTarget(float xPos, float yPos, int code) {
        Random rand = new Random();
        Log.d("---Random x----", "Random x" + xPos + "Random y" + yPos);
        Sprite sprite;
        if (code == 0) {
            sprite = new Sprite(xPos, yPos, this.fishboneReg, getVertexBufferObjectManager());
            sprite.setUserData("Fish");
            fishboneSprites.add(sprite);
        }
       else if (code == 1) {
            sprite = new Sprite(xPos, yPos, this.lightbulbReg, getVertexBufferObjectManager());
            sprite.setUserData("LightBulb");
            lightbulbSprites.add(sprite);
        }
        else {
            sprite = new Sprite(xPos, yPos, this.canReg, getVertexBufferObjectManager());
            sprite.setUserData("Can");
            canSprites.add(sprite);
        }
        theScene.attachChild(sprite);
        theScene.registerTouchArea(sprite);
        float startY = sprite.getY();
        MoveYModifier mody = new MoveYModifier(2,
                startY, startY + 1000);
        //DestructModifier modifier = new DestructModifier(2, startY, startY+1000,sprite,this);
        //SequenceEntityModifier modif = new SequenceEntityModifier(mody, modifier);
        sprite.registerEntityModifier(mody);
    }

    public void addGoodTarget(float xPos, float yPos, int code) {
        Random rand = new Random();
        Log.d("---Random x----", "Random x" + xPos + "Random y" + yPos);
        Sprite sprite;
        if (code == 0) {
            sprite = new Sprite(xPos, yPos, this.eggReg, getVertexBufferObjectManager());
            sprite.setUserData("Egg");
            eggSprites.add(sprite);
        }
        else if (code == 1) {
            sprite = new Sprite(xPos, yPos, this.pizzaReg, getVertexBufferObjectManager());
            sprite.setUserData("Pizza");
            pizzaSprites.add(sprite);
        }
        else {
            sprite = new Sprite(xPos, yPos, this.hotDogReg, getVertexBufferObjectManager());
            sprite.setUserData("Hot Dog");
            hotDogSprites.add(sprite);
        }
        theScene.attachChild(sprite);
        theScene.registerTouchArea(sprite);
        float startY = sprite.getY();
        MoveYModifier mody = new MoveYModifier(2,
                startY, startY + 1000);
        //DestructModifier modifier = new DestructModifier(2, startY, startY+1000,sprite,this);
        //SequenceEntityModifier modif = new SequenceEntityModifier(mody, modifier);
        sprite.registerEntityModifier(mody);
    }

    public void detach(Sprite sprite) {
        if (sprite.getUserData().equals("Fish")) {
            sprite.setVisible(false);
            fishboneSprites.remove(sprite);
            theScene.detachChild(sprite);
        }
        else {
            sprite.setVisible(false);
            lightbulbSprites.remove(sprite);
            theScene.detachChild(sprite);
        }
    }
    @Override
    protected void onCreateResources() {
        try {
            final String[] backgrounds = {"woodbackground.png", "space.png", "city.png"};
            ITexture background = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open(backgrounds[new Random().nextInt(3)]);
                }
            });

            //leftPaw for left side
            ITexture leftPaw = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("leftPaw.png");
                }
            });

            //rightPaw for right side
            ITexture rightPaw = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("rightPaw.png");
                }
            });

            //Negative items to grab
            ITexture fishbone = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("fishbone.png");
                }
            });

            ITexture lightbulb = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("lightbulb.png");
                }
            });

            ITexture can = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("can.png");
                }
            });

            ITexture egg = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("egg.png");
                }
            });

            ITexture hotDog = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("hotdog.png");
                }
            });

            ITexture pizza = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("pizza.png");
                }
            });

            background.load();
            leftPaw.load();
            rightPaw.load();
            fishbone.load();
            lightbulb.load();
            can.load();

            egg.load();
            hotDog.load();
            pizza.load();

            this.trumpTowerReg = TextureRegionFactory.extractFromTexture(background);
            this.leftPawReg = TextureRegionFactory.extractFromTexture(leftPaw);
            this.rightPawReg = TextureRegionFactory.extractFromTexture(rightPaw);
            this.fishboneReg = TextureRegionFactory.extractFromTexture(fishbone);
            this.lightbulbReg = TextureRegionFactory.extractFromTexture(lightbulb);
            this.canReg = TextureRegionFactory.extractFromTexture(can);

            this.eggReg = TextureRegionFactory.extractFromTexture(egg);
            this.hotDogReg = TextureRegionFactory.extractFromTexture(hotDog);
            this.pizzaReg = TextureRegionFactory.extractFromTexture(pizza);

        }
        catch (IOException e) {
            Log.e("Error", e.getMessage());
        }
    }
    private void collisionHandling(String param) {
                Sprite paw = null;
                if (param.equals("left")) {
                    paw = this.leftPawSprite;
                }
                else {
                    paw = this.rightPawSprite;
                }
                Sprite theFish = null;
                for (Sprite s : fishboneSprites) {
                    if (paw.collidesWith(s)) {
                        sp.play(garbageId, 1,1,0,0,1);
                        strike++;
                        if (strike >= 3){
                            Intent intent = new Intent(this, GameOverActivity.class);
                            intent.putExtra("EXTRA_SCORE", score);
                            finish();
                            startActivity(intent);
                        }
                       theFish = s;
                       break;
                    }
                }
                if (theFish != null) {
                    fishboneSprites.remove(theFish);
                    theScene.detachChild(theFish);
                    return;
                }
                else {
                    Sprite theBulb = null;
                    for (Sprite s : lightbulbSprites) {
                        if (paw.collidesWith(s)) {
                            sp.play(garbageId, 1,1,0,0,1);
                            strike++;
                            if(strike >= 3) {
                                Intent intent = new Intent(this, GameOverActivity.class);
                                intent.putExtra("EXTRA_SCORE", score);
                                finish();
                                startActivity(intent);
                            }
                            theBulb = s;
                            break;
                        }
                    }
                    if (theBulb != null) {
                        lightbulbSprites.remove(theBulb);
                        theScene.detachChild(theBulb);
                        return;
                    }
                    else {
                        Sprite thePizza = null;
                        for (Sprite s : pizzaSprites) {
                            if (paw.collidesWith(s)) {
                                sp.play(foodId2, 1,1,0,0,1);
                                score+= 10;
                                thePizza = s;
                                break;
                            }
                        }
                        if (thePizza != null) {
                            pizzaSprites.remove(thePizza);
                            theScene.detachChild(thePizza);
                            return;
                        }
//                        else {
//                            Sprite theHotDog = null;
//                            for (Sprite s : hotDogSprites) {
//                                if (paw.collidesWith(s)) {
//                                    sp.play(foodId1, 1,1,0,0,1);
//                                    score+= 10;
//                                    theHotDog = s;
//                                    break;
//                                }
//                            }
//                            if (theHotDog != null) {
//                                hotDogSprites.remove(theHotDog);
//                                theScene.detachChild(theHotDog);
//                                return;
//                            }
//                            else {
//                                Sprite thePizza = null;
//                                for (Sprite s : pizzaSprites) {
//                                    if (paw.collidesWith(s)) {
//                                        sp.play(foodId2, 1,1,0,0,1);
//                                        score+= 10;
//                                        thePizza = s;
//                                        break;
//                                    }
//                                }
//                                if (thePizza != null) {
//                                    pizzaSprites.remove(thePizza);
//                                    theScene.detachChild(thePizza);
//                                    return;
//                                }
//                                else {
//                                    Sprite theEgg = null;
//                                    for (Sprite s : eggSprites) {
//                                        if (paw.collidesWith(s)) {
//                                            sp.play(foodId1, 1,1,0,0,1);
//                                            score+= 10;
//                                            theEgg = s;
//                                            break;
//                                        }
//                                    }
//                                    if (theEgg != null) {
//                                        eggSprites.remove(theEgg);
//                                        theScene.detachChild(theEgg);
//                                        return;
//                                    }
//                                }
//                            }
//                        }
                    }
                }
    }
    public boolean onSceneTouchEvent(Scene pScene,  final TouchEvent pEvent) {
        if (pEvent.isActionDown()) {
           if (pEvent.getX() <= 200) {
               this.leftPawSprite = new Sprite(pEvent.getX(),pEvent.getY(), this.leftPawReg, getVertexBufferObjectManager());
               this.theScene.attachChild(this.leftPawSprite);
               collisionHandling("left");
               Timer timer = new Timer();
               timer.schedule(new TheTask("left"), 400);
           }
            else {
               this.rightPawSprite = new Sprite(pEvent.getX(),pEvent.getY(), this.rightPawReg, getVertexBufferObjectManager());
               this.theScene.attachChild(this.rightPawSprite);
               collisionHandling("right");
               Timer timer = new Timer();
               timer.schedule(new TheTask("right"), 400);
           }
        }
        return true;
    }
    private class TheTask extends TimerTask {
        private String direction;
        public TheTask(String d) {
            this.direction = d;
        }
        @Override
        public void run() {
            if (direction.equals("left")) {
                MainActivity.this.leftPawSprite.setVisible(false);
                MainActivity.this.theScene.detachChild(MainActivity.this.leftPawSprite);
            }
            else {
                MainActivity.this.rightPawSprite.setVisible(false);
                MainActivity.this.theScene.detachChild(MainActivity.this.rightPawSprite);
            }
        }
    }
    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
        score = 0;
        strike = 0;
        int halfWidth = (int)((1.0/2.0)*camWidth);
        int wholeHeight = camHeight;
        Sprite backgroundSprite = new Sprite(0, 0, this.trumpTowerReg, getVertexBufferObjectManager());
        //this.world = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        //this.world.setContactListener(createContactListener());
        //FixtureDef objectDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
        //final Rectangle ground = new Rectangle(0, 600 - 2, 400, 2, this.getVertexBufferObjectManager());
        //PhysicsFactory.createBoxBody(this.world, ground, BodyDef.BodyType.StaticBody, objectDef);
        //ground.setUserData("Ground");

        scene.setBackground(new Background(new Color(Color.BLACK)));
        scene.attachChild(backgroundSprite);
        scene.registerTouchArea(backgroundSprite);
        //scene.attachChild(ground);

        scene.setTouchAreaBindingOnActionDownEnabled(true);
        scene.setOnSceneTouchListener(this);
        theScene = scene;
        createSpriteSpawnTimeHandler();
        sp.play(backgroundId, 1, 1, 0, -1, 1);
        return scene;
    }
}
