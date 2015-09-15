package cn.ecailan.esy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ry8.CeaFrame.model.BusinessResponse;
import org.ry8.CeaFrame.theme.ResourcesFactory;
import org.ry8.external.androidquery.callback.AjaxStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.ecailan.esy.R;
import cn.ecailan.esy.adapter.BoyuanOperationAdapter;
import cn.ecailan.esy.model.SendMsgRequest;
import cn.ecailan.esy.view.ListViewSwipeGesture;

// 操作项列表管理界面Fragment
public class OperationFragment extends Fragment implements BusinessResponse{

    public SharedPreferences shared;
    private SendMsgRequest sendMsgRequest;
    private Button btnAdd;
    private InputMethodManager inputMethodManager;
    private EditText replyEdit;
    private BoyuanOperationAdapter boyuanOperationAdapter;
    private ListView operationList;
    private TextView title;
    private FrameLayout nav_bar;
    private ImageView rightButton;
    private String opname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.boyuan_operation_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendMsgRequest = new SendMsgRequest(getActivity());
        sendMsgRequest.addResponseListener(OperationFragment.this);
        sendMsgRequest.getOpname();
        initView(view);
    }

    private void initView(View v) {

        title = (TextView) v.findViewById(R.id.topview_title);
        title.setText("操作项列表管理");
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
                showEditConment(v);
            }
        });
        operationList = (ListView) v.findViewById(R.id.operation_list);
        boyuanOperationAdapter = new BoyuanOperationAdapter(getActivity(),sendMsgRequest.operationArrayList);
        operationList.setAdapter(boyuanOperationAdapter);
        final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(operationList,swipeListener,getActivity());
        touchListener.SwipeType	=	ListViewSwipeGesture.Double;
        operationList.setOnTouchListener(touchListener);
    }

    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

        @Override
        public void FullSwipeListView(int position) {
            Toast.makeText(getActivity(), "Action_2", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void HalfSwipeListView(int position) {
//            System.out.println("<<<<<<<" + position);
            String opname =  sendMsgRequest.operationArrayList.remove(position).get("opname").toString();
            sendMsgRequest.delOpname(opname);
            boyuanOperationAdapter.notifyDataSetChanged();
//            Toast.makeText(getActivity(),"删除", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void LoadDataForScroll(int count) {

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
//            Toast.makeText(activity,"Delete", Toast.LENGTH_SHORT).show();
//            for(int i:reverseSortedPositions){
//                data.remove(i);
//                new MyAdapter().notifyDataSetChanged();
//            }
        }

        @Override
        public void OnClickListView(int position) {

        }


    };

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

         if("add_opname".equals(status.getRequestCode())){
             HashMap<String, Object> hashMap = new HashMap<String, Object>();
             hashMap.put("opname",opname);
             sendMsgRequest.operationArrayList.add(hashMap);
             boyuanOperationAdapter.notifyDataSetChanged();
         }

    }


    //弹出输入框和输入法
    private void showEditConment(View view) {

        View convertView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_comment_replay, null);
        Button sendMessage = (Button) convertView.findViewById(R.id.send_msg);

        replyEdit = (EditText) convertView.findViewById(R.id.reply);
        final PopupWindow pw = new PopupWindow(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pw.setTouchable(true);
        pw.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opname = replyEdit.getText().toString();
                sendMsgRequest.addOpname(opname);
                pw.dismiss();
            }
        });
        pw.setBackgroundDrawable(getActivity().getResources().getDrawable(R.color.white));
        replyEdit.setFocusable(true);
        replyEdit.requestFocus();
        replyEdit.setFocusableInTouchMode(true);
        pw.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        inputMethodManager = (InputMethodManager) replyEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               inputMethodManager =
                                       (InputMethodManager) replyEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputMethodManager.showSoftInput(replyEdit, 0);
                           }

                       },
                500);


    }
}
