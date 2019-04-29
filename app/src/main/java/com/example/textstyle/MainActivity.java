package com.example.textstyle;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    TextView textView;
    float oldXvalue;
    float oldYvalue;
    Button Button_sytle;
    EditText EditText_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText_main = findViewById(R.id.EditText_main);
        EditText_main.setOnTouchListener(MainActivity.this);

        Button_sytle = findViewById(R.id.Button_sytle);



        //그리기앱
        Button_sytle.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Typeface typeface = ResourcesCompat.getFont(MainActivity.this, R.font.wowahan);
                //textView.setTypeface(typeface);
                Intent intent = new Intent(MainActivity.this, StylePop.class);
                startActivityForResult(intent, 1);


            }
        });
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int width = ((ViewGroup) v.getParent()).getWidth() - v.getWidth();
        int height = ((ViewGroup) v.getParent()).getHeight() - v.getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldXvalue = event.getX();
            oldYvalue = event.getY();
            //  Log.i("Tag1", "Action Down X" + event.getX() + "," + event.getY());

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            v.setX(event.getRawX() - oldXvalue);
            v.setY(event.getRawY() - (oldYvalue + v.getHeight()));
            //  Log.i("Tag2", "Action Down " + me.getRawX() + "," + me.getRawY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (v.getX() > width && v.getY() > height) {
                v.setX(width);
                v.setY(height);
            } else if (v.getX() < 0 && v.getY() > height) {
                v.setX(0);
                v.setY(height);
            } else if (v.getX() > width && v.getY() < 0) {
                v.setX(width);
                v.setY(0);
            } else if (v.getX() < 0 && v.getY() < 0) {
                v.setX(0);
                v.setY(0);
            } else if (v.getX() < 0 || v.getX() > width) {
                if (v.getX() < 0) {
                    v.setX(0);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                } else {
                    v.setX(width);
                    v.setY(event.getRawY() - oldYvalue - v.getHeight());
                }
            } else if (v.getY() < 0 || v.getY() > height) {
                if (v.getY() < 0) {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(0);
                } else {
                    v.setX(event.getRawX() - oldXvalue);
                    v.setY(height);
                }
            }


        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                Integer color=0;
                float size = 0;
                Integer font = 0;


                font = data.getIntExtra("font", font);
                color = data.getIntExtra("color", color);
                size = data.getFloatExtra("size", size);

                Typeface typeface =  ResourcesCompat.getFont(MainActivity.this, font);
                EditText_main.setTypeface(typeface);
                EditText_main.setTextColor(color);
                EditText_main.setTextSize(size);

                //txtResult.setText(result);
            }
        }
    }

}
