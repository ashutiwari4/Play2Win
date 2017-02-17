/**
 * The dialog shown when the game is over
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package ashutosh.bdemo;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverDialog extends Dialog {
    public static final int REVIVE_PRICE = 5;

    /**
     * Name of the SharedPreference that saves the score
     */
    public static final String score_save_name = "score_save";

    /**
     * Key that saves the score
     */
    public static final String best_score_key = "score";

    /**
     * The game that invokes this dialog
     */
    private Game game;

    private TextView tvCurrentScoreVal;
    private TextView tvBestScoreVal;
    private TextView ttlScore;

    public GameOverDialog(Game game) {
        super(game);
        this.game = game;
        this.setContentView(R.layout.gameover);
        this.setCancelable(false);

        tvCurrentScoreVal = (TextView) findViewById(R.id.tv_current_score_value);
        tvBestScoreVal = (TextView) findViewById(R.id.tv_best_score_value);
        ttlScore = (TextView) findViewById(R.id.ttl_score);
    }

    public void init() {
        Button okButton = (Button) findViewById(R.id.b_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCoins();

                if (game.numberOfRevive <= 1) {
                    game.accomplishmentBox.saveLocal(game);
                }

                dismiss();
                game.finish();
            }
        });

        manageScore();
        manageMedals();
    }

    private void manageScore() {
        SharedPreferences saves = game.getSharedPreferences(score_save_name, 0);
        int oldPoints = saves.getInt(best_score_key, 0);
        if (game.accomplishmentBox.points > oldPoints) {
            // Save new highscore
            SharedPreferences.Editor editor = saves.edit();
            editor.putInt(best_score_key, game.accomplishmentBox.points);
            tvBestScoreVal.setTextColor(Color.RED);
            editor.commit();
        }
        tvCurrentScoreVal.setText("" + game.accomplishmentBox.points);
        game.accomplishmentBox.savesTotalPoint(game, game.accomplishmentBox.points);
        //SharedPreferences username = game.getSharedPreferences(AccomplishmentBox.SAVE_NAME, 0);
        //ttlScore.setText(username.getString(AccomplishmentBox.USERNAME, null) != null ? username.getString(AccomplishmentBox.USERNAME, null) + ", " + game.getString(R.string.total_point) + game.accomplishmentBox.getLastTotalPoints(game) : game.getString(R.string.total_point) + game.accomplishmentBox.getLastTotalPoints(game));
        tvBestScoreVal.setText("" + oldPoints);
    }

    private void manageMedals() {
        SharedPreferences medaille_save = game.getSharedPreferences(MainActivity.medaille_save, 0);
        int medaille = medaille_save.getInt(MainActivity.medaille_key, 0);

        SharedPreferences.Editor editor = medaille_save.edit();

        if (game.accomplishmentBox.achievement_gold) {
            ((ImageView) findViewById(R.id.medaille)).setImageBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.gold));
            if (medaille < 3) {
                editor.putInt(MainActivity.medaille_key, 3);
            }
        } else if (game.accomplishmentBox.achievement_silver) {
            ((ImageView) findViewById(R.id.medaille)).setImageBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.silver));
            if (medaille < 2) {
                editor.putInt(MainActivity.medaille_key, 2);
            }
        } else if (game.accomplishmentBox.achievement_bronze) {
            ((ImageView) findViewById(R.id.medaille)).setImageBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.bronce));
            if (medaille < 1) {
                editor.putInt(MainActivity.medaille_key, 1);
            }
        } else {
            ((ImageView) findViewById(R.id.medaille)).setVisibility(View.INVISIBLE);
        }
        editor.commit();
    }

    private void saveCoins() {
        SharedPreferences coin_save = game.getSharedPreferences(Game.coin_save, 0);
        coin_save.getInt(Game.coin_key, 0);
        SharedPreferences.Editor editor = coin_save.edit();
        editor.putInt(Game.coin_key, game.coins);
        editor.commit();
    }

}
