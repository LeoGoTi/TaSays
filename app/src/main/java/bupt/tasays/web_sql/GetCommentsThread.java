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
    private DBManager dbManager;
    String sql;
    String type=null;//用于专题的获取
    int userid;

    public GetCommentsThread(Handler handler,int userid){
        this.handler=handler;
        this.userid=userid;
    }
    public GetCommentsThread(Handler handler,String type,int userid){
        this.handler=handler;
        this.type=type;
        this.userid=userid;
    }
    @Override
    public void run(){
        if(type==null)
            sql = "SELECT content,songname,singername,url,contentid,exists(select * from collection where userid="+userid+" and collection.contentid=comments.contentid) as isliked\n" +
                    "from comments,songinfo where comments.songid=songinfo.songid order by rand() LIMIT 20;";
        else
            sql = "SELECT content,songname,singername,url,contentid,exists(select * from collection where userid="+userid+" and collection.contentid=comments.contentid) as isliked\n" +
                    "from special,songinfo where comments.songid=songinfo.songid and subject='"+type+"' LIMIT 20;";
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        ResultSet resultSet = dbManager.executeQuery(sql);
        if(resultSet!=null)
            handler.obtainMessage(SUCCESS,resultSet).sendToTarget();
        else
            handler.obtainMessage(FAILURE);
    }
}
