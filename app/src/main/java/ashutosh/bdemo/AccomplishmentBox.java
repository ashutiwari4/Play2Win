/**
 * Saves achievements and score in shared preferences.
 * You should use a SQLite DB instead, but I'm too lazy to chance it now.
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package ashutosh.bdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;


public class AccomplishmentBox {
    /**
     * Points needed for a gold medal
     */
    public static final int GOLD_POINTS = 100;

    /**
     * Points needed for a silver medal
     */
    public static final int SILVER_POINTS = 50;

    /**
     * Points needed for a bronze medal
     */
    public static final int BRONZE_POINTS = 10;

    public static final String SAVE_NAME = "ACCOMBLISHMENTS";

    public static final String SAVE_TOTAL_POINT = "TOTALPOINT";

    public static final String USERNAME = "USERNAME";

    public static final String CARD_TYPE = "CARDTYPE";

    public static CardType cardType;

    enum CardType {GOLD, SILVER, BRONZE, NONE}


    public static final String ONLINE_STATUS_KEY = "online_status";

    public static final String KEY_POINTS = "points";
    public static final String ACHIEVEMENT_KEY_50_COINS = "achievement_survive_5_minutes";
    public static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    public static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    public static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    public static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    int points;
    boolean achievement_50_coins;
    boolean achievement_toastification;
    boolean achievement_bronze;
    boolean achievement_silver;
    boolean achievement_gold;

    /**
     * Stores the score and achievements locally.
     * <p>
     * The accomblishments will be saved local via SharedPreferences.
     * This makes it very easy to cheat.
     *
     * @param activity activity that is needed for shared preferences
     */
    public void saveLocal(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();

        if (points > saves.getInt(KEY_POINTS, 0)) {
            editor.putInt(KEY_POINTS, points);
        }
        if (achievement_50_coins) {
            editor.putBoolean(ACHIEVEMENT_KEY_50_COINS, true);
        }
        if (achievement_toastification) {
            editor.putBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, true);
        }
        if (achievement_bronze) {
            editor.putBoolean(ACHIEVEMENT_KEY_BRONZE, true);
        }
        if (achievement_silver) {
            editor.putBoolean(ACHIEVEMENT_KEY_SILVER, true);
        }
        if (achievement_gold) {
            editor.putBoolean(ACHIEVEMENT_KEY_GOLD, true);
        }

        editor.commit();
    }

    /**
     * Uploads accomplishments to Google Play Services
     *
     * @param activity
     */
    public void submitScore(Activity activity) {

        //TODO

        AccomplishmentBox.savesAreOnline(activity);

        Toast.makeText(activity.getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
    }

    /**
     * reads the local stored data
     *
     * @param activity activity that is needed for shared preferences
     * @return local stored score and achievements
     */
    public static AccomplishmentBox getLocal(Activity activity) {
        AccomplishmentBox box = new AccomplishmentBox();
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);

        box.points = saves.getInt(KEY_POINTS, 0);
        box.achievement_50_coins = saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false);
        box.achievement_toastification = saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
        box.achievement_bronze = saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
        box.achievement_silver = saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
        box.achievement_gold = saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);

        return box;
    }

    /**
     * marks the data as online
     *
     * @param activity activity that is needed for shared preferences
     */
    public static void savesAreOnline(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, true);
        editor.commit();
    }

    /**
     * marks the data as offline
     *
     * @param activity activity that is needed for shared preferences
     */
    public static void savesAreOffline(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, false);
        editor.commit();
    }


    public static void savecardType(Activity activity, CardType cardType) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putString(CARD_TYPE, cardType.name());
        editor.commit();
    }


    public static CardType getCardType(Activity activity) {
        return CardType.valueOf(activity.getSharedPreferences(SAVE_NAME, 0).getString(CARD_TYPE, "NONE"));
    }


    /**
     * saves total point
     *
     * @param activity
     */
    public static void savesTotalPoint(Activity activity, int points) {
        int ttlPoint = getLastTotalPoints(activity);
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        ttlPoint += points;
        SharedPreferences.Editor editor = saves.edit();
        editor.putInt(SAVE_TOTAL_POINT, ttlPoint);
        editor.apply();
    }

    public static void resetTotalPoint(Activity activity, int points) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putInt(SAVE_TOTAL_POINT, points);
        editor.apply();
    }


    public static int getLastTotalPoints(Activity activity) {
        return activity.getSharedPreferences(SAVE_NAME, 0).getInt(SAVE_TOTAL_POINT, 0);
    }

    public static void saveUsername(Activity activity, String username) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        username = username.substring(0, 1).toUpperCase() + username.substring(1);
        editor.putString(USERNAME, username);
        editor.apply();
    }


    public static String getUsername(Activity activity) {
        return activity.getSharedPreferences(SAVE_NAME, 0).getString(USERNAME, null);
    }

    public static void removeUsername(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.remove(USERNAME);
        editor.commit();
    }


    /**
     * checks if the last data is already uploaded
     *
     * @param activity activity that is needed for shared preferences
     * @return wheater the last data is already uploaded
     */
    public static boolean isOnline(Activity activity) {
        return activity.getSharedPreferences(SAVE_NAME, 0).getBoolean(ONLINE_STATUS_KEY, true);
    }
}