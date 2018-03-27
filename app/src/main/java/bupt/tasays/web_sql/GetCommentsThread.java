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

    public GetCommentsThread(Handler handler){
        this.handler=handler;
    }
    public GetCommentsThread(Handler handler,String type){
        this.handler=handler;
        this.type=type;
    }
    @Override
    public void run(){
        if(type==null)
            sql = "SELECT content,songname,singername from comments,songinfos where comments.songid=songinfos.songid order by rand();";
        else
            sql = "SELECT content,songname,singername from special,songinfo where special.songid=songinfo.songid and subject='"+type+"';";
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        ResultSet resultSet = dbManager.executeQuery(sql);
        if(resultSet!=null)
            handler.obtainMessage(SUCCESS,resultSet).sendToTarget();
        else
            handler.obtainMessage(FAILURE);
    }
}
