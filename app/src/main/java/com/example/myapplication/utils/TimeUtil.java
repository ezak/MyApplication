package com.example.myapplication.utils;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
    private static final String TAG = "TimeUtil";

    public enum TimeGranularity {
        SECONDS {
            public long toMillis() {
                return TimeUnit.SECONDS.toMillis(1);
            }
        }, MINUTES {
            public long toMillis() {
                return TimeUnit.MINUTES.toMillis(1);
            }
        }, HOURS {
            public long toMillis() {
                return TimeUnit.HOURS.toMillis(1);
            }
        }, DAYS {
            public long toMillis() {
                return TimeUnit.DAYS.toMillis(1);
            }
        }, WEEKS {
            public long toMillis() {
                return TimeUnit.DAYS.toMillis(7);
            }
        }, MONTHS {
            public long toMillis() {
                return TimeUnit.DAYS.toMillis(30);
            }
        }, YEARS {
            public long toMillis() {
                return TimeUnit.DAYS.toMillis(365);
            }
        }, DECADES {
            public long toMillis() {
                return TimeUnit.DAYS.toMillis(365 * 10);
            }
        };

        public abstract long toMillis();

        public static String calculateTimeAgoByTimeGranularity(Date pastTime) {
            TimeGranularity granularity = TimeGranularity.SECONDS;
            long timeDifferenceInMillis =  System.currentTimeMillis() - pastTime.getTime();
            String time = "few";

            if (timeDifferenceInMillis / TimeGranularity.DECADES.toMillis() > 0) {
                granularity = TimeGranularity.DECADES;
            } else if (timeDifferenceInMillis / TimeGranularity.YEARS.toMillis() > 0) {
                granularity = TimeGranularity.YEARS;
            } else if (timeDifferenceInMillis / TimeGranularity.MONTHS.toMillis() > 0) {
                granularity = TimeGranularity.MONTHS;
            } else if (timeDifferenceInMillis / TimeGranularity.WEEKS.toMillis() > 0) {
                granularity = TimeGranularity.WEEKS;
            } else if (timeDifferenceInMillis / TimeGranularity.DAYS.toMillis() > 0) {
                granularity = TimeGranularity.DAYS;
            } else if (timeDifferenceInMillis / TimeGranularity.HOURS.toMillis() > 0) {
                granularity = TimeGranularity.HOURS;
            } else if (timeDifferenceInMillis / TimeGranularity.MINUTES.toMillis() > 0) {
                granularity = TimeGranularity.MINUTES;
            } else {
                // already assigned
                // granularity = TimeGranularity.SECONDS;
                Log.e(TAG, "calculateTimeAgoByTimeGranularity: " );
            }

            if (timeDifferenceInMillis / granularity.toMillis() > 0) {
                time = String.valueOf((timeDifferenceInMillis / granularity.toMillis()));
            }

            return  time + " " + granularity.name().toLowerCase() + " ago";
        }

    }
}
