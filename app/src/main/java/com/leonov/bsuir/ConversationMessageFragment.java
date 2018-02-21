package com.leonov.bsuir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.models.Conversation;
import com.leonov.bsuir.models.ConversationMessage;
import com.leonov.bsuir.models.Role;
import com.leonov.bsuir.models.User;

import java.util.Collection;

@SuppressLint("ValidFragment")
public class ConversationMessageFragment extends Fragment {
    private ListView listView;
    private Conversation conversation;

    @SuppressLint("ValidFragment")
    public ConversationMessageFragment(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.conversation_message_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.list_of_messages);

        (new ListData<ConversationMessage>(ConversationMessage.class, new ListData.OnDataAvailable<ConversationMessage>() {
            @Override
            public void onDataAvailable(Collection<ConversationMessage> data, DownloadStatus status) {
                MySimpleArrayAdapter feedAdapter = new MySimpleArrayAdapter(getContext(), data.toArray());
                listView.setAdapter(feedAdapter);
            }
        })).execute();
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<ConversationMessage> {
        private final Context context;
        private final Object[] values;

        public MySimpleArrayAdapter(Context context, Object[] values) {
            super(context, R.layout.conversation_message);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.conversation_message, parent, false);
            TextView user = (TextView) rowView.findViewById(R.id.message_user);
            TextView time = (TextView) rowView.findViewById(R.id.message_time);
            TextView text = (TextView) rowView.findViewById(R.id.message_text);

            ConversationMessage conversationMessage = (ConversationMessage) values[position];

            user.setText("".concat(
                    conversationMessage.getIsConsultantMessage().intValue() ==1 ?
                            conversationMessage.getConversation().getConsultantGroupUser().getUser().getFirstName():
                            conversationMessage.getConversation().getCustomerInformation().getUser().getFirstName()
            ));
            time.setText("".concat(conversationMessage.getDateTime().toString()));
            text.setText("".concat(conversationMessage.getMessage()));

            return rowView;
        }
    }
}


















