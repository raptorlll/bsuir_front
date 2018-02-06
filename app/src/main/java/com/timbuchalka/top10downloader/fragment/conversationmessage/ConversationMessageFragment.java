package com.timbuchalka.top10downloader.fragment.conversationmessage;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConversationMessage;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class ConversationMessageFragment
        extends CrudFragment<ConversationMessage> {
    @Override
    protected int getLayoutList(){
        return R.layout.list_row_customer_payment;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_customer_payment_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_customer_payment_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_customer_payment_update;
    }

    public ConversationMessageFragment() {
        super(ConversationMessage.class);
    }

    @SuppressLint("ValidFragment")
    public ConversationMessageFragment(Class<ConversationMessage> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<ConversationMessage> getCreateFragment() {
        return new ConversationMessageUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<ConversationMessage> getUpdateFragment(ConversationMessage activeElement) {
        return new ConversationMessageUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(ConversationMessage activeElement) {
        return new ConversationMessageReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<ConversationMessage> {
        @Override
        public void fillData(View v) {
            message = (TextView) v.findViewById(R.id.message);
            attachedFile = (TextView) v.findViewById(R.id.attachedFile);
        }

        private TextView message;
        private TextView attachedFile;

        @Override
        public void setText(ConversationMessage currentElement) {
            message.setText(currentElement.getMessage());
            attachedFile.setText(currentElement.getAttachedFile().toString());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, ConversationMessage currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
