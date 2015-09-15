package cn.ecailan.esy.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import cn.ecailan.esy.R;
import cn.ecailan.esy.model.SendMsgRequest;
import cn.ecailan.esy.model.SendRequest;

//采收操作
public class PurchaseFragment extends Fragment implements BusinessResponse{//

    public static SendMsgRequest sendMsgRequest;
    private TextView title;
    private FrameLayout nav_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.boyuan_purchase_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendMsgRequest = new SendMsgRequest(getActivity());
        sendMsgRequest.addResponseListener(PurchaseFragment.this);

        initView(view);



    }

    private void initView(View v) {

        title = (TextView)v.findViewById(R.id.topview_title);
        title.setText("采收操作");
        nav_bar = (FrameLayout)v.findViewById(R.id.nav_bar);
        Drawable drawable = ResourcesFactory.getDrawable(getResources(), R.drawable.nav_background);

        if (null != drawable) {
            nav_bar.setBackgroundDrawable(drawable);
        }

    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

    }
}


