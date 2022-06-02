package com.example.finalproj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GuestHealthyAdapter extends RecyclerView.Adapter<GuestHealthyAdapter.ViewHolder> {

    public  interface MyRecyclerViewClickListener{
        void onBtnClicked(int position);
    }

    private  final List<Listitem_healthy> mDataList;
    public GuestHealthyAdapter(List<Listitem_healthy> datalist){ mDataList = datalist; }

    private MyRecyclerViewClickListener myRecyclerViewClickListener;
    public void setOnClickListener(MyRecyclerViewClickListener listener)
    {
        myRecyclerViewClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_healthy_item_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listitem_healthy item = mDataList.get(position);
        holder.tv_name.setText(item.getListitem_healthyname());
        holder.tv_count.setText(item.getListitem_healthycount());
        holder.tv_set.setText(item.getListitem_healthyset());
        holder.tv_healthyKG.setText(item.getListitem_KG());
        if(myRecyclerViewClickListener!=null){
            final int pos = position;
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRecyclerViewClickListener.onBtnClicked(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_count;
        TextView tv_set;
        TextView tv_healthyKG;
        Button btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.healthyname);
            tv_count = itemView.findViewById(R.id.healthycount);
            tv_set = itemView.findViewById(R.id.healthysetnum);
            tv_healthyKG = itemView.findViewById(R.id.healthyKG);
            btnEdit = itemView.findViewById(R.id.Btn_edit);

        }
    }

}
