package cn.ecailan.esy.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by flywithme on 2015/9/9.
 */
public class S_Product_Head {

    //定义属性
    public int productidg;
    public int name;
    public int classid;
    public int picpath;
    public int price;
    public int barCode;
    public int stock;


    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        this.productidg = jsonObject.optInt("productidg",-1);
        this.name = jsonObject.optInt("name",-1);
        this.classid = jsonObject.optInt("classid",-1);
        this.picpath = jsonObject.optInt("picpath",-1);
        this.price = jsonObject.optInt("price",-1);
        this.barCode = jsonObject.optInt("barCode",-1);
        this.stock = jsonObject.optInt("stock",-1);

        return ;

    }

}
