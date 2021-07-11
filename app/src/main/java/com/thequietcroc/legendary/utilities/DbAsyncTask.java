package com.thequietcroc.legendary.utilities;

import android.os.AsyncTask;
import android.os.Looper;

public class DbAsyncTask {

    public DbAsyncTask(final Runnable runnable) {
        try {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                throw new IllegalStateException();
            }

            runnable.run();
        } catch (final IllegalStateException e) {
            AsyncTask.execute(runnable);
        }
    }
}
