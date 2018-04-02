package com.ntnu.henrik.lab3;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private CanvasBox canvas;
    private Handler handler;

    //ball variables
    private int ballRadius = 30;
    private float ballX;
    private float ballY;

    //sensor variables
    private float sensorX;
    private float sensorY;
    private long prevSensorUpdateTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        //sound and haptic feedback
        final Vibrator vibratorFeedback = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        final ToneGenerator soundFeedback = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);

        //sets up sensors
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_FASTEST);

        //Display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //Gets height(Y) and width(X) of the screen (could vary from phones)
        final int screenWidth = size.x;
        final int screenHeigth = size.y;

        //subtracting from the screensize to get new variables
        final int newScreenHeight = screenHeigth - 300;
        final int newScreenWidth =  screenWidth - 100;

        //sets ball height and width
        ballX = screenWidth / 2 - ballRadius;
        ballY = screenHeigth / 2 - ballRadius;

        //initilizes canvas
        canvas = new CanvasBox(MainActivity.this);
        setContentView(canvas);

        handler = new Handler(){

            @Override
            public void handleMessage(Message message){
                canvas.invalidate();
            }

        };

        //figure out where to draw and give feedback
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //Draws along the X-axis, and makes sure not to draw outside the screen
                if (ballX < newScreenWidth && ballX > 100){
                    ballX = ballX + sensorY * 6.0f;
                }
                //ball hits wall and gets moved and returns feedback
                else if (ballX < newScreenWidth){
                    ballX = ballX + 100 -(sensorX * 6.0f);
                    assert vibratorFeedback != null;

                    vibratorFeedback.vibrate(50);
                    soundFeedback.startTone(ToneGenerator.TONE_PROP_BEEP);

                }
                //ball hits wall and gets moved and returns feedback
                else if (ballX > 0){
                    ballX = ballX - 100 -(sensorX * 6.0f);
                    assert vibratorFeedback != null;

                    vibratorFeedback.vibrate(50);
                    soundFeedback.startTone(ToneGenerator.TONE_PROP_BEEP);
                }

                //Draws along the Y-axis, and makes sure not to draw outside the screen
                if (ballY < newScreenHeight && ballY > 100){
                    ballY = ballY + sensorX * 6.0f;
                }
                //ball hits wall and gets moved and returns feedback
                else if (ballY < newScreenHeight){
                    ballY = ballY + 100 -(sensorY * 6.0f);
                    assert vibratorFeedback != null;

                    vibratorFeedback.vibrate(50);
                    soundFeedback.startTone(ToneGenerator.TONE_PROP_BEEP);
                }
                //ball hits wall and gets moved and returns feedback
                else if (ballY > 0){
                    ballY = ballY - 100 -(sensorY * 6.0f);
                    assert vibratorFeedback != null;

                    vibratorFeedback.vibrate(50);
                    soundFeedback.startTone(ToneGenerator.TONE_PROP_BEEP);
                }

                handler.sendEmptyMessage(0);
            }
        },0,100);
    }

    //Update the sensor
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mainSensor = sensorEvent.sensor;

        if (mainSensor.getType() == Sensor.TYPE_ACCELEROMETER){

            long currentTime = System.currentTimeMillis();

            if ((currentTime - prevSensorUpdateTime) > 100){
                prevSensorUpdateTime= currentTime;

                sensorX= sensorEvent.values[0];
                sensorY= sensorEvent.values[1];
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    //Creates/draws into the canvas box
    public class CanvasBox extends View {
        private Paint ball;
        private Paint background;

        public CanvasBox(Context context){
            super(context);
            setFocusable(true);

            ball = new Paint();
            background = new Paint();

        }
        //draws the ball and background
        public void onDraw(Canvas screen){

            //Ball
            ball.setStyle(Paint.Style.FILL);
            ball.setColor(Color.RED);
            ball.setAntiAlias(true);
            ball.setTextSize(30f);

            //Background
            background.setStyle(Paint.Style.FILL);
            background.setColor(Color.RED);
            background.setAntiAlias(true);
            background.setTextSize(30f);

            //background box position
            int canvasW = getWidth();
            int canvasH = getHeight();
            Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
            int backgroundW = canvasW -100;
            int backgroundH = canvasH -100;
            int left = centerOfCanvas.x - (backgroundW / 2);
            int top = centerOfCanvas.y - (backgroundH / 2);
            int right = centerOfCanvas.x + (backgroundW / 2);
            int bottom = centerOfCanvas.y + (backgroundH / 2);
            Rect box = new Rect(left, top, right, bottom);

            //creates -
            //background box
            screen.drawRect(box, background);
            //ball
            screen.drawCircle(ballX,ballY,ballRadius,ball);

        }
    }
}
