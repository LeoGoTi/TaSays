package bupt.tasays.list_adapter;

/**
 * Created by root on 18-3-17.
 */

public class MoodLine {
    private int moodSrc;
    private String time;
    private String content;
    private int contentid;

    public MoodLine(int moodSrc,String time,String content){
        this.moodSrc=moodSrc;
        this.time=time;
        this.content=content;
    }

    public MoodLine(int moodSrc,String time,String content,int contentid){
        this.moodSrc=moodSrc;
        this.time=time;
        this.content=content;
        this.contentid=contentid;
    }

    public int getMoodSrc(){
        return moodSrc;
    }

    public String getTime(){
        return time;
    }

    public String getContent(){
        return  content;
    }

    public int getContentid(){
        return contentid;
    }
}
