package com.timbuchalka.top10downloader.fragment.consultantgroup;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.text.SimpleDateFormat;


public class ConsultantGroupFragment
        extends CrudFragment<ConsultantGroup> {
    @Override
    protected int getLayoutList(){
        return R.layout.list_row_consultant_group;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_consultant_group_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_consultant_group_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_consultant_group_update;
    }

    public ConsultantGroupFragment() {
        super(ConsultantGroup.class);
    }

    @SuppressLint("ValidFragment")
    public ConsultantGroupFragment(Class<ConsultantGroup> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<ConsultantGroup> getCreateFragment() {
        return new ConsultantGroupUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<ConsultantGroup> getUpdateFragment(ConsultantGroup activeElement) {
        return new ConsultantGroupUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(ConsultantGroup activeElement) {
        return new ConsultantGroupReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<ConsultantGroup> {
        @Override
        public void fillData(View v) {
            this.name = (TextView) v.findViewById(R.id.name);
            this.description = (TextView) v.findViewById(R.id.description);
            this.videoTarif = (TextView) v.findViewById(R.id.videoTarif);
            this.conversationTarif = (TextView) v.findViewById(R.id.conversationTarif);
        }
        private TextView name;
        private TextView description;
        private TextView videoTarif;
        private TextView conversationTarif;

        @Override
        public void setText(ConsultantGroup currentElement) {
            name.setText(currentElement.getName());
            description.setText(currentElement.getDescription());
            videoTarif.setText(currentElement.getVideoTarif().toString());
            conversationTarif.setText(currentElement.getConversationTarif().toString());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, ConsultantGroup currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
