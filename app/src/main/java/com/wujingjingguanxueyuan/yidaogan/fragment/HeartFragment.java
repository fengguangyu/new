package com.wujingjingguanxueyuan.yidaogan.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wujingjingguanxueyuan.yidaogan.activity.WeatherMain;
import com.wujingjingguanxueyuan.yidaogan.activity.WhereActivity;
import com.wujingjingguanxueyuan.yidaogan.base.BaseFragment;

import com.wujingjingguanxueyuan.yidaogan.R;

/**
 * 心率测试
 * 建议上网查询相关资料
 * 此处就是根据相关资料-->GitHub上的开源项目
 * (地址：https://github.com/phishman3579/android-heart-rate-monitor)
 * 进行修改而来的
 * 思路就是打开摄像头并打开闪关灯
 * 用手指放在在镜头出，随后对surfaceview
 * 中显示的图像进行处理
 * 随后得出心率的数值
 * 该功能具有一定的科学依据
 * 如在图像处理上具有基础的可以研究一下
 * 如果没有先关基础的参考下功能就好
 * 不必深究
 * 这里用到的图标引擎libs包中都已提供
 * Created by Administrator on 2016/5/27.
 */
public class HeartFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Button button;
    private Button button2;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_heart,null);
        initView();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                startActivity(new Intent(context, WhereActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(context, WeatherMain.class));
                break;
            default:
                break;
        }


    }

    private void initView() {
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

}

