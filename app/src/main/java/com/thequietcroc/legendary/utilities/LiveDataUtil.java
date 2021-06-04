package com.thequietcroc.legendary.utilities;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class LiveDataUtil {

    public static <T> void observeOnce(final LifecycleOwner owner,
                                       final LiveData<T> liveData,
                                       final Observer<T> observer) {
        liveData.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                observer.onChanged(t);
                liveData.removeObserver(this);
            }
        });
    }
}
