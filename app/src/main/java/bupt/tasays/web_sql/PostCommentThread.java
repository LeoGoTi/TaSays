package bupt.tasays.web_sql;

import android.os.Handler;
import java.sql.ResultSet;
import java.util.Calendar;

import bupt.tasays.DB_Direct.DBManager;
/**
 * Created by root on 18-3-17.
 */

public class PostCommentThread extends Thread{
    private DBManager dbManager;
    String sql;
    String account,content,mood;
    Handler handler;

    public PostCommentThread(String account, String content, String mood,Handler handler){
        this.content=content;
        this.account=account;
        this.mood=mood;
        this.handler=handler;
    }
    @Override
    public void run(){
        try {
            String time=getTime();
            sql = "SELECT * FROM users where account='"+account+"'";
            dbManager = DBManager.createInstance();
            dbManager.connectDB();
            ResultSet resultSet = dbManager.executeQuery(sql);
            resultSet.absolute(1);
            int id = resultSet.getInt("ID");
            sql = "INSERT into comments(content,userid,time,class2) values"+
                    "('"+content+"',"+
                    Integer.toString(id)+",'"+
                    time+"','"+
                    mood+"')";//mood暂时用英文Happy代替
            dbManager.executeUpdate(sql);
            handler.obtainMessage(4).sendToTarget();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return Integer.toString(year)+
                (month<=9?"0"+Integer.toString(month):Integer.toString(month))+
                (day<=9?"0"+Integer.toString(day):Integer.toString(day))+
                (hour<=9?"0"+Integer.toString(hour):Integer.toString(hour))+
                (minute<=9?"0"+Integer.toString(minute):Integer.toString(minute))+
                (second<=9?"0"+Integer.toString(second):Integer.toString(second));//这句没改完
    }
}
