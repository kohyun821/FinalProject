package com.example.finalproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH>{

    private Context context;

    ArrayList<UserInformation> liist = new ArrayList<>();

    public UserAdapter(Context context, ArrayList<InBodyUser> list) {
        this.context = context;
        this.liist = liist;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return liist.size();
    }

    class UserVH extends RecyclerView.ViewHolder{

        TextView userName;
        TextView userNumber;


        public UserVH(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.Read_Name);
            userNumber = itemView.findViewById(R.id.Read_Number);
        }
    }

}
