package com.example.app.ui.prueba_fragmento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PruebaFragmentoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PruebaFragmentoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es mi fragmento de prueaba");
    }

    public LiveData<String> getText() {
        return mText;
    }
}