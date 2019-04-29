package com.example.textstyle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import org.w3c.dom.Text;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class StylePop extends Activity implements ColorPickerDialogListener {

    static final String[] LIST_MENU = {"우아한", "티몬", "잘난"} ;

    ListView ListView_font;
    TextView TextView_sample;
    Button Button_color;
    ImageButton ImageButton_up;
    ImageButton ImageButton_down;
    TextView TextView_size;
    Button Button_set;
    float textSize = 0;
    Typeface typeface = null;
    Integer resultFont = 0;
    Integer resultColor = 0;
    float resultSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sytle);

        ListView_font = findViewById(R.id.ListView_font);
        TextView_sample = findViewById(R.id.TextView_sample);
        Button_color = findViewById(R.id.Button_color);
        ImageButton_up = findViewById(R.id.ImageButton_up);
        ImageButton_down = findViewById(R.id.ImageButton_down);
        TextView_size = findViewById(R.id.TextView_size);
        Button_set = findViewById(R.id.Button_set);

        TextView_size.setText(String.valueOf((int)TextView_sample.getTextSize()/getResources().getDisplayMetrics().scaledDensity));


        //폰트 설정
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        //ListView listView = findViewById(R.id.ListView_font);
        ListView_font.setAdapter(adapter);

        //폰트 클릭
        ListView_font.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(this.getClass().getName(),"111111111111111111111111111 : " + ((TextView)view).getText());
                String listItem = ((TextView)view).getText().toString();


                if(listItem.equals("우아한"))
                {
                   typeface = ResourcesCompat.getFont(StylePop.this, R.font.wowahan);
                    resultFont = R.font.wowahan;
                }
                else if(listItem.equals("티몬"))
                {
                     typeface = ResourcesCompat.getFont(StylePop.this, R.font.tonmonsori);
                    resultFont = R.font.tonmonsori;
                }
                else if(listItem.equals("잘난"))
                {
                    typeface = ResourcesCompat.getFont(StylePop.this, R.font.jlnan);
                    resultFont = R.font.jlnan;
                }

                TextView_sample.setTypeface(typeface);
            }
        });


        //컬러 팝업
        Button_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(false)
                        .setDialogId(0)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .show(StylePop.this);

            }
        });


        //텍스트 사이즈 업
        ImageButton_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float size = 0;
                textSize = TextView_sample.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
                size = textSize + 2;
                TextView_sample.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                TextView_size.setText(String.valueOf((int)size));
                resultSize = size;
            }
        });

        //텍스트 사이즈 다운
        ImageButton_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float size = 0;
                textSize = TextView_sample.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
                size = textSize - 2;
                TextView_sample.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                TextView_size.setText(String.valueOf((int)size));
                resultSize = size;
            }
        });
    }



    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();

        intent.putExtra("font", resultFont);
        intent.putExtra("color", resultColor);
        intent.putExtra("size", resultSize);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }


    /**
     * 컬러 선택 리턴
     * @param dialogId
     * @param color
     */
    @Override
    public void onColorSelected(int dialogId, final int color) {

        final int invertColor = ~color;
        final String hexColor = String.format("%X", color);
        String hexInvertColor = String.format("%X", invertColor);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView_sample.setTextColor(color);
                resultColor = color;
                //TextView_sample.setText(hexColor);
            }
        });
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
