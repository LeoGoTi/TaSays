package bupt.tasays.list_adapter;

/**
 * Created by root on 17-12-12.
 */

public class Comment {
    private String comment;
    private String user_id;
    private String song;
    private String url;

    public Comment(String comment,String user_id,String song,String url)
    {
        this.comment=comment;
        this.user_id=user_id;
        this.song=song;
        this.url=url;
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
}
