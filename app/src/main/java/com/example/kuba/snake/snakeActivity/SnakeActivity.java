package com.example.kuba.snake.snakeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kuba.snake.R;
import com.example.kuba.snake.snakeLogic.SnakeLogic;

import static com.example.kuba.snake.snakeActivity.SnakeActivity.START_STOP;
import static com.example.kuba.snake.snakeActivity.SnakeActivity.logic;

public class SnakeActivity extends AppCompatActivity {

    static int START_STOP = 1;//1 = start, 0 = stop
    Handler handler = new Handler();
    Paint paint = new Paint();
    Runnable r;
    static SnakeLogic logic;
    static int licznik = 0;
    View testView;

    public static void setStartStop(int startStop) {
        START_STOP = startStop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        testView = new myview(this);


        logic = new SnakeLogic(getApplicationContext());
        logic.initSnake();


        r = new Runnable() {
            public void run() {
                if (START_STOP == 1&&!logic.isGameOver()) {
                    logic.updatePosition();
                    handler.postDelayed(this, 350);
                    setContentView(testView);

                    testView.invalidate();

                }else{
                    /*GameOverActivity gameOverActivity = new GameOverActivity();
                    finish();
                    gameOverActivity.setPoint(logic.getPoints());


                    gameOverActivity.points.setText(logic.getPoints() + " PKT");

                    logic.getSnake().length = 0;
                    logic.getSnake().getPosition().clear();
                    logic.initSnake();
                    logic.setGameOver(false);
                    START_STOP = 0;
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                    //intent.putExtra("",logic.getPoints());!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    startActivity(intent);*/
                    Thread.currentThread().interrupt();
                }
            }
        };

        handler.postDelayed(r, 350);

    }


}

class myview extends View {
    private GraphicsTools tools = new GraphicsTools();
    private static int WIDTH = 0;
    private static int HEIGHT = 0;
    private int snakeColor = Color.BLACK;
    private int backgroundColor = Color.WHITE;
    private int fruitColor = Color.RED;
    private int linesColor = Color.BLACK;
    Context context;
    Paint paint = new Paint();
    //SnakeLogic logic;


    public myview(Context context) {
        super(context);
        this.context = context;
        //logic = new SnakeLogic(context);
    }

    public int getSnakeColor() {
        return snakeColor;
    }

    public void setSnakeColor(int snakeColor) {
        this.snakeColor = snakeColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        repaintBackground(canvas);


        WIDTH = canvas.getWidth() / 12;
        HEIGHT = canvas.getHeight() / 20;


        tools.drawLines(canvas, linesColor, paint);
        Log.v("snaketest", "draw lines");
        drawSnake(canvas);

        /*paint.setColor(fruitColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(3 * WIDTH + 2, 2 * HEIGHT + 2, 3 * WIDTH + WIDTH - 1, 2 * HEIGHT + HEIGHT - 1, paint);*/

        if (logic.isAte()) {
            logic.generateFruit();
            logic.setAte(false);
        }

        drawFruit(canvas);

        /*if (logic.isGameOver()) {

            GameOverActivity gameOverActivity = new GameOverActivity();
            gameOverActivity.gameOver(canvas,context);
        }*/
    }

    public void repaintBackground(Canvas canvas) {
        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }

    public void drawSnake(Canvas canvas) {
        int r;
        int c;
        paint.setColor(snakeColor);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < logic.getSnake().length; i++) {
            Log.v("snaketest", "drawing snake");
            r = logic.getSnake().getPosition().get(i).getR();
            c = logic.getSnake().getPosition().get(i).getC();
            canvas.drawRect(c * WIDTH + 2, r * HEIGHT + 2, c * WIDTH + WIDTH - 1, r * HEIGHT + HEIGHT - 1, paint);
        }
    }

    private void drawFruit(Canvas canvas) {
        int r = logic.getFruitPosition().getR();
        int c = logic.getFruitPosition().getC();
        paint.setColor(fruitColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(c * WIDTH + 2, r * HEIGHT + 2, c * WIDTH + WIDTH - 1, r * HEIGHT + HEIGHT - 1, paint);
        Log.v("snaketest", "drawing fruit");
    }


}


