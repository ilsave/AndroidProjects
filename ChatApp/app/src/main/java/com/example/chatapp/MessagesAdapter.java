package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private static final int TYPE_MY_MESSAGE = 0;
    public static final int  TYPE_OTHER_MESSAGE = 1;

    private Context context;

    private List<Message> messages;

    public MessagesAdapter(Context context){
        messages = new ArrayList<>();
        this.context = context;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }


    //создаем view из нашего макета
    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i== TYPE_MY_MESSAGE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_my_message, viewGroup, false);
        }else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_other_message, viewGroup, false);
        }
        return new MessagesViewHolder(view);
    }

    /**
     * Return the view type of the item at <code>position</code> for the purposes
     * of view recycling.
     *
     * <p>The default implementation of this method returns 0, making the assumption of
     * a single view type for the adapter. Unlike ListView adapters, types need not
     * be contiguous. Consider using id resources to uniquely identify item view types.
     *
     * @param position position to query
     * @return integer value identifying the type of the view needed to represent the item at
     * <code>position</code>. Type codes need not be contiguous.
     */
    //чтобы сообщения от разных пользователей были разного типа используем вот этот метод
    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        if (author != null && author.equals(PreferenceManager.getDefaultSharedPreferences(context).getString("author","Anonim" ))){
            return TYPE_MY_MESSAGE;
        }else {
            return  TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder messagesViewHolder, int i) {
        Message message = messages.get(i);
        String author = message.getAuthor();
        String textOfMessage = message.getTextOfMessage();
        messagesViewHolder.textViewAuthor.setText(messages.get(i).getAuthor());
        String urlToImage = message.getImageUrl();
        if (textOfMessage != null && !textOfMessage.isEmpty()) {
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.VISIBLE);
            messagesViewHolder.textViewTextOfMessage.setText(textOfMessage);
            //messagesViewHolder.imageViewImage.setVisibility(View.GONE);
            //если сообщение пришло с сервера и там нет картинки то мы ее просто не показываем
        }else{
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.GONE);
        }
        if (urlToImage !=  null && !urlToImage.isEmpty()){
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.GONE);
            messagesViewHolder.imageViewImage.setVisibility(View.VISIBLE);
            Picasso.get().load(urlToImage).into(messagesViewHolder.imageViewImage);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewAuthor;
        private TextView textViewTextOfMessage;
        private ImageView imageViewImage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTextOfMessage = itemView.findViewById(R.id.textViewTextOfMessage);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);

        }
    }
}
