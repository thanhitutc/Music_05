package com.framgia.music5;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseFragment extends Fragment {
    protected View mView;
    protected RecyclerView mRecycler;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        initActions();
    }

    protected abstract void initActions();

    protected abstract void initComponents();

    protected void replaceFragment(Fragment fragment) {
        Fragment f = getActivity().getSupportFragmentManager()
                .findFragmentByTag(fragment.getClass().getName());
        if (f != null && f == fragment) {
            if (fragment.isVisible()) {
                return;
            }
            getActivity().getSupportFragmentManager().beginTransaction().show(fragment).commit();
            return;
        }
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutcontent, fragment, fragment.getClass().getName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }
}
