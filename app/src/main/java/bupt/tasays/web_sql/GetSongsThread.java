package bupt.tasays.web_sql;

import android.os.Handler;

import java.sql.ResultSet;


import bupt.tasays.DB_Direct.DBManager;

public class GetSongsThread extends Thread {
    private Handler handler;
    private int SUCCESS=1;
    private int FAILURE=0;
    private DBManager dbManager;
    String sql;
    int songids[],songCount=-1;
    public GetSongsThread(Handler handler,int [] songids,int songCount){
        this.handler=handler;
        this.songCount=songCount;
        this.songids=songids;
    }

    @Override
    public void run() {
        if(songCount==0)
            return;
        sql = "select * from songinfos where songid "+generateID();
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        ResultSet resultSet = dbManager.executeQuery(sql);
        if (resultSet != null)
            handler.obtainMessage(SUCCESS, resultSet).sendToTarget();
        else
            handler.obtainMessage(FAILURE);
    }

    public String generateID(){
        String tempString="in(";
        for(int i=0;i<songCount-1;i++)
        {
            tempString+=Integer.toString(songids[i])+",";
        }
        tempString+=Integer.toString(songids[songCount-1])+")";
        return tempString;
    }
}
