package bupt.tasays.list_adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import java.util.List;


/**
 * Created by root on 18-3-18.
 */

public class AdapterViewpager extends PagerAdapter {
    private List<View> mViewList;
    private Handler handler;
    private int SUCCESS_TYPE=4;
    private float xDistance, yDistance, xLast, yLast;

    public AdapterViewpager(List<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {//必须实现
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {//必须实现
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {//必须实现，实例化
        container.addView(mViewList.get(position));
        mViewList.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type="初恋";
                switch (position){
                    case 0:
                        type="初恋";
                        break;
                    case 1:
                        type="毕业";
                        break;
                    case 2:
                        type="台湾偶像剧";
                        break;
                    case 3:
                        type="旅游";
                        break;
                    default:
                        break;
                }
                handler.obtainMessage(SUCCESS_TYPE,type).sendToTarget();
            }
        });
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        container.removeView(mViewList.get(position));
    }

    public void setHandler(Handler handler) {
        this.handler=handler;
    }

}
