/**
 * A shopped wodden log
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package ashutosh.bdemo.sprites;

import android.graphics.Bitmap;

import ashutosh.bdemo.Game;
import ashutosh.bdemo.GameView;
import ashutosh.bdemo.R;
import ashutosh.bdemo.Util;

public class WoodLog extends Sprite {

    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;

    public WoodLog(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.log_full);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }
    
    /**
     * Sets the position
     * @param x
     * @param y
     */
    public void init(int x, int y){
        this.x = x;
        this.y = y;
    }
}
