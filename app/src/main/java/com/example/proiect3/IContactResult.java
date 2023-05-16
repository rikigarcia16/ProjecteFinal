package com.example.proiect3;

import java.util.ArrayList;

public interface IContactResult {
    void onSuccess(ArrayList<String> list);
    void onFailure(Throwable throwable);
}
