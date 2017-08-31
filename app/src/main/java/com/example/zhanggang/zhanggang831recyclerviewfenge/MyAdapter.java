package com.example.zhanggang.zhanggang831recyclerviewfenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：适配器
 * 时  间：2017/8/31 - 14:06.
 * 创建人：张刚
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<String> list;
    HashMap<Integer, Boolean> hashMap;

    public MyAdapter() {
        hashMap = new HashMap<>();
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {  //测试数据
            list.add("条目" + i);
            hashMap.put(i, false);  //key为当前数据，value为设置不选中
        }
    }

    //接口监听
    onClickLinstener onClickLinstener;
    onLongClickLinstener onLongClickLinstener;

    public void setOnClickLinstener(MyAdapter.onClickLinstener onClickLinstener) {
        this.onClickLinstener = onClickLinstener;
    }

    public void setOnLongClickLinstener(MyAdapter.onLongClickLinstener onLongClickLinstener) {
        this.onLongClickLinstener = onLongClickLinstener;
    }

    //初始化视图
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(inflate);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder mholder = (ViewHolder) holder;
            mholder.textView.setText(list.get(position));
            //图片点击事件
            mholder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickLinstener != null) {
                        onClickLinstener.setOnClick(view, position);
                    }
                }
            });
            //图片长按事件
            mholder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onLongClickLinstener != null) {
                        onLongClickLinstener.setOnLongClick(view, position);
                    }
                    return true;
                }
            });
            //针对checkbox错乱问题，只操作数据不操作checkbox，在数据改变之后，再去刷新
            mholder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //1.当前的数据   2.取当前数据状态的反
                    hashMap.put(position, !hashMap.get(position));
                    notifyDataSetChanged();
                }
            });
            //将当前的状态设置给checkbox
            mholder.checkBox.setChecked(hashMap.get(position));
//            //整个子条目点击事件
//            mholder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onClickLinstener!=null){
//                        onClickLinstener.setOnClick(view,position);
//                    }
//                }
//            });       m
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //内部类viewholder
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox) CheckBox checkBox;
        @BindView(R.id.textview) TextView textView;
        @BindView(R.id.image) ImageView imageView;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    //全选
    public void selectedAll() {
        Set<Map.Entry<Integer, Boolean>> entries = hashMap.entrySet();  //得到集合
        //如果有未选中的就去全部选中
        boolean isSelected = false;
        //判断要选中还是不选中
        for (Map.Entry<Integer, Boolean> bean : entries) {
            Boolean value = bean.getValue();
            //如果有没选中的，就全选中
            if (!value){
                isSelected=true;
                break;
            }
        }
        //如果isSelected为true说明全部选中了，false全未选中
        for (Map.Entry<Integer, Boolean> bean : entries) {
            bean.setValue(isSelected);
        }
        notifyDataSetChanged();
    }

    //反选
    public void selectedRevert() {
        Set<Map.Entry<Integer, Boolean>> entries = hashMap.entrySet();  //得到集合
        //把状态设置成它的反
        for (Map.Entry<Integer, Boolean> bean: entries) {
            bean.setValue(!bean.getValue());
        }
        notifyDataSetChanged();
    }

    //添加一条数据
    public void add(int position) {
        list.add(position + 1, "这是新加的数据");
        notifyItemRangeChanged(position + 1, getItemCount());
    }

    //删除一条数据
    public void remove(int position) {
        list.remove(position);
        notifyItemRangeRemoved(position, getItemCount());
    }

    //更新一条数据
    public void updata(int position) {
        list.remove(position);
        list.add(position, "这是更新的数据");
        notifyItemInserted(position);
    }

    //定义点击接口
    public interface onClickLinstener {
        void setOnClick(View view, int position);
    }

    //定义长按接口
    public interface onLongClickLinstener {
        void setOnLongClick(View view, int position);
    }
}
