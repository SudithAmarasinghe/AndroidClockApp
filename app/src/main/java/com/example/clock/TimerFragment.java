package com.example.clock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private static long startingTime;

    private TextView txtView;
    private Button startBtn;
    private Button stopBtn;
    private Button setBtn;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeft = startingTime;
    private EditText editText;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        txtView = view.findViewById(R.id.cdTimer);
        startBtn = view.findViewById(R.id.btStart);
        stopBtn = view.findViewById(R.id.btStop);
        setBtn = view.findViewById(R.id.btSet);
        editText = view.findViewById(R.id.txtTimer);

        startBtn.setBackgroundColor(0xFFFFFFFF);
        stopBtn.setBackgroundColor(0xFFFFFFFF);
        setBtn.setBackgroundColor(0xFFFFFFFF);

        initial();

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingTime = Long.valueOf(String.valueOf(editText.getText())) * 1000 * 60;
                txtView.setText(editText.getText()+":"+"00");
                setBtn.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.VISIBLE);
                txtView.setVisibility(View.VISIBLE);
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pauseTimer();
                }else{
                    resetTimer();
                    startTimer();
                }
            }
        });



        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initial();
            }
        });

        updateCountdownText();

        return view;
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountdownText();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timerRunning = false;
                startBtn.setText("Start");
            }

        }.start();

        timerRunning = true;
        startBtn.setText("Pause");
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        startBtn.setText("Resume");
    }

    private void resetTimer(){
        timeLeft = startingTime;
        updateCountdownText();
    }

    private void updateCountdownText(){
        int minutes = (int) (timeLeft / 1000)/ 60;
        int seconds = (int) (timeLeft / 1000)% 60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        txtView.setText(timeLeftFormatted);
    }

    private void initial(){
        startBtn.setVisibility(View.GONE);
        stopBtn.setVisibility(View.GONE);
        txtView.setVisibility(View.GONE);
        setBtn.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText.setText("");
    }
}