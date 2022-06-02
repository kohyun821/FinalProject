package com.example.finalproj;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

//RecyclerView.Adapter< ? .ViewHolder>
 public class Dialog_Healthy_Adapter extends RecyclerView.Adapter<Dialog_Healthy_Adapter.ViewHolder> {


    public  interface MyRecyclerViewClickListener{
        void onItemClicked(int position);
    }


    //<> 안에 수정
    private  final List<HealthyList> mDataList;
    //<>안, 변수명 수정
    public Dialog_Healthy_Adapter(List<HealthyList> datalist){ mDataList = datalist; }

    //변수명 수정
    private MyRecyclerViewClickListener myRecyclerViewClickListener;

    //.앞에 수정
    public void setOnClickListener(MyRecyclerViewClickListener listener)
    {
        myRecyclerViewClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                //layout. 뒤에 아이템 리스트 (xml 파일) 로 수정
                .inflate(R.layout.healthy_recyclerview_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthyList item = mDataList.get(position);

//        holder.imageView.setImageResource(R.drawable.test_img);
        Glide.with(holder.itemView).load(item.getImg_uri()).into(holder.imageView);
        Log.d("체크",item.getImg_uri());
        holder.tv_name.setText(item.getHealthyList_name());
        if(myRecyclerViewClickListener!=null){
            final int pos = position;
            //어떤게 클릭
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRecyclerViewClickListener.onItemClicked(pos);
                    Log.d("클릭","리사이클러 아이템 클릭");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hr_list_item_imageview);
            tv_name = itemView.findViewById(R.id.hr_list_item_textview);

        }
    }

}