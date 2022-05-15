package com.example.finalproj;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserEditListAdapter extends RecyclerView.Adapter<UserEditListAdapter.ViewHolder>{

    public  interface MyRecyclerViewClickListener{
        void onBtnClicked(int position);
    }
    private  final List<ListItem> mDataList;
    public UserEditListAdapter(List<ListItem> datalist){
        mDataList = datalist;
    }

    private MyRecyclerViewClickListener myRecyclerViewClickListener;
    public void setOnClickListener(MyRecyclerViewClickListener listener)
    {
        myRecyclerViewClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_useredit_listview_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem item = mDataList.get(position);
        holder.tv_name.setText(item.getListitem_name());
        holder.tv_userAuth.setText(item.getListitem_userauth());
        holder.tv_term.setText(item.getListitem_term());
        holder.tv_lostterm.setText(item.getListitem_lostterm());

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
        TextView tv_userAuth;
        TextView tv_term;
        TextView tv_lostterm;
        Button btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.text_Name);
            tv_userAuth = itemView.findViewById(R.id.text_userAuth);
            tv_term = itemView.findViewById(R.id.text_term);
            tv_lostterm = itemView.findViewById(R.id.text_lostterm);
            btnEdit = itemView.findViewById(R.id.Btn_change);

        }
    }
}
