package com.leonov.bsuir;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.CreateData;
import com.leonov.bsuir.api.crud.GetData;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.api.crud.PostData;
import com.leonov.bsuir.fragment.conversation.ConversationFragment;
import com.leonov.bsuir.models.Conversation;
import com.leonov.bsuir.models.ConversationMessage;
import com.leonov.bsuir.models.CustomerPayment;
import com.leonov.bsuir.statical.RolesChecker;
import com.leonov.bsuir.video.CallScreenActivity;
import com.leonov.bsuir.video.SinchService;
import com.sinch.android.rtc.calling.Call;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;

@SuppressLint("ValidFragment")
public class ConversationMessageFragment extends Fragment{
    private ListView listView;
    private Button addButton;
    private EditText textField;
    private Conversation conversation;
    private Button close;
    private Button viewStatus;
    private Button payment;
    private Button video;

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
        payment = (Button) view.findViewById(R.id.payments);
        video = (Button) view.findViewById(R.id.video);

        callAsynchronousTask();
        updateView();

        if(RolesChecker.getInstance().isConsultant()){
            close.setEnabled(false);
            video.setEnabled(false);
        }

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

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentMain, new ConversationPaymentsFragment(conversation));
                ft.commit();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ListData<CustomerPayment>(CustomerPayment.class, new ListData.OnDataAvailable<CustomerPayment>() {
                    @Override
                    public void onDataAvailable(Collection<CustomerPayment> data, DownloadStatus status) {
                        if (conversation.canPost(data)) {
                            final ConversationMessage conversationMessage = new ConversationMessage();
                            conversationMessage.setMessage(textField.getText().toString());
                            textField.setText("");
                            conversationMessage.setConversation(conversation);

                            sendMessage(conversationMessage);
                        } else {
                            Toast.makeText(getContext(),
                                    "Is not enough money. You need " + conversation.needAtLeast(data),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                }, "/customer_payment/conversation/" + conversation.getId().toString())).execute();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (new ListData<CustomerPayment>(CustomerPayment.class, new ListData.OnDataAvailable<CustomerPayment>() {
                    @Override
                    public void onDataAvailable(Collection<CustomerPayment> data, DownloadStatus status) {
                        if (conversation.canPost(data)) {
                            final ConversationMessage conversationMessage = new ConversationMessage();
                            conversationMessage.setMessage(textField.getText().toString());
                            textField.setText("");
                            conversationMessage.setConversation(conversation);

                            callVideo();
                        } else {
                            Toast.makeText(getContext(),
                                    "Is not enough money. You need " + conversation.needAtLeast(data),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                }, "/customer_payment/conversation/" + conversation.getId().toString())).execute();
            }
        });
    }

    private void callVideo() {
        System.out.println("debug");

        Call call = ((BaseAuthActivity) getActivity()).getSinchServiceInterface().callUserVideo(
                conversation.getConsultantGroupUser().getUser().getUsername(),
                new SinchService.CallEnded() {
                    @Override
                    public void callEnded(int duration) {
                        final ConversationMessage conversationMessage = new ConversationMessage();
                        conversationMessage.setMessage(textField.getText().toString());
                        textField.setText("");
                        conversationMessage.setConversation(conversation);
                        Time time = new Time(0, 0, duration);
                        conversationMessage.setVideoDuration(time);

                        sendMessage(conversationMessage);

                        System.out.println("ended. Duration " + duration);
                    }
                }
        );
        String callId = call.getCallId();

        Intent callScreen = new Intent(getContext(), CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }

    void sendMessage(ConversationMessage conversationMessage){
        CreateData<ConversationMessage> conversationMessageCreateData =
                new CreateData<ConversationMessage>(ConversationMessage.class, new CreateData.OnDataAvailable<ConversationMessage>() {
                    @Override
                    public void onDataAvailable(ConversationMessage data, DownloadStatus status) {
                        conversation.setMessagesCount(conversation.getMessagesCount() + 1);
                        updateView();
                    }
                }, conversationMessage);
        conversationMessageCreateData.execute();

        System.out.println("Clicked");
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
                conversation.setConversationMessages(data);
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
            text.setText(conversationMessage.getVideoDuration() == null ?
                    conversationMessage.getMessage() :
                    "--- Video message. Long - ".concat(
                            new SimpleDateFormat("HH:mm:ss", Locale.US).format(conversationMessage.getVideoDuration())
                    ).concat(" s.")
            );

            return rowView;
        }
    }
}


















