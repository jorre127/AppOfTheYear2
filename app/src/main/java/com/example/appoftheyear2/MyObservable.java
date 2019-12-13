package com.example.appoftheyear2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Observable;
import java.util.Observer;

public class MyObservable extends Observable {
    private static MyObservable instance = null;

    private MyObservable() {
    }

    public static MyObservable getInstance() {
        if (instance == null) {
            instance = new MyObservable();
        }
        return instance;
    }

    public void sendData(Object data) {
        setChanged();
        notifyObservers(data);
    }

    public class MyAdapter extends BaseAdapter implements Observer {


        @Override
        public void update(Observable observable, Object o) {
            if (observable instanceof MyObservable) {
                // do what you need here
            }
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }


    }
}
