package cn.ecailan.esy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.BoyuanOperationAdapter;

/**
 * Created by Myuluo on 2015/9/2.
 * 博元端控制界面--衍生出三个Fragment
 */
public class BoyuanConntrolActivity extends FragmentActivity {

    private BoyuanOperationAdapter adapter;
    private ArrayList<Fragment> fragmentsList;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.boyuan_control_layout);
        iniView();

    }

    private void iniView() {



    }
}
