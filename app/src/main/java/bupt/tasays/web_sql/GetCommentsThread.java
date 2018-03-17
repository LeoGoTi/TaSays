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

    public GetCommentsThread(Handler handler){
        this.handler=handler;
    }
    @Override
    public void run(){
        sql = "SELECT content,songname,singername from comments,songinfo where comments.songid=songinfo.songid;";
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        ResultSet resultSet = dbManager.executeQuery(sql);
        if(resultSet!=null)
            handler.obtainMessage(SUCCESS,resultSet).sendToTarget();
        else
            handler.obtainMessage(FAILURE);
    }
}
