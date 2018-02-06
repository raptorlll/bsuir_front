package com.timbuchalka.top10downloader.fragment.crud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.timbuchalka.top10downloader.models.ModelInterface;

abstract public class ReadFragment<T extends ModelInterface>
        extends Fragment {
    private int layout;
    protected T activeElement;

    public ReadFragment() {
    }

    @SuppressLint("ValidFragment")
    public ReadFragment(T activeElement, int layout) {
        super();
        this.activeElement = activeElement;
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(layout, parent, false);

        createSetVars(view);

        return view;
    }

    abstract public void createSetVars(View view);
}


















