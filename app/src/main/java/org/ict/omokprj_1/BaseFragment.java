package org.ict.omokprj_1;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    private View fragmentView;

    public abstract int getFragmentId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(getFragmentId(), container, false);
        //inflater는 xml로 정의된 view (또는 menu 등)를 실제 객체화 시키는 용도입니다.
        //예를 들어 약간 복잡한 구조의 view를 java코드로 만들게 되면 생성하고 속성 넣어주느라 코드가 길어질 수 있는데,
        //그걸 미리 xml로 만들어 놓고 java코드에서는 inflater를 활용하여 바로 view를 생성할 수 있습니다.
        return fragmentView;
    }

    public View getFragmentView() {
        return fragmentView;
    }
    public void onPause(Preferences preferences){
        //do nothing
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause(new Preferences(getActivity()));
    }

    public void onResume(Preferences preferences){
        //do nothing
    }

    @Override
    public void onResume() {
        super.onResume();
        onResume(new Preferences(getActivity()));
    }
}
