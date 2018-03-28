package bupt.tasays.web_sql;

import android.os.Handler;

import bupt.tasays.DB_Direct.DBManager;

/**
 * Created by root on 18-3-28.
 */

public class CollectionThread extends Thread {
    private int SUCCESS=3;
    private int FAILURE=0;
    private DBManager dbManager;
    int contentid;
    boolean like;
    int userid;
    String sql;

    public CollectionThread(int contentid,int userid,boolean like){
        this.contentid=contentid;
        this.userid=userid;
        this.like=like;
    }
    @Override
    public void run(){
        if(!like)
            sql="delete from collection where contentid='"
                    +contentid
                    +"'and userid="
                    +Integer.toString(userid)+";";
        else
            sql = "insert into collection(contentid,userid) values ('"
                    +contentid +"',"
                    +Integer.toString(userid)+");";
        dbManager = DBManager.createInstance();
        dbManager.connectDB();
        int success=dbManager.executeUpdate(sql);
    }
}
