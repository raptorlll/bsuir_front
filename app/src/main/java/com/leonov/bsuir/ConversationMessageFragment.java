package com.leonov.bsuir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.CreateData;
import com.leonov.bsuir.api.crud.GetData;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.api.crud.PostData;
import com.leonov.bsuir.models.Conversation;
import com.leonov.bsuir.models.ConversationMessage;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ValidFragment")
public class ConversationMessageFragment extends Fragment{
    private ListView listView;
    private Button addButton;
    private EditText textField;
    private Conversation conversation;
    private Button close;
    private Button viewStatus;

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
        addButton = (Button) view.findViewById(R.id.fab);
        textField = (EditText) view.findViewById(R.id.input);
        close = (Button) view.findViewById(R.id.close);
        viewStatus = (Button) view.findViewById(R.id.viewStatus);

        callAsynchronousTask();
        updateView();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("");
                PostData postData = new PostData(new PostData.OnDataAvailable() {
                    @Override
                    public void onDataAvailable(String data, DownloadStatus status) {
                        System.out.println("closed");
                    }
                }, "/conversation/close/"+ conversation.getId().toString());
                postData.execute();
            }
        });

        viewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("");
                GetData getData = new GetData(new GetData.OnDataAvailable() {
                    @Override
                    public void onDataAvailable(String data, DownloadStatus status) {
                        System.out.println("closed");
                        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                        b.setMessage(data);
                        b.setCancelable(false);
                        b.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        b.setTitle("Statuses");
                        AlertDialog ad = b.create();
                        ad.show();
                    }
                }, "/conversation/statuses/"+ conversation.getId().toString());
                getData.execute();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConversationMessage conversationMessage = new ConversationMessage();
                conversationMessage.setMessage(textField.getText().toString());
                textField.setText("");
                conversationMessage.setConversation(conversation);

                CreateData<ConversationMessage> conversationMessageCreateData =
                        new CreateData<ConversationMessage>(ConversationMessage.class, new CreateData.OnDataAvailable<ConversationMessage>() {
                            @Override
                            public void onDataAvailable(ConversationMessage data, DownloadStatus status) {
                                updateView();
                            }
                        }, conversationMessage);
                conversationMessageCreateData.execute();

                System.out.println("Clicked");
            }
        });
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            updateView();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //execute in every 50000 ms
    }
    private void updateView() {
        (new ListData<ConversationMessage>(ConversationMessage.class, new ListData.OnDataAvailable<ConversationMessage>() {
            @Override
            public void onDataAvailable(Collection<ConversationMessage> data, DownloadStatus status) {
                MySimpleArrayAdapter feedAdapter = new MySimpleArrayAdapter(getContext(), data.toArray());
                listView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        }, "/conversation_message/conversation/" + conversation.getId().toString())).execute();
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

            String dateString = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US).format(conversationMessage.getDateTime());

            user.setText("".concat(
                    conversationMessage.getIsConsultantMessage().intValue() ==1 ?
                            conversationMessage.getConversation().getConsultantGroupUser().getUser().getFirstName():
                            conversationMessage.getConversation().getCustomerInformation().getUser().getFirstName()
            ));
            time.setText("".concat(dateString));
            text.setText("".concat(conversationMessage.getMessage()));

            return rowView;
        }
    }
}


















