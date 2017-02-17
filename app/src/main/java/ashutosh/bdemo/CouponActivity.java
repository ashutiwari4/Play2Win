package ashutosh.bdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ashutosh on 15/2/17.
 */

public class CouponActivity extends Activity {
    private EditText etCoupon;
    private ImageView ivBadge;
    private TextView tvBadge, tvScore, tvRedeemNow;

    public static final int BRONZE_THRESHOLD = 100;
    public static final int SILVER_THRESHOLD = 300;
    public static final int GOLD_THRESHOLD = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        etCoupon = (EditText) findViewById(R.id.etCoupon);
        ivBadge = (ImageView) findViewById(R.id.iv_badge);
        tvBadge = (TextView) findViewById(R.id.tv_badge);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvRedeemNow = (TextView) findViewById(R.id.tv_redeem_now);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageMedals();
    }

    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.tv_redeem_now:
                showMe();
                break;
            case R.id.btn_play_now:
                if (etCoupon.getText().toString().trim().equals("XYZ") || etCoupon.getText().toString().trim().equals("123")) {
                    startActivity(new Intent(CouponActivity.this, Game.class));
                } else {
                    Toast.makeText(CouponActivity.this, "Please enter right coupon code!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void manageMedals() {
        int points = AccomplishmentBox.getLastTotalPoints(this);
        AccomplishmentBox.CardType cardType = AccomplishmentBox.getCardType(this);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        ivBadge.startAnimation(anim);
        tvBadge.startAnimation(anim);

        if (cardType == AccomplishmentBox.CardType.GOLD) {
            tvRedeemNow.setVisibility(View.VISIBLE);
            ivBadge.setImageResource(R.drawable.gold);
            tvBadge.setText(AccomplishmentBox.getUsername(this) != null ? AccomplishmentBox.getUsername(this) + ", You have Gold badge. Redeem it" : "You have Gold badge. Redeem it");
            tvBadge.setTextColor(getResources().getColor(R.color.gold));
            tvScore.setText(AccomplishmentBox.getUsername(this) + ", Your Score: " + points + ".\nYou can redeem now! Go to nearest shop.");
        } else if (cardType == AccomplishmentBox.CardType.SILVER) {
            tvRedeemNow.setVisibility(View.VISIBLE);
            if (points >= GOLD_THRESHOLD) {
                ivBadge.setImageResource(R.drawable.gold);
                AccomplishmentBox.savecardType(this, AccomplishmentBox.CardType.GOLD);
                AccomplishmentBox.resetTotalPoint(this, (points - GOLD_THRESHOLD));
                points = AccomplishmentBox.getLastTotalPoints(this);
                tvBadge.setTextColor(getResources().getColor(R.color.gold));
                tvBadge.setText(AccomplishmentBox.getUsername(this) != null ? AccomplishmentBox.getUsername(this) + ", You achieved Gold badge. Redeem it!" : "You have Gold badge. Redeem it!");
                tvScore.setText(AccomplishmentBox.getUsername(this) + ", Your Score: " + points + ".\nYou can redeem now! Go to nearest shop.");

            } else {
                tvBadge.setTextColor(getResources().getColor(R.color.silver));
                int localPoint = (GOLD_THRESHOLD - points);
                tvScore.setText(AccomplishmentBox.getUsername(this) + ", Your Score: " + points + ".\nYou need " + (localPoint > -1 ? localPoint : 0) + " points for Gold badge");
                ivBadge.setImageResource(R.drawable.silvers);
                tvBadge.setText(AccomplishmentBox.getUsername(this) != null ? AccomplishmentBox.getUsername(this) + ", You have silver badge. Go for Gold" : "You have silver badge. Go for Gold");

            }
        } else if (cardType == AccomplishmentBox.CardType.BRONZE) {
            tvRedeemNow.setVisibility(View.VISIBLE);
            if (points >= SILVER_THRESHOLD) {
                ivBadge.setImageResource(R.drawable.silvers);
                AccomplishmentBox.savecardType(this, AccomplishmentBox.CardType.SILVER);
                AccomplishmentBox.resetTotalPoint(this, (points - SILVER_THRESHOLD));
                points = AccomplishmentBox.getLastTotalPoints(this);
                tvBadge.setTextColor(getResources().getColor(R.color.silver));

                tvBadge.setText(AccomplishmentBox.getUsername(this) != null ? AccomplishmentBox.getUsername(this) + ", You achieved silver badge. Go for Gold!" : "You have silver badge. Go for Gold!");
                int localPoint = (GOLD_THRESHOLD - points);
                tvScore.setText(AccomplishmentBox.getUsername(this) + ", Your Score: " + points + ".\nYou need " + (localPoint > -1 ? localPoint : 0) + " points for Gold badge");
            } else {
                tvBadge.setTextColor(getResources().getColor(R.color.bronze));
                int localPoint = (SILVER_THRESHOLD - points);
                tvScore.setText(AccomplishmentBox.getUsername(this) + ", Your Score: " + points + ".\nYou need " + (localPoint > -1 ? localPoint : 0) + " points for silver badge");
                ivBadge.setImageResource(R.drawable.bronze);
                tvBadge.setText(AccomplishmentBox.getUsername(this) != null ? AccomplishmentBox.getUsername(this) + ", You have bronze badge. Go for Silver" : "You have bronze badge. Go for Silver");

            }
        } else if (cardType == AccomplishmentBox.CardType.NONE) {
            if (points >= BRONZE_THRESHOLD) {
                tvRedeemNow.setVisibility(View.VISIBLE);
                ivBadge.setImageResource(R.drawable.bronze);
                AccomplishmentBox.savecardType(this, AccomplishmentBox.CardType.BRONZE);
                AccomplishmentBox.resetTotalPoint(this, (points - BRONZE_THRESHOLD));
                points = AccomplishmentBox.getLastTotalPoints(this);
                tvBadge.setTextColor(getResources().getColor(R.color.bronze));
                tvBadge.setText(AccomplishmentBox.getUsername(this) != null ? AccomplishmentBox.getUsername(this) + ", You achieved bronze badge. Go for Silver" : "You have bronze badge. Go for Silver");
                int localPoint = (SILVER_THRESHOLD - points);
                tvScore.setText("Your Score: " + points + ".\nYou need " + (localPoint > -1 ? localPoint : 0) + " points for silver badge");
            } else {
                ivBadge.setImageResource(R.drawable.no_badge);
                tvRedeemNow.setVisibility(View.GONE);
                tvBadge.setTextColor(getResources().getColor(R.color.gray));
                tvBadge.setText(AccomplishmentBox.getUsername(this) + ", You have achieved no badge yet!");
                int localPoint = (BRONZE_THRESHOLD - points);
                tvScore.setText(AccomplishmentBox.getUsername(this) + ", Your Score: " + points + ".\nYou need " + (localPoint > -1 ? localPoint : 0) + " points for Bronze badge");
            }
        } else {
            tvRedeemNow.setVisibility(View.GONE);
            ivBadge.setVisibility(View.INVISIBLE);
            tvBadge.setVisibility(View.INVISIBLE);
            tvScore.setVisibility(View.INVISIBLE);
        }
    }


    public ArrayAdapter<String> prepairRedeemOptions() {

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CouponActivity.this, android.R.layout.select_dialog_singlechoice);

        AccomplishmentBox.CardType cardType = AccomplishmentBox.getCardType(this);
        if (cardType == AccomplishmentBox.CardType.GOLD) {
            arrayAdapter.add(AccomplishmentBox.CardType.GOLD.name());
        } else if (cardType == AccomplishmentBox.CardType.SILVER) {
            arrayAdapter.add(AccomplishmentBox.CardType.SILVER.name());
        } else if (cardType == AccomplishmentBox.CardType.BRONZE) {
            arrayAdapter.add(AccomplishmentBox.CardType.BRONZE.name());
        }
        return arrayAdapter;

    }

    public void showMe() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(CouponActivity.this);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Select your options");


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final ArrayAdapter arrayAdapter = prepairRedeemOptions();

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                redeemMyBadge(AccomplishmentBox.CardType.valueOf(arrayAdapter.getItem(which).toString()));
                AlertDialog.Builder builderInner = new AlertDialog.Builder(CouponActivity.this);
                builderInner.setTitle("Congratulations!");
                builderInner.setMessage("Your redeemed your " + arrayAdapter.getItem(which).toString().toLowerCase() + " badge!");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        manageMedals();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }


    public void redeemMyBadge(AccomplishmentBox.CardType cardType) {
        AccomplishmentBox.resetTotalPoint(this, 0);
        if (cardType == AccomplishmentBox.CardType.GOLD) {
            AccomplishmentBox.savecardType(this, AccomplishmentBox.CardType.SILVER);
        } else if (cardType == AccomplishmentBox.CardType.SILVER) {
            AccomplishmentBox.savecardType(this, AccomplishmentBox.CardType.BRONZE);
        } else if (cardType == AccomplishmentBox.CardType.BRONZE) {
            AccomplishmentBox.savecardType(this, AccomplishmentBox.CardType.NONE);
        }
    }
}
