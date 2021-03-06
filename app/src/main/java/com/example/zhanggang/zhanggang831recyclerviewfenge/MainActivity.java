package com.example.zhanggang.zhanggang831recyclerviewfenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：主类  设置Recyclerview适配器  和添加分割线
 * 时  间：2017/8/31 - 14:05.
 * 创建人：张刚
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    private MyAdapter adapter;

    @BindView(R.id.quanxuan)TextView quanxuan;
    @BindView(R.id.fanxuan)TextView fanxuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加分割线
        MyDecoration decoration = new MyDecoration(this,LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        //设置适配器
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        //点击事件
        adapter.setOnClickLinstener(new MyAdapter.onClickLinstener() {
            @Override
            public void setOnClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
                //单机添加数据
                adapter.add(position);
            }
        });
        //长按事件
        adapter.setOnLongClickLinstener(new MyAdapter.onLongClickLinstener() {
            @Override
            public void setOnLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "长按", Toast.LENGTH_SHORT).show();
                //单机删除数据
//                adapter.remove(position);
                adapter.updata(position);

            }
        });
        //全选
        quanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.selectedAll();
            }
        });
        //反选
        fanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.selectedRevert();
            }
        });


    }
}
