package org.ict.omokprj_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private View fragmentView;

    public abstract int getFragmentId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(getFragmentId(), container, false);
        return fragmentView;
    }

    public View getFragmentView() {
        return fragmentView;
    }
}
