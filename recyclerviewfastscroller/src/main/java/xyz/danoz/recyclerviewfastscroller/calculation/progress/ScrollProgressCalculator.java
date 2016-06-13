package xyz.danoz.recyclerviewfastscroller.calculation.progress;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Assists in calculating the amount of scroll progress for a {@link RecyclerView}
 */
public interface ScrollProgressCalculator {

    /**
     * Calculates the scroll progress of a provided RecyclerView
     * @param view for which to calculate scroll progress
     * @return fraction from [0 to 1] representing the scroll progress
     */
    public float calculateScrollProgress(View view);

}
