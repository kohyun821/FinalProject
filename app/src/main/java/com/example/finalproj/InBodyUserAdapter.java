package com.example.finalproj;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class InBodyUserAdapter extends RecyclerView.Adapter<InBodyUserAdapter.ViewHolder>{


    //<> 안에 수정
    private  final List<InBodyUser> mDataList;
    //<>안, 변수명 수정
    public InBodyUserAdapter(List<InBodyUser> datalist){ mDataList = datalist; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                //layout. 뒤에 아이템 리스트 (xml 파일) 로 수정
                .inflate(R.layout.inbody_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InBodyUser item = mDataList.get(position);

        holder.tv_date.setText(item.getdate());
        holder.tv_fat.setText(item.getfat());
        holder.tv_fatmass.setText(item.getfat_mass());
        holder.tv_skeletalmass.setText(item.getskeletal_mass());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_fat;
        TextView tv_fatmass;
        TextView tv_skeletalmass;
        TextView tv_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_fat = itemView.findViewById(R.id.Inbody_tv_fat);
            tv_fatmass = itemView.findViewById(R.id.Inbody_tv_fatmass);
            tv_skeletalmass = itemView.findViewById(R.id.Inbody_tv_skeletalmass);
            tv_date = itemView.findViewById(R.id.Inbody_tv_date);

        }
    }

}
