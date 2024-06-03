/*
 * Copyright 2024 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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