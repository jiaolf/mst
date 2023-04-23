package com.jiaolf.dream.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jiaolf.dream.R;

public class MVPDemoActivity extends AppCompatActivity implements GoodsViewInterface{

    TextView tvInfo;

    Object goodInfo;

    GoodsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpdemo);

        initView();

        presenter = new GoodsPresenter(this);
    }

    private void initView() {
        tvInfo = findViewById(R.id.tv_goods);
        findViewById(R.id.btn).setOnClickListener(v -> {
            presenter.fetchGoodsInfo();
        });
    }

    @Override
    public void showGoods(String data) {
        tvInfo.setText(data);
    }

    @Override
    public void showLoading() {
        Log.i("jlf", "loading...");
    }

    @Override
    public void hideLoading() {
        Log.i("jlf", "hide loading");
    }
}