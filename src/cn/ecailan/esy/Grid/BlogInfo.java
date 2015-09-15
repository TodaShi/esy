package cn.ecailan.esy.Grid;

/**
 * Created by Administrator on 2015-5-20.
 */
public class BlogInfo {
    public int shot_id; //发表id
    public int user_id; //用户id
    public String icon; //用户头像
    public String user_name;//用户名字
    public String title;    //发表内容
    public int views_count; //观看数
    public int likes_count;// 赞的数量
    public int comment_count;   //回复数
    public String addtime;      //新增日期
    public String imageUrl;     //图片地址

    public int getShot_id() {
        return shot_id;
    }

    public void setShot_id(int shot_id) {
        this.shot_id = shot_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
