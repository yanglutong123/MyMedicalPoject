package com.yang.mymedicalpoject.fragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mvplibrary.base.fragment.BaseMvpFragment;
import com.google.android.material.tabs.TabLayout;
import com.yang.mymedicalpoject.R;
import com.yang.mymedicalpoject.base.MyApp;
import com.yang.mymedicalpoject.base.adapter.PagerAdapter;
import com.yang.mymedicalpoject.fragment.homefragment.Fragment2;
import com.yang.mymedicalpoject.fragment.homefragment.HomeConstants;
import com.yang.mymedicalpoject.fragment.homefragment.bean.HomeBean;
import com.yang.mymedicalpoject.fragment.homefragment.model.HomeModel;
import com.yang.mymedicalpoject.fragment.homefragment.presenter.HomePresenter;
import com.yang.mymedicalpoject.ui.activity.HomeLiNianActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseMvpFragment<HomeConstants.MyHomeView, HomeModel, HomePresenter> implements HomeConstants.MyHomeView {

    private TabLayout tab;
    private ViewPager mVp;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> str;
    private RadioGroup rb_group1;
    //历年考试
    private RadioButton rb_3;
    //模拟考试
    private RadioButton rb_4;
    //收藏
    private RadioButton rb_6;

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    protected int creatLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initDataMvp(View view) {
        tab = view.findViewById(R.id.tab_channel);
        mVp = view.findViewById(R.id.viewPager);
        rb_3 = view.findViewById(R.id.rb_3);
        rb_4 = view.findViewById(R.id.rb_4);
        rb_6 = view.findViewById(R.id.rb_6);
        rb_group1 = view.findViewById(R.id.rb_Group1);

        initClick();

        //网络请求
        initData();
    }

    private void initClick() {
        rb_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_3:
                        //选中跳转到activity
                        MyApp.openActivity(getContext(), HomeLiNianActivity.class);
                        rb_3.setChecked(false);
                        break;
                        case R.id.rb_4:

                        break;
                        case R.id.rb_6:{

                        }
                        break;
                    default:
                }
            }
        });
    }

    private void initData() {
        //加载网络请求
        mPresenter.getData();
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }


    @Override
    public void showData(HomeBean homeBean) {
        Log.i("杨路通", "HomeFragment: "+"llll");
        fragments = new ArrayList<>();
        str=new ArrayList<>();
        List<HomeBean.UTypeBean> arrayList = homeBean.getU_type();
        //获取回数据以后设置tab
        for (int i = 0; i <arrayList.size() ; i++) {
            ArrayList<HomeBean.UTypeBean.ZTypeBean> z_type = (ArrayList<HomeBean.UTypeBean.ZTypeBean>) homeBean.getU_type().get(i).getZ_type();
            str.add(arrayList.get(i).getName());
                tab.addTab(tab.newTab());
            Fragment2 fragment2 = new Fragment2();
                fragments.add(fragment2);

            Bundle bundle = new Bundle();
           bundle.putSerializable("list",  z_type);
            fragment2.setArguments(bundle);
        }


        //设置ViewPager+Fragment
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), str,fragments);
        mVp.setAdapter(adapter);
        tab.setupWithViewPager(mVp);
    }

    @Override
    public void error(String msg, int code) {
        Log.i("杨路通", "error: "+msg+code);
    }
}