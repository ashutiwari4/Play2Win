/**
 * Rainbow tail for the nyan cat
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

public class Rainbow extends Sprite {
    
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    
    public Rainbow(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.rainbow);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 4);
        this.height = this.bitmap.getHeight()/3;
    }

    @Override
    public void move() {
        changeToNextFrame();
        super.move();
    }
    
    
}
