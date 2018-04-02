package bupt.tasays.web_sql;

import android.os.Handler;

import java.sql.ResultSet;

import bupt.tasays.DB_Direct.DBManager;
/**
 * Created by root on 18-3-17.
 */

public class GetCommentsThread extends Thread{
    private Handler handler;
    private int SUCCESS=1;
    private int FAILURE=0;
    private int SUCCESS_IDS=10;
    private DBManager dbManager;
    String sql;
    String type=null;//用于专题的获取
    int userid;
    int contentids[],contentCount=-1;

    public GetCommentsThread(Handler handler,int userid){
        this.handler=handler;
        this.userid=userid;
    }
    public GetCommentsThread(Handler handler,String type,int userid){
        this.handler=handler;
        this.type=type;
        this.userid=userid;
    }
    //此方法用于获取指定id数集的评论
    public GetCommentsThread(Handler handler,int userid,int [] contentids,int contentCount){
        this.handler=handler;
        this.userid=userid;
        this.contentids=contentids;
        this.contentCount=contentCount;
    }

    @Override
    public void run(){
        if(contentCount==-1) {
            if (type == null)
                sql = "SELECT content,songname,singername,url,contentid,exists(select * from collection where userid=" + userid + " and collection.contentid=comments.contentid) as isliked\n" +
                        "from comments,songinfo where comments.songid=songinfo.songid order by rand() LIMIT 20;";
            else
                sql = "SELECT content,songname,singername,url,contentid,exists(select * from collection where userid=" + userid + " and collection.contentid=comments.contentid) as isliked\n" +
                        "from comments,songinfo where comments.songid=songinfo.songid and class1='" + type + "' LIMIT 20;";
            dbManager = DBManager.createInstance();
            dbManager.connectDB();
            ResultSet resultSet = dbManager.executeQuery(sql);
            if (resultSet != null)
                handler.obtainMessage(SUCCESS, resultSet).sendToTarget();
            else
                handler.obtainMessage(FAILURE);
        }
        else
        {
            sql = "SELECT content,songname,singername,url,contentid,exists(select * from collection where userid=" + userid + " and collection.contentid=comments.contentid) as isliked\n" +
                    "from comments,songinfo where comments.songid=songinfo.songid "+generateID()+" order by rand() LIMIT 20;";
            dbManager = DBManager.createInstance();
            dbManager.connectDB();
            ResultSet resultSet = dbManager.executeQuery(sql);
            if (resultSet != null)
                handler.obtainMessage(SUCCESS_IDS, resultSet).sendToTarget();
            else
                handler.obtainMessage(FAILURE);
        }
    }
    //生成sql用的id字符串
    public String generateID(){
        String tempString="and contentid in(";
        for(int i=0;i<contentCount-1;i++)
        {
            tempString+=Integer.toString(contentids[i])+",";
        }
        tempString+=Integer.toString(contentids[contentCount-1])+")";
        return tempString;
    }
}
