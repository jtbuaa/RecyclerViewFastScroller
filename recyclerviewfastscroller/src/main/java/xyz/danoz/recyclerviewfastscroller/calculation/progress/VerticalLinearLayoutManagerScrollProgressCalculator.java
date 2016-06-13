package xyz.danoz.recyclerviewfastscroller.calculation.progress;

import xyz.danoz.recyclerviewfastscroller.calculation.VerticalScrollBoundsProvider;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Calculates scroll progress for a {@link RecyclerView} with a {@link LinearLayoutManager}
 */
public class VerticalLinearLayoutManagerScrollProgressCalculator extends VerticalScrollProgressCalculator {

    public VerticalLinearLayoutManagerScrollProgressCalculator(VerticalScrollBoundsProvider scrollBoundsProvider) {
        super(scrollBoundsProvider);
    }

    /**
     * @param view recycler that experiences a scroll event
     * @return the progress through the recycler view list content
     */
    @Override
    public float calculateScrollProgress(View view) {
        return view.getScrollY() / view.getHeight();
    }
}
