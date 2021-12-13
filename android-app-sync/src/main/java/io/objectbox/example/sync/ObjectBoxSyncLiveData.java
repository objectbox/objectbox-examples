package io.objectbox.example.sync;

import java.util.List;
import java.util.logging.ConsoleHandler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import io.objectbox.android.ObjectBoxLiveData;
import io.objectbox.query.Query;
import io.objectbox.sync.SyncChange;
import io.objectbox.sync.listener.SyncChangeListener;

/**
 * Like {@link ObjectBoxLiveData} but also publishes new results if sync changes are received.
 */
public class ObjectBoxSyncLiveData<T> extends ObjectBoxLiveData<T> {

    private final Query<T> query;
    private final LiveData<List<SyncChange>> syncChangesLiveData;

    public ObjectBoxSyncLiveData(Query<T> query, LiveData<List<SyncChange>> syncChangesLiveData) {
        super(query);
        this.query = query;
        this.syncChangesLiveData = syncChangesLiveData;
    }

    @Override
    protected void onActive() {
        super.onActive();
        syncChangesLiveData.observeForever(syncChangeObserver);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        syncChangesLiveData.removeObserver(syncChangeObserver);
    }

    private final Observer<List<SyncChange>> syncChangeObserver = new Observer<List<SyncChange>>() {
        @Override
        public void onChanged(List<SyncChange> syncChanges) {
            query.publish();
        }
    };
}
