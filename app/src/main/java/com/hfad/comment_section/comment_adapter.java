package com.hfad.comment_section;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class comment_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private List<String> comments_list;
    private List<String> name_list;
    private comment_listener comment_listener;

    public comment_adapter(Context context,comment_listener comment_listener, List<String> comments_list,List<String> name_list) {
        this.context = context;
        this.comments_list = comments_list;
        this.comment_listener = comment_listener;
        this.name_list = name_list;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.activity_comment_card;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder;
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.activity_comment_card,parent,false);
        holder = new CommentHolder(view,comment_listener);

        return holder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommentHolder)
        {
            ((CommentHolder) holder).user_comment.setText(comments_list.get(position));
            ((CommentHolder) holder).user_name.setText(name_list.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return comments_list.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView user_comment;
        public TextView user_name;
        comment_listener comment_listener;

        public CommentHolder(@NonNull View itemView,comment_listener image_listener) {
            super(itemView);
            user_comment = itemView.findViewById(R.id.user_comment);
            user_name = itemView.findViewById(R.id.user_name);
            this.comment_listener = image_listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            comment_listener.comment_click(getAdapterPosition());
        }

    }



    public interface comment_listener
    {
        void comment_click(int position);
    }





}
