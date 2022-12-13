package com.example.clock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Display a stopwatch with milliseconds, seconds and
 * minutes. User can START, PAUSE, RESUME and RESET the stopwatch.
 * A simple {@link Fragment} subclass.
 * Use the {@link StopwatchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Sudith Amarasinghe
 * @version 1.0
 * @since 2022-08-10
 */
public class StopwatchFragment extends Fragment {

    TextView timer ;
    Button start, pause, reset;
    long millisecondTime, startTime, timeBuff, updateTime = 0L ;
    Handler handler;
    int seconds, minutes, milliSeconds;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    public static StopwatchFragment newInstance(String param1, String param2) {
        StopwatchFragment fragment = new StopwatchFragment();
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

        View view =  inflater.inflate(R.layout.fragment_stopwatch, container, false);
        timer = (TextView)view.findViewById(R.id.tvTimer);
        start = (Button)view.findViewById(R.id.btStart);
        pause = (Button)view.findViewById(R.id.btPause);
        reset = (Button)view.findViewById(R.id.btReset);

        handler = new Handler() ;

        start.setBackgroundColor(0xFFFFFFFF);
        pause.setBackgroundColor(0xFFFFFFFF);
        reset.setBackgroundColor(0xFFFFFFFF);


        /**
         * <h1>Start the stopwatch</h1>
         * By clicking start button user starts the stopwatch.
         */
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }

        });

        /**
         * By clicking PAUSE button user can pause
         * the stopwatch.
         * <p>
         * Again pressing the same button which indicate
         * as RESUME, user can start again
         * the stopwatch from stopped point.
         */
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                timeBuff += millisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                start.setText("Resume");

            }
        });


        /**
         * Pressing RESET button, the stopwatch is reset.
         * Counting starts from zero.
         */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                millisecondTime = 0L ;
                startTime = 0L ;
                timeBuff = 0L ;
                updateTime = 0L ;
                seconds = 0 ;
                minutes = 0 ;
                milliSeconds = 0 ;

                timer.setText("00:00:00");
                start.setText("Start");

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Time is calculated to display according
     * minutes, seconds and milliseconds.
     */
    public Runnable runnable = new Runnable() {

        public void run() {

            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);

            timer.setText("" + minutes + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliSeconds));

            handler.postDelayed(this, 0);
        }

    };

}