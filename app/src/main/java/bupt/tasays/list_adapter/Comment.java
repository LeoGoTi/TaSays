package bupt.tasays.list_adapter;

/**
 * Created by root on 17-12-12.
 */

public class Comment {
    private String comment;
    private String user_id;
    private String song;
    private String url;
    private int contentid;
    private boolean isLiked;

    public Comment(String comment,String user_id,String song,String url,int contentid,boolean isLiked)
    {
        this.comment=comment;
        this.user_id=user_id;
        this.song=song;
        this.url=url;
        this.contentid=contentid;
        this.isLiked=isLiked;
    }

    public String getComment()
    {
        return comment;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public String getSong()
    {
        return song;
    }

    public String getUrl()
    {
        return url;
    }

    public int getContentid(){
        return contentid;
    }

    public boolean getIsLiked(){
        return isLiked;
    }
}
