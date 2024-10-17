package com.example.app.db.models.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app.db.models.ExamenModel;

public class ExamenViewModel extends ViewModel {
    private final MutableLiveData<ExamenModel> examen = new MutableLiveData<>();

    public void setExamen(ExamenModel examen) {
        this.examen.setValue(examen);
    }

    public LiveData<ExamenModel> getExamen() {
        return examen;
    }
}
