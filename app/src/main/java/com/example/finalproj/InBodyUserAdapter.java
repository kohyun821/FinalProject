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

public class InBodyUserAdapter extends RecyclerView.Adapter<InBodyUserAdapter.InBodyUserVH>{

    private Context context;

    ArrayList<InBodyUser> list = new ArrayList<>();

    public InBodyUserAdapter(Context context, ArrayList<InBodyUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InBodyUserVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

        return new InBodyUserVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InBodyUserVH holder, int position) {
        InBodyUser user = list.get(holder.getBindingAdapterPosition());

        holder.userifm.setText(user.getUserifm());
        holder.userifm3.setText(user.getUserifm3());
        holder.userifm2.setText(user.getUserifm2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InBodyUserVH extends RecyclerView.ViewHolder{

        TextView userifm;
        TextView userifm3;
        TextView userifm2;

        CardView cardview;

        public InBodyUserVH(@NonNull View itemView) {
            super(itemView);

            userifm = itemView.findViewById(R.id.editTextDate);
            userifm3 = itemView.findViewById(R.id.editTextDate3);
            userifm2 = itemView.findViewById(R.id.editTextDate2);

            cardview = itemView.findViewById(R.id.card_View);
        }

        public int getBindingAdapterPosition() {
            return list.size();
        }
    }
}
