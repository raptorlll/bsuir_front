package com.leonov.bsuir.fragment.conversation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov.bsuir.ConversationMessageFragment;
import com.leonov.bsuir.MainFragment;
import com.leonov.bsuir.R;
import com.leonov.bsuir.adapters.CrudInformationAdapter;
import com.leonov.bsuir.fragment.crud.CrudFragment;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.Conversation;

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
        ConversationUpdateFragment conversationUpdateFragment = new ConversationUpdateFragment(genericClass, activeElement, getLayoutUpdate());
        return conversationUpdateFragment;
    }

    @Override
    public ReadFragment getReadFragment(Conversation activeElement) {
        return new ConversationReadFragment(activeElement, getLayoutView());
    }

    @Override
    protected void attacheListListeners(ListView listView) {
        super.attacheListListeners(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Conversation c = ((CrudInformationAdapter<Conversation>) ((ListView) adapterView).getAdapter()).getElementsList().get(0);

                FragmentTransaction ft;
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentMain, new ConversationMessageFragment(c));
                ft.commit();

                System.out.println("Click");
            }
        });
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
            active.setText("Status : " + (activeElement.getActive() == 1 ? "Active" : "Inactive"));
            consultantGroupUser.setText("Consultant : " +activeElement.getConsultantGroupUser().getUser().getFirstName().concat(" ")
                    .concat(activeElement.getConsultantGroupUser().getConsultantGroup().getName()));
            customerInformation.setText("Customer : " + activeElement.getCustomerInformation().getUser().getFirstName() +
                    " " + activeElement.getCustomerInformation().getAdditionalInformation());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, Conversation currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
