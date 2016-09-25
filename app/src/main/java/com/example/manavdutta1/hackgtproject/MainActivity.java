package com.example.manavdutta1.hackgtproject;

import android.util.DisplayMetrics;
import android.util.Log;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
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

import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.entity.sprite.AnimatedSprite;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener {

    private float firstX;
    private ITextureRegion trumpTowerReg, leftPawReg, rightPawReg, fishboneReg, lightbulbReg;
    private int camWidth, camHeight;
    private Sprite leftPawSprite, rightPawSprite, fishboneSprite, lightbulbSprite;
    private Scene theScene;
    private BitmapTextureAtlas texCat, rightCat;
    private TiledTextureRegion regCat, rightRegCat;
    private Sprite[] fishboneSprites;
    private Sprite[] lightbulbSprites;
    @Override
    public EngineOptions onCreateEngineOptions() {
           DisplayMetrics metrics = new DisplayMetrics();
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
                        int random = new Random().nextInt(2);
                        //below method call for new sprite
                        addTarget(xPos, yPos, random);
                        xPos = MathUtils.random(30.0f,
                                (400 - 30.0f));
                        yPos = 10.0f;
                        random = new Random().nextInt(2);
                        addTarget(xPos, yPos, random);
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
        }
       else {
            sprite = new Sprite(xPos, yPos, this.lightbulbReg, getVertexBufferObjectManager());
        }
        theScene.attachChild(sprite);
        theScene.registerTouchArea(sprite);
        float startY = sprite.getY();
        MoveYModifier mody = new MoveYModifier(2,
                startY, startY + 1000);
        sprite.registerEntityModifier(mody);
    }

        @Override
    protected void onCreateResources() {
        try {
            ITexture background = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("woodbackground.png");
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

            texCat = new BitmapTextureAtlas(this.getTextureManager(), 136, 85, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            regCat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texCat, this.getAssets(),"leftPaw.png", 0, 0, 1, 1);
            texCat.load();

            rightCat = new BitmapTextureAtlas(this.getTextureManager(), 136, 85, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            rightRegCat = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(rightCat, this.getAssets(),"rightPaw.png", 0, 0, 1, 1);
            rightCat.load();

            background.load();
            leftPaw.load();
            rightPaw.load();
            fishbone.load();
            lightbulb.load();

            this.trumpTowerReg = TextureRegionFactory.extractFromTexture(background);
            this.leftPawReg = TextureRegionFactory.extractFromTexture(leftPaw);
            this.rightPawReg = TextureRegionFactory.extractFromTexture(rightPaw);
            this.fishboneReg = TextureRegionFactory.extractFromTexture(fishbone);
            this.lightbulbReg = TextureRegionFactory.extractFromTexture(lightbulb);
        }
        catch (IOException e) {
            Log.e("Error", e.getMessage());
        }
    }

    public boolean onSceneTouchEvent(Scene pScene,  final TouchEvent pEvent) {
        if (pEvent.isActionDown()) {
           if (pEvent.getX() <= 200) {
               this.leftPawSprite = new Sprite(pEvent.getX(),pEvent.getY(), this.leftPawReg, getVertexBufferObjectManager());
               this.theScene.attachChild(this.leftPawSprite);
               Timer timer = new Timer();
               timer.schedule(new TheTask("left"), 400);
           }
            else {
               this.rightPawSprite = new Sprite(pEvent.getX(),pEvent.getY(), this.rightPawReg, getVertexBufferObjectManager());
               this.theScene.attachChild(this.rightPawSprite);
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
        int halfWidth = (int)((1.0/2.0)*camWidth);
        int wholeHeight = camHeight;
        Sprite backgroundSprite = new Sprite(0, 0, this.trumpTowerReg, getVertexBufferObjectManager());
        //int fishX = new Random().nextInt(360)+30;
        //int lightX = new Random().nextInt(360)+30;
        //fishboneSprites = new Sprite[2];
        //lightbulbSprites = new Sprite[2];

        //fishboneSprites[0] = new Sprite(new Random().nextInt(360)+30,10, this.fishboneReg, getVertexBufferObjectManager());
        //lightbulbSprites[0] = new Sprite(new Random().nextInt(360)+30,10, this.lightbulbReg, getVertexBufferObjectManager());
        //fishboneSprites[1] = new Sprite(new Random().nextInt(360)+30,10, this.fishboneReg, getVertexBufferObjectManager());
        //lightbulbSprites[1] = new Sprite(new Random().nextInt(360)+30,10, this.lightbulbReg, getVertexBufferObjectManager());

        //for (int i = 0; i < 2; i++) {
        //    Sprite fishSprite = fishboneSprites[i];
        //    Sprite lightbulbSprite = lightbulbSprites[i];
        //    float fishboneStartY = fishSprite.getY();
        //    float lightbulbStartY = lightbulbSprite.getY();
        //    MoveYModifier fishModifier = new MoveYModifier(2, fishboneStartY, fishboneStartY + 1000);
        //    MoveYModifier lightbulbModifier = new MoveYModifier(2, lightbulbStartY, lightbulbStartY + 1000);
        //    fishSprite.registerEntityModifier(fishModifier);
        //    lightbulbSprite.registerEntityModifier(lightbulbModifier);
        //    scene.attachChild(lightbulbSprite);
        //    scene.attachChild(fishSprite);
        //    scene.registerTouchArea(fishSprite);
        //    scene.registerTouchArea(lightbulbSprite);
        //}

        scene.setBackground(new Background(new Color(Color.BLACK)));
        scene.attachChild(backgroundSprite);
        scene.registerTouchArea(backgroundSprite);

        scene.setTouchAreaBindingOnActionDownEnabled(true);
        scene.setOnSceneTouchListener(this);
        theScene = scene;
        createSpriteSpawnTimeHandler();
        return scene;
    }
}
