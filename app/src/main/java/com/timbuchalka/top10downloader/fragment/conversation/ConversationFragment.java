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
        return R.layout.list_row_conversation;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_conversation_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_conversation_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_conversation_update;
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
        public void fillData(View view) {
            active = (TextView) view.findViewById(R.id.active);
            consultantGroupUser = (TextView) view.findViewById(R.id.consultantGroupUser);
            customerInformation = (TextView) view.findViewById(R.id.customerInformation);
        }

        TextView active;
        TextView consultantGroupUser;
        TextView customerInformation;

        @Override
        public void setText(Conversation activeElement) {
            active.setText(activeElement.getActive() == 1 ? "Active" : "Inactive");
            consultantGroupUser.setText(activeElement.getConsultantGroupUser().getUser().getFirstName().concat("")
                    .concat(activeElement.getConsultantGroupUser().getUser().getLastName()));
            customerInformation.setText(activeElement.getCustomerInformation().getAdditionalInformation());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, Conversation currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
