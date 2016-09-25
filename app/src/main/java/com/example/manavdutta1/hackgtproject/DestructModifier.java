package com.example.manavdutta1.hackgtproject;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

/**
 * Created by manavdutta1 on 9/25/16.
 */
public class DestructModifier extends MoveYModifier {
    private Sprite spr;
    private MainActivity activity;
    public DestructModifier(final float pDuration, final float pFromY, final float pToY, Sprite sprite, MainActivity activity) {
        this(pDuration, pFromY, pToY, null, EaseLinear.getInstance(), sprite, activity);
    }

    public DestructModifier(final float pDuration, final float pFromY, final float pToY, final IEaseFunction pEaseFunction, Sprite sprit, MainActivity activity) {
        this(pDuration, pFromY, pToY, null, pEaseFunction, sprit, activity);
    }

    public DestructModifier(final float pDuration, final float pFromY, final float pToY, final IEntityModifierListener pEntityModifierListener, MainActivity activity) {
        super(pDuration, pFromY, pToY, pEntityModifierListener, EaseLinear.getInstance());
    }

    public DestructModifier(final float pDuration, final float pFromY, final float pToY, final IEntityModifierListener pEntityModifierListener, final IEaseFunction pEaseFunction, Sprite sprite, MainActivity activity) {
        super(pDuration, pFromY, pToY, pEntityModifierListener, pEaseFunction);
        this.spr = sprite;
        this.activity = activity;
    }

    protected DestructModifier(final MoveYModifier pMoveYModifier) {
        super(pMoveYModifier);
    }

    @Override
    protected void onSetInitialValue(final IEntity pEntity, final float pY) {
        activity.detach(spr);
    }


    @Override
    protected void onSetValue(final IEntity pEntity, final float pPercentageDone, final float pY) {
        activity.detach(spr);
    }

}
