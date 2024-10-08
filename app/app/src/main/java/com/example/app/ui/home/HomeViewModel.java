package com.example.app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es un fragmento, se puede editar desde ui>nombreFragmento>ViewModel.java");
    }

    public LiveData<String> getText() {
        return mText;
    }
}