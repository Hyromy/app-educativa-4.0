package com.example.app.db.models.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app.db.models.UsuarioModel;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<UsuarioModel> usuario = new MutableLiveData<>();

    public void setUsuario(UsuarioModel usuario) {
        this.usuario.setValue(usuario);
    }

    public LiveData<UsuarioModel> getUsuario() {
        return usuario;
    }
}