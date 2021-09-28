package io.objectbox.example.sync

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.Query
import io.objectbox.sync.SyncChange

/**
 * Like [ObjectBoxLiveData] but also publishes new results if sync changes are received.
 */
class ObjectBoxSyncLiveData<T>(
    private val query: Query<T>,
    private val syncChangesLiveData: LiveData<List<SyncChange>>
) : ObjectBoxLiveData<T>(
    query
) {
    override fun onActive() {
        super.onActive()
        syncChangesLiveData.observeForever(syncChangeObserver)
    }

    override fun onInactive() {
        super.onInactive()
        syncChangesLiveData.removeObserver(syncChangeObserver)
    }

    private val syncChangeObserver = Observer<List<SyncChange>> {
        query.publish()
    }
}