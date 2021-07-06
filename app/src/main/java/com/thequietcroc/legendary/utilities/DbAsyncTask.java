package com.thequietcroc.legendary.utilities;

import android.os.AsyncTask;

public class DbAsyncTask {

    public DbAsyncTask(final Runnable runnable) {
        try {
            runnable.run();
        } catch (final IllegalStateException e) {
            AsyncTask.execute(runnable);
        }
    }
}
