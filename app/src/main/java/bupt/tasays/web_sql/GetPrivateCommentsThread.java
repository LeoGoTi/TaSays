package bupt.tasays.web_sql;

import android.os.Handler;

import java.sql.ResultSet;

import bupt.tasays.DB_Direct.DBManager;

/**
 * Created by root on 18-3-24.
 */

public class GetPrivateCommentsThread extends Thread{
    private Handler handler;
    private int SUCCESS=1;
    private int FAILURE=0;
    private DBManager dbManager;
    String account;
    String sql;

    public GetPrivateCommentsThread(Handler handler,String account){
        this.handler=handler;
        this.account=account;
    }
    @Override
    public void run(){
        sql = "SELECT content,time,class2,contentid from comments,users\n" +
                "where userid=ID and account='"+account+"' ORDER BY time DESC ;";
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        ResultSet resultSet = dbManager.executeQuery(sql);
        if(resultSet!=null)
            handler.obtainMessage(SUCCESS,resultSet).sendToTarget();
        else
            handler.obtainMessage(FAILURE);
    }
}
