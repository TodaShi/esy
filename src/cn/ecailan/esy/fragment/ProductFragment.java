package cn.ecailan.esy.fragment;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.BoyuanProductAdapter;
import cn.ecailan.esy.model.SendMsgRequest;

/**
 *
 产品列表
 */
public class ProductFragment extends Fragment implements BusinessResponse{

    private TextView title;
    private FrameLayout nav_bar;
    private SendMsgRequest sendMsgRequest;
    private ImageView rightButton;
    private ListView productList;
    private BoyuanProductAdapter boyuanProductAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.boyuan_product_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendMsgRequest = new SendMsgRequest(getActivity());
        sendMsgRequest.addResponseListener(ProductFragment.this);
        sendMsgRequest.getProduct();
        initView(view);

    }

    private void initView(View v) {


        title = (TextView) v.findViewById(R.id.topview_title);
        title.setText("产品列表");
        nav_bar = (FrameLayout) v.findViewById(R.id.nav_bar);
        Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);
        if (null != drawable) {
            nav_bar.setBackgroundDrawable(drawable);
        }

        rightButton = (ImageView) v.findViewById(R.id.rightButton);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        productList = (ListView) v.findViewById(R.id.boyuan_product_list);
        boyuanProductAdapter = new BoyuanProductAdapter(getActivity(),sendMsgRequest.arrayProductList);
        productList.setAdapter(boyuanProductAdapter);
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}

