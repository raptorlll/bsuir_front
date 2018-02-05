package com.timbuchalka.top10downloader.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantInformation;


public class ConsultantInformationFragment
        extends CrudFragment<ConsultantInformation> {
    @Override
    protected int getLayoutList(){
        return R.layout.list_row_consultant_information;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_consultant_information_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_consultant_information_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_consultant_information_update;
    }

    public ConsultantInformationFragment() {
        super(ConsultantInformation.class);
    }

    @SuppressLint("ValidFragment")
    public ConsultantInformationFragment(Class<ConsultantInformation> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<ConsultantInformation> getCreateFragment() {
        return new ConsultantInformationUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<ConsultantInformation> getUpdateFragment(ConsultantInformation activeElement) {
        return new ConsultantInformationUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(ConsultantInformation activeElement) {
        return new ConsultantInformationReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<ConsultantInformation> {
        @Override
        public void fillData(View v) {
            this.status = (TextView) v.findViewById(R.id.status);
            this.videoTarif = (TextView) v.findViewById(R.id.videoTarif);
            this.conversationTarif = (TextView) v.findViewById(R.id.conversationTarif);
            this.user = (TextView) v.findViewById(R.id.user);
            this.consultantGroup = (TextView) v.findViewById(R.id.consultantGroup);
        }

        private TextView status;
        private TextView videoTarif;
        private TextView conversationTarif;
        private TextView user;
        private TextView consultantGroup;

        @Override
        public void setText(ConsultantInformation currentElement){
            status.setText(currentElement.getStatus()==0 ? "Active" : "Inactive");
            videoTarif.setText(currentElement.getVideoTarif() != null ? currentElement.getVideoTarif().toString() : "");
            conversationTarif.setText(currentElement.getConversationTarif() != null ? currentElement.getConversationTarif().toString() : "");
            user.setText(currentElement.getUser().getFirstName());
            consultantGroup.setText(currentElement.getConsultantGroup().getName());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, ConsultantInformation currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();

        return viewHolder;
    }
}
