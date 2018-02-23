package com.leonov.bsuir.fragment.customerayment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.leonov.bsuir.R;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.api.crud.PostData;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.CustomerPayment;

import java.util.ArrayList;
import java.util.Collection;

import com.leonov.bsuir.models.Conversation;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputListener;
import com.stripe.android.view.CardInputWidget;

public class CustomerPaymentUpdateFragment
        extends UpdateFragment<CustomerPayment> {
    private Conversation conversationActive;
    private TextView amount;
    private Spinner conversation;
    private ArrayList<Conversation> conversationData;
    CardInputWidget mCardInputWidget;
    EditText value;
    Button button;

    @SuppressLint("ValidFragment")
    CustomerPaymentUpdateFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentUpdateFragment(Class<CustomerPayment> genericClass, CustomerPayment activeElement, int layout) {
        super(genericClass, activeElement, layout);
        executeDropDowns();
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentUpdateFragment(Class<CustomerPayment> genericClass, int layout) {
        super(genericClass, layout);
        executeDropDowns();
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentUpdateFragment(Class<CustomerPayment> genericClass, int layout, Conversation conversationActive) {
        super(genericClass, layout);
        this.conversationActive = conversationActive;
        executeDropDowns();
    }

    private void executeDropDowns() {
        (new ListData<Conversation>(Conversation.class, new ListData.OnDataAvailable<Conversation>() {
            @Override
            public void onDataAvailable(Collection<Conversation> data, DownloadStatus status) {
                System.out.println("");
                conversationData = (ArrayList<Conversation>) data;
                ArrayList<String> list = new ArrayList<String>();

                for (Conversation u : data) {
                    list.add(u.getId().toString().concat(" ") +
                            u.getConsultantGroupUser().getUser().getFirstName().concat(" ") +
                            u.getConsultantGroupUser().getConsultantGroup().getName());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

                conversation.setAdapter(arrayAdapter);

                if (conversationActive != null) {
                    int i = 0;
                    for (Conversation u : data) {
                        if (conversationActive.getId() == u.getId()) {
                            conversation.setSelection(i);
                        }

                        i++;
                    }
                } else if (activeElement.getConversation() == null && !data.isEmpty()) {
                    conversation.setSelection(0);
                } else {
                    int i = 0;
                    for (Conversation u : data) {
                        if (activeElement.getConversation().getId() == u.getId()) {
                            conversation.setSelection(i);
                        }

                        i++;
                    }
                }

                conversation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        activeElement.setConversation(conversationData.get(i));
                        System.out.println("");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        })).execute();

    }

    @Override
    public void convertForView(CustomerPayment activeElement) {
        amount.setText(activeElement.getAmount().toString());

    }

    @Override
    public void convertForSubmit(CustomerPayment activeElement) {
        activeElement.setAmount(Long.parseLong(amount.getText().toString()));
        /* All in spinner */
    }

    @Override
    public void onClickListeners(View view) {


    }

    @Override
    public void setListeners() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card card = mCardInputWidget.getCard();
                System.out.println("df");

                if (card == null) {
                    return;
                }

                Stripe stripe = new Stripe(getContext(), "pk_test_1hb22cvnjrKI5FMaIsfo7lhf");
                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                PostData postData = new PostData(new PostData.OnDataAvailable() {
                                    @Override
                                    public void onDataAvailable(String data, DownloadStatus status) {
                                        System.out.println("Data available");
                                        if (data.equals("error")) {
                                            Toast.makeText(getContext(),
                                                    "Error!",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        } else {
                                            value.setText("");

                                            changeToListPage();

                                            Toast.makeText(getContext(),
                                                    "You successfuly payed!",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    }
                                }, "/customer_payment/save", "" + token.getId() +
                                        ":" + value.getText().toString() +
                                        ":" + activeElement.getConversation().getId().toString()
                                );
                                postData.execute();
                            }

                            public void onError(Exception error) {
                                // Show localized error message
                                Toast.makeText(getContext(),
                                        "Error!",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );
            }
        });
    }

    @Override
    public void findViewsById(View v) {
        amount = (TextView) v.findViewById(R.id.amount);
        conversation = (Spinner) v.findViewById(R.id.conversation);


        mCardInputWidget = (CardInputWidget) v.findViewById(R.id.card_input_widget);
        value = (EditText) v.findViewById(R.id.amount);
        button = (Button) v.findViewById(R.id.submit);

        //4242424242424242	Visa
        //4000056655665556	Visa (debit)
        //5555555555554444	Mastercard
        //2223003122003222	Mastercard (2-series)
        //5200828282828210	Mastercard (debit)
        //5105105105105100	Mastercard (prepaid)
        //378282246310005	American Express
        //371449635398431	American Express
        //6011111111111117	Discover
        //6011000990139424	Discover
        //30569309025904	Diners Club
        //38520000023237	Diners Club
        //3530111333300000	JCB
    }

    @Override
    public Fragment getListView() {
        return new CustomerPaymentFragment();
    }
}
