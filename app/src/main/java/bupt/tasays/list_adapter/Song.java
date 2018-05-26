package bupt.tasays.list_adapter;

public class Song {
    public int singerid;
    public String singername;
    public int songid;
    public String songname;
    public String album;
    public String url;

    public Song(int singerid,String singername,int songid,String songname,String album,String url){
        this.singerid=singerid;
        this.singername=singername;
        this.songid=songid;
        this.songname=songname;
        this.album=album;
        this.url=url;
    }

}
