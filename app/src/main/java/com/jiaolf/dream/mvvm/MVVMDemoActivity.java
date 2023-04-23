package com.jiaolf.dream.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.jiaolf.dream.R;

public class MVVMDemoActivity extends AppCompatActivity {

    TextView tvName;

    NameViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvmdemo);

        tvName = findViewById(R.id.tv_name);

        model = new ViewModelProvider(this).get(NameViewModel.class);

        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                    tvName.setText(s);
            }
        };

        model.getCurrentName().observe(this, nameObserver);





        findViewById(R.id.btn_live).setOnClickListener(v -> {
            String name = "Nice! " + (System.currentTimeMillis() + "").substring(8);
            model.getCurrentName().setValue(name);
        });
    }
}