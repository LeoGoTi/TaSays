package bupt.tasays.tasays;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bupt.tasays.list_adapter.MoodLine;
import bupt.tasays.list_adapter.MoodLineAdapter;
import bupt.tasays.web_sql.GetPrivateCommentsThread;
import bupt.tasays.web_sql.PostCommentThread;
import bupt.tasays.web_sql.WebService;


/**
 * Created by root on 17-12-5.
 */

public class MoodFragment extends Fragment {
    private static List<MoodLine> moodLineList = new ArrayList<>();
    private static List<MoodLine> moodLineListTemp = new ArrayList<>();
    static MoodLineAdapter adapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    MainActivity mainActivity;
    private static Handler handler;
    private static GetPrivateCommentsThread getPrivateCommentsThread;
    private RadioButton happy, normal, sad;
    private static boolean needRefresh = false;
    static ProgressDialog progressDialog;
    VariableChanger changer=new VariableChanger();
    View view;

    String back;
    int[] tempArray = new int[20];

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mood_layout, container, false);
        mainActivity = (MainActivity) getActivity();
        recyclerView = view.findViewById(R.id.mood_recycler);
        floatingActionButton = view.findViewById(R.id.mood_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        handler = new MyHandler();
        adapter = new MoodLineAdapter(moodLineList, handler);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        refreshMoodLine();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    while(!needRefresh){
//                        try {
//                            Thread.sleep(300);
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    };
//                    refreshMoodLine();
//                    needRefresh =false;
//                }
//            }
//        }).start();//监听刷新事件
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    while (back == null) {
//                        try {
//                            Thread.sleep(300);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    progressDialog.dismiss();
//                    Snackbar.make(recyclerView, "写入成功，看看我们给你的推荐吧！", Snackbar.LENGTH_LONG)
//                            .setAction("好的", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    HomeFragment homeFragment = new HomeFragment();
//                                    mainActivity.homeFragment = homeFragment;
//                                    int tempCount = stringToArray(back);
//                                    back = null;
//                                    homeFragment.setFromMood(tempArray, tempCount);
//                                    mainActivity.p1.setImageResource(R.drawable.zhuye);
//                                    mainActivity.p3.setImageResource(R.drawable.xinqing_1);
//                                    mainActivity.p4.setImageResource(R.drawable.geren_1);
//                                    mainActivity.replaceFragment(homeFragment);
//                                }
//                            }).setDuration(Snackbar.LENGTH_LONG).show();
//                }
//            }
//        }).start();
        return view;
    }

    public void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.mood_write, null);//获取自定义布局
        builder.setView(layout);
        //final AlertDialog dlg = builder.create();
        builder.setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        layout.findViewById(R.id.mood_write_edit);
                    }
                })
                .setPositiveButton("写入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = layout.findViewById(R.id.mood_write_edit);

                        Calendar calendar = Calendar.getInstance();
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        happy = layout.findViewById(R.id.mood_write_happy);
                        normal = layout.findViewById(R.id.mood_write_normal);
                        sad = layout.findViewById(R.id.mood_write_sad);
                        boolean a = happy.isChecked(),
                                b = normal.isChecked(),
                                c = sad.isChecked();
                        int tempMoodSrc = a ? R.drawable.smiley_happy_big : (b ? R.drawable.smiley_normal_big : (c ? R.drawable.smiley_sad_big : 9137852));//随便输入了一个错误代码。。。。
                        if (tempMoodSrc != 9137852) {
                            moodLineListTemp.clear();
                            moodLineListTemp.addAll(moodLineList);
                            moodLineList.clear();
                            moodLineList.add(new MoodLine(tempMoodSrc, month + "月" + day + "日", editText.getText().toString() + "\n"));
                            moodLineList.addAll(moodLineListTemp);
                            //adapter=new MoodLineAdapter(moodLineList);
                            //recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            progressDialog = new ProgressDialog(mainActivity);
                            progressDialog.setMessage("加载中，请稍候");
                            progressDialog.show();

                            try {
                                new Thread(new PostCommentThread(mainActivity.getPersonalString("account"), editText.getText().toString(), a ? "Happy" : (b ? "Normal" : "Sad"), handler)).start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String tempString = WebService.executeGetIDs(Integer.toString(mainActivity.getPersonalInt("userid")), "1");
                                        if (tempString != null) {
                                            changer.variableChanger("back",tempString);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else
                            Toast.makeText(mainActivity, "没选择心情哦", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //dialog代替等待
                        //while(back==null);


                    }
                });
        builder.show();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        ResultSet resultSet = (ResultSet) msg.obj;
                        String tempContent, tempClass2;
                        String tempTime;
                        int tempContentid;
                        int tempMoodSrc;
                        while (resultSet.next()) {
                            tempContent = resultSet.getString("content");
                            tempTime = resultSet.getString("time");
                            tempContentid = resultSet.getInt("contentid");
                            tempClass2 = resultSet.getString("class2");
                            String str = tempTime.substring(4, 8);
                            switch (tempClass2) {
                                case "Happy":
                                case "褒":
                                    tempMoodSrc = R.drawable.smiley_happy_big;
                                    break;
                                case "Normal":
                                case "中":
                                    tempMoodSrc = R.drawable.smiley_normal_big;
                                    break;
                                case "Sad":
                                case "贬":
                                    tempMoodSrc = R.drawable.smiley_sad_big;
                                    break;
                                default:
                                    tempMoodSrc = R.drawable.smiley_happy_big;
                                    break;
                            }
                            moodLineList.add(new MoodLine(tempMoodSrc, str.substring(0, 2) + "月" + str.substring(2, 4) + "日", tempContent, tempContentid));
                            adapter.notifyDataSetChanged();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 0://删除失败
                case 4://4号消息：传送成功
                case 3://3号消息：删除成功
                    changer.variableChanger("needRefresh",true);
                    break;
            }
        }
    }

    public void refreshMoodLine() {
        try {
            //开始从服务器载入之前清除已存在的
            moodLineList.clear();
            getPrivateCommentsThread = new GetPrivateCommentsThread(handler, mainActivity.getPersonalString("account"));
            getPrivateCommentsThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int stringToArray(String back)//返回个数
    {
        int count = 0;
        int i = 0;
        String[] arrayStr = back.split(",");
        while (i < arrayStr.length) {
            tempArray[i] = Integer.parseInt(arrayStr[i]);
            i++;
            count++;
        }
        return count;
    }

    public class VariableChanger{
        public VariableChanger(){};
        public void variableChanger(String name, Object o) {
            switch (name) {
                case "needRefresh":
                    if((Boolean)o)
                        refreshMoodLine();
                    needRefresh = false;
                    break;
                case "back":
                    if(o==null)
                        break;
                    back=(String)o;
                    progressDialog.dismiss();
                    Snackbar.make(recyclerView, "写入成功，看看我们给你的推荐吧！", Snackbar.LENGTH_LONG)
                            .setAction("好的", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    HomeFragment homeFragment = new HomeFragment();
                                    mainActivity.homeFragment = homeFragment;
                                    if(back.equals("服务器连接超时..."))
                                        return;
                                    int tempCount = stringToArray(back);
                                    back = null;
                                    homeFragment.setFromMood(tempArray, tempCount);
                                    mainActivity.p1.setImageResource(R.drawable.zhuye);
                                    mainActivity.p3.setImageResource(R.drawable.xinqing_1);
                                    mainActivity.p4.setImageResource(R.drawable.geren_1);
                                    mainActivity.replaceFragment(homeFragment);
                                }
                            }).setDuration(Snackbar.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }
}
