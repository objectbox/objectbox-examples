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

package io.objectbox.example;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.objectbox.Box;
import io.objectbox.android.ObjectBoxDataSource;
import io.objectbox.query.Query;

public class NotePagedViewModel extends ViewModel {

    private LiveData<PagedList<Note>> noteLiveDataPaged;

    public LiveData<PagedList<Note>> getNoteLiveDataPaged(Box<Note> notesBox) {
        if (noteLiveDataPaged == null) {
            // query all notes, sorted a-z by their text (https://docs.objectbox.io/queries)
            Query<Note> query = notesBox.query().order(Note_.text).build();
            noteLiveDataPaged = new LivePagedListBuilder<>(
                    new ObjectBoxDataSource.Factory<>(query),
                    20
            ).build();
        }
        return noteLiveDataPaged;
    }
}
