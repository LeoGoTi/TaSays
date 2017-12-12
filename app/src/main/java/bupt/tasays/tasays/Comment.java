package bupt.tasays.tasays;

/**
 * Created by root on 17-12-12.
 */

public class Comment {
    private String comment;
    private String user_id;
    private String song;

    public Comment(String comment,String user_id,String song)
    {
        this.comment=comment;
        this.user_id=user_id;
        this.song=song;
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

}
