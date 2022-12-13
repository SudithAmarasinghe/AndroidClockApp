package com.example.clock;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clock.databinding.ActivityMainBinding;

/**
 * App to use stopwatch, timer and clock (both
 * digital and analog) in same app.
 * Each option an be reached using bottom
 * navigation bar.
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new StopwatchFragment());

        /**
         * Allow to bottom navigation. User can
         * navigate each of three fragments of
         * screens using this.
         */
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.stopwatch:
                    replaceFragment(new StopwatchFragment());
                    break;

                case R.id.timer:
                    replaceFragment(new TimerFragment());
                    break;

                case R.id.clock:
                    replaceFragment(new TimeFragment());

            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}