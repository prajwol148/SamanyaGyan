package ssjprajwol.com;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static ssjprajwol.com.Sets.category_id;

public class Questions extends AppCompatActivity implements View.OnClickListener {

    private TextView question, question_counter, timer;
    private Button option1, option2, option3, option4;
    private List <QuestionModel> QuestionModelList;
    private int questionNumber, score;
    private CountDownTimer count;
    private int setNo;
    private FirebaseFirestore firestore;
    private Dialog loading;
    private SoundEffect soundEffect;

    int FLAG = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = findViewById(R.id.question);
        question_counter= findViewById(R.id.question_counter);
        timer =findViewById(R.id.timer);


        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        loading = new Dialog(Questions.this);
        loading.setContentView(R.layout.loading_bar);
        loading.setCancelable(false);
        loading.getWindow().setBackgroundDrawableResource(R.drawable.background_progressbar);
        loading.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loading.show();


        setNo = getIntent().getIntExtra("SETNO",1);
        firestore = FirebaseFirestore.getInstance();
        soundEffect = new SoundEffect(this);

        FLAG = 3;
        soundEffect.setSound(FLAG);

        getQuestionsList();
        score = 0;

    }

    private void getQuestionsList() {

        QuestionModelList = new ArrayList<>();

        firestore.collection("SamanyaGyan").document("CAT"+String.valueOf(category_id))
                .collection("SET" + String.valueOf(setNo))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot questions = task.getResult();

                    for(QueryDocumentSnapshot doc: questions){
                        QuestionModelList.add(new QuestionModel(doc.getString("QUESTION"),
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                Integer.valueOf(doc.getString("ANSWER"))
                                ));

                    }

                    setQuestion();

                } else {
                    Toast.makeText(Questions.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

                loading.cancel();
            }
        });

        /*QuestionModelList.add(new QuestionModel("Question 1","A", "B","C","D", 2));
        QuestionModelList.add(new QuestionModel("Question 2","A", "B","C","D", 1));
        QuestionModelList.add(new QuestionModel("Question 3","A", "B","C","D", 2));
        QuestionModelList.add(new QuestionModel("Question 4","A", "B","C","D", 4));
        QuestionModelList.add(new QuestionModel("Question 5","A", "B","C","D", 3));
*/


    }

    private void setQuestion() {
        timer.setText(String.valueOf(20));

        question.setText(QuestionModelList.get(0).getQuestion());

        option1.setText(QuestionModelList.get(0).getOption1());
        option2.setText(QuestionModelList.get(0).getOption2());
        option3.setText(QuestionModelList.get(0).getOption3());
        option4.setText(QuestionModelList.get(0).getOption4());

        question_counter.setText((String.valueOf(1)+"/"+ String.valueOf(QuestionModelList.size())));

        startTimer();
        questionNumber = 0;

    }

    private void startTimer() {
        count = new CountDownTimer(22000,1000) {
            @Override
            public void onTick(long l) {
                if(l <20000)
                    timer.setText(String.valueOf(l/1000));
            }


            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        count.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        int pressedOption = 0;

        switch (view.getId()){
            case R.id.option1:
                pressedOption = 1;
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);

                break;

            case R.id.option2:
                pressedOption = 2;
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);

                break;

            case R.id.option3:
                pressedOption = 3;
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);

                break;

            case R.id.option4:
                pressedOption = 4;
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                option4.setEnabled(false);

                break;

            default:
        }
        count.cancel();
        checkAnswer(pressedOption,view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(int pressedOption, View view) {
        if(pressedOption == QuestionModelList.get(questionNumber).getAnswer()) {
            //Correct Ans
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            FLAG = 1;
            soundEffect.setSound(FLAG);
            score ++;
        }
        else{
            //Wrong Answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            FLAG = 2;
            soundEffect.setSound(FLAG);

            switch (QuestionModelList.get(questionNumber).getAnswer()){
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
            }
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },2000);

    }

    private void changeQuestion() {
        if(questionNumber < QuestionModelList.size()-1){
            //goto next question

            questionNumber++;
            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            question_counter.setText(String.valueOf(questionNumber+1)+"/"+String.valueOf(QuestionModelList.size()));
            timer.setText(String.valueOf(20));

            startTimer();

            option1.setEnabled(true);
            option2.setEnabled(true);
            option3.setEnabled(true);
            option4.setEnabled(true);


        }
        else{
            //Dispplay score of the player: go to score.class
            Intent intent= new Intent(Questions.this, Score.class);
            intent.putExtra("SCORE",String.valueOf(score) + "/" + String.valueOf(QuestionModelList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //Questions.this.finish();
        }
    }

    private void playAnim(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animator) {
                if(value == 0){
                    switch (viewNum){
                        case 0:
                            ((TextView)view).setText(QuestionModelList.get(questionNumber).getQuestion());
                            break;
                        case 1:
                            ((Button)view).setText(QuestionModelList.get(questionNumber).getOption1());
                            break;
                        case 2:
                            ((Button)view).setText(QuestionModelList.get(questionNumber).getOption2());
                            break;
                        case 3:
                            ((Button)view).setText(QuestionModelList.get(questionNumber).getOption3());
                            break;
                        case 4:
                            ((Button)view).setText(QuestionModelList.get(questionNumber).getOption4());
                            break;
                    }
                    if(viewNum != 0){
                        ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));
                    }

                    playAnim(view,1,viewNum);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        count.cancel();
    }
}