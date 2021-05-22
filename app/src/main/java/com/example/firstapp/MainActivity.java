
//Lots of imports

package com.example.firstapp;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = (LinearLayout) findViewById(R.id.log_scroll);

        setContentView(R.layout.activity_main);

        for (int i = 0; i<100; i++) {
            this.add_log("YEEEES  " + i);
        }

    }

    @SuppressLint("SetTextI18n")
    public void add_log(String text){
        View linearLayout =  findViewById(R.id.log_scroll);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.info);

        TextView valueTV = new TextView(this);
        valueTV.setText(text);

        valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

        ((LinearLayout) linearLayout).addView(valueTV);

    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public void change_box(View given_view){
//        setContentView(R.layout.activity_main);
//
//        LinearLayout ll = (LinearLayout) findViewById(R.id.log_scroll);
//        TextView tv1 = new TextView(this);
//        tv1.setText("This is tv1");
//
//        ll.addView(tv1);






//        for (int i = 0; i < 5; i++) {
//            System.out.println(i);
//        }
//        EditText name_given = findViewById(R.id.person_name);
//        String name_text = name_given.getText().toString();
//
//        TextView button = findViewById(R.id.button);
//
        Date datern = new Date();
//        String display = datern.toString();
//
//        String message = "Hello there! How's it going " + name_text + "?\nRight now, the date is " + display;
//
//        ;
//        try{
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            startActivity(intent);
//            button.setText(message);
//        }
//        catch (ActivityNotFoundException e) {
//            button.setText("There seems to be a problem with the picture taking function.");
//        }
//
//
//
//
//
//
//
//
//
//
//
//    }
}