package io.objectbox.example.sync;

import androidx.annotation.StringRes;

public enum TasksFilter {
    ALL(1, R.string.tab_all),
    OPEN(2, R.string.tab_open),
    DONE(3, R.string.tab_done);

    public final int id;
    public final int textResId;

    TasksFilter(int id, @StringRes int textResId) {
        this.id = id;
        this.textResId = textResId;
    }

    public static TasksFilter fromId(int id) {
        for (TasksFilter value : values()) {
            if (value.id == id) return value;
        }
        return ALL;
    }
}
