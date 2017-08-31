package com.example.zhanggang.zhanggang831recyclerviewfenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：适配器
 * 时  间：2017/8/31 - 14:06.
 * 创建人：张刚
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private Context context;

    public MyAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
//            //整个子条目点击事件
//            mholder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onClickLinstener!=null){
//                        onClickLinstener.setOnClick(view,position);
//                    }
//                }
//            });
//            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mholder.textView, "translationX", -200f, 200f, 1f);
//            objectAnimator.setDuration(2000);
//            objectAnimator.start();
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //内部类viewholder
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview)
        TextView textView;
        @BindView(R.id.image)
        ImageView imageView;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
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
