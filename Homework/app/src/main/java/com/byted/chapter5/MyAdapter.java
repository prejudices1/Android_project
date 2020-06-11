package com.byted.chapter5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<ArticleResponse> mDataset;
    private Context mcontext;
    private  final ListItemClickListener mOnClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public MyAdapter(ListItemClickListener listener){
        mOnClickListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        // each data item is just a string in this case
        public TextView likecount;
        public TextView nickname;
        public ImageView imageView;
        public ImageView avator;
        public ImageView good;

        public MyViewHolder(@NonNull View v) {
            super(v);
            likecount = v.findViewById(R.id.likecount);
            nickname = v.findViewById(R.id.nickname);
            imageView=v.findViewById(R.id.image1);
            avator=v.findViewById(R.id.avator);
            good=v.findViewById(R.id.good);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }


    public void setData(Context context,List<ArticleResponse> myDataset) {
        mcontext=context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String url ="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3875642194,2749727726&fm=11&gp=0.jpg";//点赞的图片地址
        holder.likecount.setText("" + mDataset.get(position).likecount);
        holder.nickname.setText(mDataset.get(position).nickname);
        Glide.with(mcontext).load(mDataset.get(position).feedurl).into(holder.imageView);
        Glide.with(mcontext).load(mDataset.get(position).avatar).into(holder.avator);
        Glide.with(mcontext).load(url).into(holder.good);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

}
