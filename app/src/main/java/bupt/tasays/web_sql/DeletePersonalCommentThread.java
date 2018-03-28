package bupt.tasays.web_sql;

import android.os.Handler;

import java.sql.ResultSet;

import bupt.tasays.DB_Direct.DBManager;

/**
 * Created by root on 18-3-28.
 */

public class DeletePersonalCommentThread extends Thread{
    private Handler handler;
    private int SUCCESS=3;
    private int FAILURE=0;
    private DBManager dbManager;
    int contentid;
    String sql;

    public DeletePersonalCommentThread(Handler handler,int contentid){
        this.handler=handler;
        this.contentid=contentid;
    }
    @Override
    public void run(){
        sql = "delete from comments where contentid="+Integer.toString(contentid)+";";
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        int success=dbManager.executeUpdate(sql);
        if(success!=0)
            handler.obtainMessage(SUCCESS).sendToTarget();
        else
            handler.obtainMessage(FAILURE);
    }
}
