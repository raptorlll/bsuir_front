package com.leonov.bsuir.fragment.crud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.leonov.bsuir.R;
import com.leonov.bsuir.api.crud.CreateData;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.UpdateData;
import com.leonov.bsuir.models.ModelInterface;

abstract public class UpdateFragment<T extends ModelInterface>
        extends Fragment
        implements View.OnClickListener,
            UpdateData.OnDataAvailable<T>,
            CreateData.OnDataAvailable<T>
{
    private static final String TAG = "Update info";
    private Class<T> genericClass;
    private int layout;
    protected T activeElement;
    private T element;
    Button buttonSubmit;

    FragmentTransaction ft;

    @Override
    public void onDataAvailable(T data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");
        //redirect back
        changeToListPage();
    }

    public void changeToListPage(){
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMain, getListView());
        ft.commit();
    }

    public UpdateFragment() {}

    @SuppressLint("ValidFragment")
    public UpdateFragment(Class<T> genericClass,T activeElement, int layout) {
        super();
        this.activeElement = activeElement;
        this.layout = layout;
        this.genericClass = genericClass;
    }

    @SuppressLint("ValidFragment")
    public UpdateFragment(Class<T> genericClass, int layout) {
        super();

        this.genericClass = genericClass;

        try {
            this.activeElement = genericClass.newInstance();
        } catch (IllegalAccessException e){
            System.out.println("");
        }catch (java.lang.InstantiationException e){
            System.out.println("");
        }

        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(layout, parent, false);

        findViewsById(view);

        buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);

        setValues();

        setListeners();

        if (buttonSubmit!=null) {
            buttonSubmit.setOnClickListener(this);
        }

        return view;
    }

    private void setValues() {
        if(activeElement.getId()==null){
            return;
        }

        convertForView(activeElement);
    }

    public boolean customSubmit(T activeElement){
        return false;
    }

    private void submit(){
        convertForSubmit(activeElement);

        if (customSubmit(activeElement)) {
            return;
        }

        if(activeElement.getId() == null) {
            CreateData<T> getCustomerInformationData = new CreateData<T>(genericClass, this, activeElement);
            getCustomerInformationData.execute();
        } else {
            UpdateData<T> getCustomerInformationData = new UpdateData<T>(genericClass, this, activeElement);
            getCustomerInformationData.execute();
        }
    }

    /** Override */
    abstract public void convertForView(T view);

    /** Override */
    abstract public void convertForSubmit(T view);

    /** Override */
    public void onClickListeners(View view) {}

    /** Override */
    public void setListeners() {}

    /** Override */
    abstract public void findViewsById(View view);

    abstract public Fragment getListView();

    @Override
    public void onClick(View view) {
        onClickListeners(view);

        if (buttonSubmit != null && view == buttonSubmit) {
            submit();
        }
    }

}
