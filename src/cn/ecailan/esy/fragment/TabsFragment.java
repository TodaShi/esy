package cn.ecailan.esy.fragment;

//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by ry8
//

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ecailan.esy.GlobalVariable;
import cn.ecailan.esy.R;
import cn.ecailan.esy.activity.YxtMainActivity;


public class TabsFragment extends Fragment {

    public ImageView tab_one;
    public ImageView tab_two;
    public ImageView tab_three;

    ImageView tab_onebg;
    ImageView tab_twobg;
    ImageView tab_threebg;
    ImageView tab_fourbg;
    private int newTabNum;
    private int oldTabNum = 1;



    public TextView attend_notify_num;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    public OperationFragment tab1frament;
    public PurchaseFragment tab2frament;
    public ProductFragment tab3frament;

    public Fragment curFragment;

    public TabsFragment() {
        GlobalVariable.tabsFragment=this;
    }

    /*
     * (non-Javadoc)
     *
     * 
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fourtoolbar, container, false);
        init(mainView);

        shared = getActivity().getSharedPreferences("userInfo", 0);
        editor = shared.edit();

        return mainView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onActivityCreated(Bundle paramBundle) {
        super.onActivityCreated(paramBundle);
        setRetainInstance(true);
    }

    void init(View mainView) {

        attend_notify_num=(TextView)mainView.findViewById(R.id.fourtoolbar_attend_notify_num);

        this.tab_one = (ImageView) mainView.findViewById(R.id.fourtoolbar_tabone);
        //    this.tab_onebg = (ImageView) mainView.findViewById(R.id.toolbar_tabonebg);
        this.tab_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTabNum = 1;
                OnTabSelected("tab_one");
            }
        });

        this.tab_two = (ImageView) mainView.findViewById(R.id.fourtoolbar_tabtwo);
        // this.tab_twobg = (ImageView) mainView.findViewById(R.id.toolbar_tabtwobg);
        this.tab_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTabNum = 2;
                OnTabSelected("tab_two");
            }
        });

        this.tab_three = (ImageView) mainView.findViewById(R.id.fourtoolbar_tabthree);
        //  this.tab_threebg = (ImageView) mainView.findViewById(R.id.toolbar_tabthreebg);
        this.tab_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTabNum = 3;
                OnTabSelected("tab_three");
            }
        });



        OnTabSelected("tab_one");
    }

    void OnTabSelected(String tabName) {

        YxtMainActivity mainAc=new YxtMainActivity();
        if (tabName == "tab_one") {

            if (null == tab1frament) {
                tab1frament = new OperationFragment();
            }

//            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
//            localFragmentTransaction.replace(R.id.fragment_container, tab1frament, "tab_one");
//            localFragmentTransaction.commit();
            switchContent(tab1frament);


            oldTabNum = 1;
            this.tab_one.setImageResource(R.drawable.fourtoolbar_icon_1a);
            this.tab_two.setImageResource(R.drawable.fourtoolbar_icon_2u);
            this.tab_three.setImageResource(R.drawable.fourtoolbar_icon_3u);

           /* this.tab_onebg.setVisibility(View.VISIBLE);
            this.tab_twobg.setVisibility(View.INVISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);*/

        } else if (tabName == "tab_two") {

            if (null == tab2frament) {
                tab2frament = new PurchaseFragment();
            }
//            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
//            localFragmentTransaction.replace(R.id.fragment_container, tab2frament, "tab_two");
//            localFragmentTransaction.commit();

            switchContent( tab2frament);
            oldTabNum = 2;
            this.tab_one.setImageResource(R.drawable.fourtoolbar_icon_1u);
            this.tab_two.setImageResource(R.drawable.fourtoolbar_icon_2a);
            this.tab_three.setImageResource(R.drawable.fourtoolbar_icon_3u);

           /* this.tab_onebg.setVisibility(View.INVISIBLE);
            this.tab_twobg.setVisibility(View.VISIBLE);
            this.tab_threebg.setVisibility(View.INVISIBLE);
            this.tab_fourbg.setVisibility(View.INVISIBLE);*/
        } else if (tabName == "tab_three") {
            if (null == tab3frament) {
                tab3frament = new ProductFragment();
            }
//            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
//            localFragmentTransaction.replace(R.id.fragment_container, tab3frament, "tab_three");
//            localFragmentTransaction.commit();
            switchContent(tab3frament);

            oldTabNum = 3;
            this.tab_one.setImageResource(R.drawable.fourtoolbar_icon_1u);
            this.tab_two.setImageResource(R.drawable.fourtoolbar_icon_2u);
            this.tab_three.setImageResource(R.drawable.fourtoolbar_icon_3a);

             /*   this.tab_onebg.setVisibility(View.INVISIBLE);
                this.tab_twobg.setVisibility(View.INVISIBLE);
                this.tab_threebg.setVisibility(View.VISIBLE);
                this.tab_fourbg.setVisibility(View.INVISIBLE);*/
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //登录成功返回到个人主页
        if (requestCode == 1) {
            if (data != null) {
                if (null == tab1frament) {
                    tab1frament = new OperationFragment();
                }

                FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
                localFragmentTransaction.replace(R.id.fragment_container, tab1frament, "tab_one");
                localFragmentTransaction.commit();

                this.tab_one.setImageResource(R.drawable.fourtoolbar_icon_1a);
                this.tab_two.setImageResource(R.drawable.fourtoolbar_icon_2u);
                this.tab_three.setImageResource(R.drawable.fourtoolbar_icon_3u);

				/*this.tab_onebg.setVisibility(View.INVISIBLE);
				this.tab_twobg.setVisibility(View.INVISIBLE);
				this.tab_threebg.setVisibility(View.INVISIBLE);
				this.tab_fourbg.setVisibility(View.VISIBLE);*/
            }
        }
    }

    public void switchContent( Fragment to) {
        if (curFragment != to) {
            FragmentTransaction transaction;
            if(newTabNum > oldTabNum) {
                transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
            }else{
                transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_from_right);
            }
                if (!to.isAdded()) {    // 先判断是否被add过
                if(curFragment!=null)
                    transaction.hide(curFragment);
                transaction.add(R.id.fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }
            else{
                if(curFragment!=null)
                    transaction.hide(curFragment);
                transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个

           }
            curFragment = to;
        }

    }

}