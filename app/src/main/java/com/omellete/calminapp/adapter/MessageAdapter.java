package com.omellete.calminapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.omellete.calminapp.R;
import com.omellete.calminapp.model.AllMethods;
import com.omellete.calminapp.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    Context context;
    List<Message> messages;
    DatabaseReference reference;

    public MessageAdapter(Context context, List<Message> messages, DatabaseReference reference){
        this.context = context;
        this.messages = messages;
        this.reference = reference;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message,parent,false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        Message message = messages.get(position);

        if (message.getName().equals(AllMethods.name)){
            holder.sender.setText("You");
            holder.tvTittle.setText(message.getMessage());
            holder.tvTittle.setGravity(Gravity.START);
            holder.sender.setTextColor(context.getResources().getColorStateList(R.color.myChatTextName));
            holder.tvTittle.setTextColor(context.getResources().getColorStateList(R.color.myChatText));
            holder.cardView.setBackgroundTintList(context.getResources().getColorStateList(R.color.myChat));
        }else{
            holder.sender.setText(message.getName());
            holder.tvTittle.setText(message.getMessage());
            holder.ibDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvTittle,sender;
        ImageButton ibDelete;
        CardView cardView;

        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            ibDelete = itemView.findViewById(R.id.btnDel);
            cardView = itemView.findViewById(R.id.cardView);
            sender = itemView.findViewById(R.id.senderName);

            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reference.child(messages.get(getAdapterPosition()).getKey()).removeValue();
                }
            });
        }
    }
}
