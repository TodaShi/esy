package org.ry8.CommonFunc;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Designer on 2015/5/19.
 */
public class Interaction {

    public  static void toastShow(Context context,String msg) {
        Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
    }
}
