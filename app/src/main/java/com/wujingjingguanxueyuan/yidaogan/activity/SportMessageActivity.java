package com.wujingjingguanxueyuan.yidaogan.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.base.BaseActivity;
import com.wujingjingguanxueyuan.yidaogan.bean.SportEntry;
import com.wujingjingguanxueyuan.yidaogan.utils.SaveKeyValues;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SportMessageActivity extends BaseActivity implements View.OnClickListener{

    private TextView finish_plans;
    private TextView sport_days;
    private TextView sport_hot;
    private TextView keepfit_scores;
    private int plans;
    private String hot_str;
    private int day_values;
    private int scores;
  //  private DatasDao datasDao;
    private Button all_btn,day_btn;
    private LinearLayout all_lin;
    private RelativeLayout day_lin;
   // private Cursor cursor;
    private ListView dataList;
    private int counts;
    private List<Map<String , Object>> list;
    private List<SportEntry> sportEntries = new ArrayList<>();
    @Override
    protected void setActivityTitle() {
        initTitle();
        setTitle("运动记录", this);
        setMyBackGround(R.color.watm_background_gray);
        setTitleTextColor(R.color.theme_blue_two);
        setTitleLeftImage(R.mipmap.mrkj_back_blue);
    }

    @Override
    protected void getLayoutToView() {
        setContentView(R.layout.activity_sport_message);
    }

    @Override
    protected void initValues() {
        plans = SaveKeyValues.getIntValues("finish_plan" , 0);
        Log.e("plans",plans+"");
      //  datasDao = new DatasDao(this);
      //  cursor = datasDao.selectAll("step");
        BmobQuery<SportEntry> stepsBmobQuery = new BmobQuery<>();
        stepsBmobQuery.addWhereEqualTo("user", BmobUser.getCurrentUser());
        stepsBmobQuery.findObjects(new FindListener<SportEntry>() {
            @Override
            public void done(List<SportEntry> list, BmobException e) {
                if(e==null){
                    sportEntries = list;
                }
            }
        });
        day_values = 1 + sportEntries.size();
        double hot_values = 0;
        int step = 0;
        counts = sportEntries.size();
        list =new ArrayList<>();
        if ( counts> 0){
            for(int i =0;i<counts;i++){
                String hot = sportEntries.get(i).getSport_heat();
                int steps = sportEntries.get(i).getSport_steps();
                double hots = Double.parseDouble(hot);
                hot_values += hots;
                step += steps;
                Map<String , Object> map = new HashMap<>();
                map.put("date" , sportEntries.get(i).getDate());
                map.put("step" , sportEntries.get(i).getSport_steps());
                map.put("length" , sportEntries.get(i).getSport_distance());
                map.put("hot" , sportEntries.get(i).getSport_heat());
              ///  Log.e("ss", date_data + "==" + step_data + "=="+length_data+"=="+hot_data+ "==");
                list.add(map);

            }
           /* while (cursor.moveToNext()){
                String hot = cursor.getString(cursor.getColumnIndex("hot"));
                int steps = cursor.getInt(cursor.getColumnIndex("steps"));
                double hots = Double.parseDouble(hot);
                hot_values += hots;
                step += steps;
                Map<String , Object> map = new HashMap<>();
                String date_data = cursor.getString(cursor.getColumnIndex("date"));
                int step_data = cursor.getInt(cursor.getColumnIndex("steps"));
                String length_data = cursor.getString(cursor.getColumnIndex("length"));
                String hot_data = cursor.getString(cursor.getColumnIndex("hot"));

                map.put("date" , date_data);
                map.put("step" , step_data);
                map.put("length" , length_data);
                map.put("hot" , hot_data);
                Log.e("ss", date_data + "==" + step_data + "=="+length_data+"=="+hot_data+ "==");
                list.add(map);
            }*/
        }
        hot_values = hot_values + Double.parseDouble(SaveKeyValues.getStringValues("sport_heat","0.00"));
        hot_str = formatDouble(hot_values);
        step = step + SaveKeyValues.getIntValues("sport_steps",0);
        scores = (int) (step * 0.5);
    }

    @Override
    protected void initViews() {
        finish_plans = (TextView) findViewById(R.id.left_txt);
        keepfit_scores = (TextView) findViewById(R.id.sport_scores);
        sport_days = (TextView) findViewById(R.id.center_txt);
        sport_hot = (TextView) findViewById(R.id.right_txt);


        all_lin = (LinearLayout) findViewById(R.id.all_data);
        all_btn = (Button) findViewById(R.id.data_all);
        day_lin = (RelativeLayout) findViewById(R.id.day_data);
        day_btn = (Button) findViewById(R.id.data_day);
        dataList = (ListView) findViewById(R.id.data_list);
    }
    /**
     * 计算并格式化doubles数值，保留两位有效数字
     *
     * @param doubles
     * @return 返回当前路程
     */
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals("0") ? "0.00" : distanceStr;
    }
    @Override
    protected void setViewsListener() {
        all_btn.setOnClickListener(this);
        day_btn.setOnClickListener(this);
    }

    @Override
    protected void setViewsFunction() {
        all_lin.setVisibility(View.VISIBLE);
        day_lin.setVisibility(View.GONE);
        finish_plans.setText(String.valueOf(plans));
        sport_days.setText(String.valueOf(day_values));
        sport_hot.setText(hot_str);
        keepfit_scores.setText(String.valueOf(scores) +"分");
        //数据列表
        if (counts > 0){
            SimpleAdapter adapter = new SimpleAdapter(this , list,R.layout.step_item,
                    new String[]{"date","step","length","hot"},
                    new int[]{R.id.date , R.id.left_step,R.id.center_length,R.id.right_hot});
            dataList.setAdapter(adapter);

        }else {
            TextView empty = (TextView) findViewById(R.id.null_view);
            dataList.setEmptyView(empty);
            Toast.makeText(this,"当前没有数据！", Toast.LENGTH_SHORT).show();
        }
       // cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.data_all:
                all_lin.setVisibility(View.VISIBLE);
                day_lin.setVisibility(View.GONE);
                break;
            case R.id.data_day:
                all_lin.setVisibility(View.GONE);
                day_lin.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
