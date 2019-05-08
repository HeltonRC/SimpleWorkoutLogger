package com.hrcosta.simpleworkoutlogger.data;

import android.provider.BaseColumns;

public class WorkoutListContract {

    public static final class ExercisesEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_EX_NAME = "name";
        public static final String COLUMN_EX_DESCRIPTION = "description";
        public static final String COLUMN_CATEGORY = "category";
    }


    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "exercise";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "description";
    }

    public static final class WorkoutEntry implements BaseColumns {
        public static final String TABLE_NAME = "workout";
        public static final String COLUMN_WORK_DATE = "date";
        public static final String COLUMN_WORK_USER_ID = "user_id";
        public static final String COLUMN_WORK_EXERCISE_ID = "exercise_id";
        public static final String COLUMN_WORK_EXERCISE_REPS = "exer_reps";
        public static final String COLUMN_WORK_EXERCISE_SETS = "exer_sets";

    }



    //todo create the contentprovider - nd801 - lesson 9



}
