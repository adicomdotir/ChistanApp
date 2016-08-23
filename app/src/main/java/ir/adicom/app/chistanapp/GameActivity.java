package ir.adicom.app.chistanapp;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    String[] questions = {
            "یک زن و شوهر 7 پسردارند و هر پسر یک خواهر این خانواده چند نفره هستند ؟",
            "آن چه چیزی است که در رادیو و دریا مشترک و هر دو آن را دارا می باشند ؟",
            "آن چیست که سر ندارد، کلاه دارد، یک پا دارد و کفش ندارد؟",
            "آن چیست قبای زرد در بر دارد، اندام ظریف چون صنوبر دارد، زرد است، به مشام تلخ است ولی طعمی چون شکر دارد؟",
            "آن چیست که یک چشم و یک پا دارد؟"
    };
    String[] answers = {
            "ده نفر",
            "موج",
            "قارچ",
            "لیمو شیرین",
            "سوزن"
    };

    TextView txtQues, txtMask, txtWrong;
    String mask, currect, wrong;
    int wrongIndex = 0;
    int maxWrong = 0;
    private ArrayList<Button> btnList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtQues = (TextView) findViewById(R.id.txtQuestion);
        txtMask = (TextView) findViewById(R.id.txtMask);
        txtWrong = (TextView) findViewById(R.id.txtWrong);

        reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reset();
    }

    private void reset() {
        Random rn = new Random();
        int rand = rn.nextInt(questions.length);
        txtQues.setText(questions[rand]);
        currect = answers[rand];
        mask = "";
        wrong = "";
        wrongIndex = 0;
        maxWrong = 0;
        for (int i = 0; i < currect.length(); i++) {
            if (currect.charAt(i) != ' ') {
                mask += "-";
                maxWrong++;
            } else {
                mask += " ";
            }
        }
        txtMask.setText(mask);
        txtWrong.setText(wrong);
        for (Button b : btnList) {
            b.setEnabled(true);
        }
    }

    public void onClick(View view) {
        if (maxWrong >= wrongIndex) {
            Button btn = (Button) view;
            char[] ch = mask.toCharArray();
            boolean exist = false;
            for (int i = 0; i < currect.length(); i++) {
                if (currect.charAt(i) == btn.getText().charAt(0)) {
                    ch[i] = btn.getText().charAt(0);
                    exist = true;
                }
            }
            if (exist == false) {
                wrongIndex++;
                if (maxWrong >= wrongIndex) {
                    wrong += btn.getText().charAt(0);
                    txtWrong.setText(wrong);
                }
            }
            mask = String.valueOf(ch);
            txtMask.setText(mask);
            btn.setEnabled(false);
            btnList.add(btn);
        }
        if (maxWrong < wrongIndex) {
            startActivity(new Intent(this, GameOverActivity.class));
        }
        if (currect.equals(mask)) {
            Toast.makeText(getApplicationContext(), "Won", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        reset();
                }
            }, 2000);

        }
    }
}
