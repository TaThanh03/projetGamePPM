package com.example.my_test;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Bitmap player;
    private Bitmap car1;
    private Bitmap car2;
    private int score;
    private int playerX = 0;
    private int playerY = 0;
    private int minPlayerY, maxPlayerY, minPlayerX, maxPlayerX;
    private int car1X, car1Y, car1speed;
    private int car2X, car2Y, car2speed;



    private int canvasWidth, canvasHeight;

    private boolean touch = false;

    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public GameView(Context context) {
        super(context);
        Bitmap fullSize_player = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
        player = Bitmap.createScaledBitmap(fullSize_player, screenWidth/10, screenHeight/5, true);

        Bitmap fullSize_car1 = BitmapFactory.decodeResource(getResources(), R.drawable.paris_driver);
        car1 = Bitmap.createScaledBitmap(fullSize_car1, screenWidth/5, screenHeight/5, true);

        Bitmap fullSize_car2 = BitmapFactory.decodeResource(getResources(), R.drawable.paris_driver2);
        car2 = Bitmap.createScaledBitmap(fullSize_car2, screenWidth/5, screenHeight/5, true);


        Bitmap fullSize_background = BitmapFactory.decodeResource(getResources(), R.drawable.sydney_road);
        backgroundImage = Bitmap.createScaledBitmap(fullSize_background, screenWidth, screenHeight, true);

        scorePaint.setColor(Color.YELLOW);
        scorePaint.setTextSize(screenHeight/20);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        Bitmap fullSize_life0 = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[0] = Bitmap.createScaledBitmap(fullSize_life0, screenWidth/30, screenHeight/20, true);
        Bitmap fullSize_life1 = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        life[1] = Bitmap.createScaledBitmap(fullSize_life1, screenWidth/30, screenHeight/20, true);

        minPlayerY = 0;
        maxPlayerY = (int)(screenHeight - player.getHeight()*1.3);

        minPlayerX = 0;
        maxPlayerX = screenWidth - player.getWidth();

        car1speed = 2;
        car2speed = 2;
        score = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        if(playerY > maxPlayerY){
            playerY = maxPlayerY;
        }
        if(playerY < minPlayerY){
            playerY = minPlayerY;
        }
        if(playerX > maxPlayerX){
            playerX = maxPlayerX;
        }
        if(playerX < minPlayerX){
            playerX = minPlayerX;
        }

        if(hitCarChecker((int)(car1X + car1.getWidth()), (int)((car1Y) + car1.getHeight()*0.90))) {
            score = score + 1;
            car1X = 99999;
        }
        if(hitCarChecker((car2X), (int)(car2Y + car2.getHeight()*0.90))) {
            score = score + 1;
            car2X = -100;
        }


        canvas.drawBitmap(backgroundImage, 0, 0, null);
        canvas.drawText("Score : " + score, 10, 20, scorePaint);
        canvas.drawBitmap(life[0], 10, 30, null);
        canvas.drawBitmap(life[0], 40, 30, null);
        canvas.drawBitmap(life[0], 70, 30, null);

        if(touch) {
            canvas.drawBitmap(player, playerX, playerY,null);
            touch = false;
        } else {
            canvas.drawBitmap(player, playerX, playerY,null);
        }

        car1X = car1X + car1speed;
        if (car1X > canvasWidth){
            car1X = 0 - 20;
            car1Y = (int) Math.floor(Math.random()* (maxPlayerY - minPlayerY)) + minPlayerY;
        }
        canvas.drawBitmap(car1, car1X, car1Y, null);

        car2X = car2X - car2speed;
        if (car2X < 0){
            car2X = canvasWidth + 20;
            car2Y = (int) Math.floor(Math.random()* (maxPlayerY - minPlayerY)) + minPlayerY;
        }
        canvas.drawBitmap(car2, car2X, car2Y, null);


    }


    public boolean hitCarChecker(int x, int y){
        if (playerX < x && x < (playerX + player.getWidth()) && playerY < y && y < (playerY + player.getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            playerX = (int)(event.getRawX() - player.getWidth()/2);
            playerY = (int)(event.getRawY() - player.getHeight()*0.90);
        }
        return true;
    }
}
