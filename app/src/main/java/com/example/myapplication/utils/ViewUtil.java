package com.example.myapplication.utils;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ViewUtil {

    private static final String TAG = "ViewUtil";
    public static void startFragment(FragmentActivity activity, Fragment fragment, int container_id, String tag, boolean atb) {
        if (!activity.getSupportFragmentManager().isDestroyed()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(
                    R.anim.fragment_slide_in,   // enter
                    R.anim.fragment_fade_out,   // exit
                    R.anim.fragment_fade_in,    // popEnter
                    R.anim.fragment_slide_out); // popExit
            ft.replace(container_id, fragment, tag);
            if (atb) ft.addToBackStack(tag);
            ft.commit();
        } else {
            Log.e(TAG, tag + " is destroyed");
        }
    }

    public static void startChildFragment(Fragment parent, Fragment fragment, int container_id, String tag) {
        if (!parent.getChildFragmentManager().isDestroyed()) {
            FragmentTransaction ft = parent.getChildFragmentManager().beginTransaction();
            ft.setCustomAnimations(
                    R.anim.fragment_slide_in,   // enter
                    R.anim.fragment_fade_out,   // exit
                    R.anim.fragment_fade_in,    // popEnter
                    R.anim.fragment_slide_out); // popExit
            ft.replace(container_id, fragment, tag);
            ft.addToBackStack(tag);
            ft.commit();
        } else {
            Log.e(TAG, tag + " is destroyed");
        }
    }

    public static void showBottomSheet(FragmentActivity activity, BottomSheetDialogFragment dialogFragment, String tag) {
        dialogFragment.show(activity.getSupportFragmentManager(), tag);
    }

    public static void showDialog(FragmentActivity activity, DialogFragment dialog, String tag) {
        // todo not tested
        if (!activity.getSupportFragmentManager().isDestroyed()) {
            dialog.show(activity.getSupportFragmentManager(), tag);
        } else {
            Log.e(TAG, "showDialog: fragment is destroyed ");
        }
    }

    public static void showChildDialog(Fragment parent, DialogFragment dialog, String tag) {
        Log.e(TAG, "showChildDialog: " + parent.getTag());
        if (!parent.getChildFragmentManager().isDestroyed()) {
            dialog.show(parent.getChildFragmentManager(), tag);
        } else {
            Log.e(TAG, tag + " is destroyed");
        }
    }

    public static void clearBackStack(FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public static int dpToPixel(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
