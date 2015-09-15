package cn.ecailan.esy;



public class ApiInterface {

    public static final String serviceUrl_PRODUCTION = "http://120.24.83.80/suyuan/suyuanif.ashx";

    public static final String serviceUrl_DEBUG = "http://120.24.83.80/suyuan/suyuanif.ashx";

    public static String getSnapUrl(String devicecode,int memberid,  String attendtime, int index) {
        return "http://120.24.83.80/yxt/snap.ashx?dcode=" + devicecode + "&mid=" + memberid + "&atime=" + attendtime + "&i=" + index;
    }

    public static String getSnaptbUrl(String devicecode,int memberid,  String attendtime, int index) {
        return "http://120.24.83.80/yxt/snaptb.ashx?dcode=" + devicecode + "&mid=" + memberid + "&atime=" + attendtime + "&i=" + index;
    }

    public static final String STATUSES_PUBLIC_TIMELINE = "/statuses/public_timeline.json";
    public static final String STATUSES_FRIENDS_TIMELING = "/statuses/friends_timeline.json";
    public static final String TRENDS_DAILY = "/trends/daily.json";

    public static final String PLAYERS = "/players/";

    public static final String SHOT_LIST = "/shots";


    public static final String USER_SIGNIN = "/user/signin";
    public static final String SENDPSW = "/user/sendPsw";
    public static final String USER_SIGNUP = "/user/signup";
    public static final String RECHARGE_STEP1 = "/user/recharge_step1";
    public static final String RECHARGE_STEP2 = "/user/recharge_step2";
    public static final String GET_ATTENDRECORD = "/get/attendrecord";




}