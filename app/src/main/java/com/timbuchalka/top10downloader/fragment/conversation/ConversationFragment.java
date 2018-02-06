package com.timbuchalka.top10downloader.fragment.conversation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.Conversation;

import java.text.SimpleDateFormat;


public class ConversationFragment
        extends CrudFragment<Conversation> {

    @Override
    protected int getLayoutList(){
        return R.layout.list_row_customer_information;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_customer_information_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_customer_information_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_customer_information_update;
    }

    public ConversationFragment() {
        super(Conversation.class);
    }


    @SuppressLint("ValidFragment")
    public ConversationFragment(Class<Conversation> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<Conversation> getCreateFragment() {
        return new ConversationUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<Conversation> getUpdateFragment(Conversation activeElement) {
        return new ConversationUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(Conversation activeElement) {
        return new ConversationReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<Conversation> {
        @Override
        public void fillData(View v) {
            this.birthData = (TextView) v.findViewById(R.id.birthData);
            this.additionalInformation = (TextView) v.findViewById(R.id.additionalInformation);
            this.primary = (TextView) v.findViewById(R.id.primary);
        }
        private TextView birthData;
        private TextView additionalInformation;
        private TextView primary;

        @Override
        public void setText(Conversation currentElement) {
            birthData.setText(new SimpleDateFormat("Y-m-d").format(currentElement.getBirthData()).concat(" birth date"));
            additionalInformation.setText(currentElement.getAdditionalInformation());
            primary.setText(currentElement.getPrimary() == 0 ? "Secondary" : "Primary");
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, Conversation currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
