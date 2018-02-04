package com.timbuchalka.top10downloader.fragment.consultantgroupuser;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.consultantgroup.ConsultantGroupReadFragment;
import com.timbuchalka.top10downloader.fragment.consultantgroup.ConsultantGroupUpdateFragment;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;


public class ConsultantGroupUserFragment
        extends CrudFragment<ConsultantGroupUser> {
    @Override
    protected int getLayoutList(){
        return R.layout.list_row_consultant_group_user;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_consultant_group_user_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_consultant_group_user_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_consultant_group_user_update;
    }

    public ConsultantGroupUserFragment() {
        super(ConsultantGroupUser.class);
    }

    @SuppressLint("ValidFragment")
    public ConsultantGroupUserFragment(Class<ConsultantGroupUser> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<ConsultantGroupUser> getCreateFragment() {
        return new ConsultantGroupUserUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<ConsultantGroupUser> getUpdateFragment(ConsultantGroupUser activeElement) {
        return new ConsultantGroupUserUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(ConsultantGroupUser activeElement) {
        return new ConsultantGroupUserReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder {
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
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, ConsultantGroupUser currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        viewHolder.fillData(convertView);

        viewHolder.status.setText(currentElement.getStatus()==0 ? "Active" : "Inactive");
        viewHolder.videoTarif.setText(currentElement.getVideoTarif() != null ? currentElement.getVideoTarif().toString() : "");
        viewHolder.conversationTarif.setText(currentElement.getConversationTarif() != null ? currentElement.getConversationTarif().toString() : "");
        viewHolder.user.setText(currentElement.getUser().getFirstName());
        viewHolder.consultantGroup.setText(currentElement.getConsultantGroup().getName());

        return viewHolder;
    }
}
