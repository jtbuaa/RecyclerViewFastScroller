package xyz.danoz.recyclerviewfastscroller.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import xyz.danoz.recyclerview.sample.R;
import xyz.danoz.recyclerviewfastscroller.sample.data.ColorDataSet;
import xyz.danoz.recyclerviewfastscroller.sample.recyclerview.ColorfulAdapter;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.title.SectionTitleIndicator;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Adapted from sample code that demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager}
 */
public class RecyclerViewWithSectionIndicatorFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recycler_view_with_fast_scroller_section_title_indicator_fragment, container, false);

        // Grab your RecyclerView, RecyclerViewFastScroller, and SectionTitleIndicator from the layout
        WebView webView = (WebView) rootView.findViewById(R.id.web_view);
        VerticalRecyclerViewFastScroller fastScroller =
                (VerticalRecyclerViewFastScroller) rootView.findViewById(R.id.fast_scroller);
        SectionTitleIndicator sectionTitleIndicator =
                (SectionTitleIndicator) rootView.findViewById(R.id.fast_scroller_section_title_indicator);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(webView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        //recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        // Connect the section indicator to the scroller
        fastScroller.setSectionIndicator(sectionTitleIndicator);

        setRecyclerViewLayoutManager(webView);

        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager
     */
    public void setRecyclerViewLayoutManager(WebView webView) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        //if (recyclerView.getLayoutManager() != null) {
        //    scrollPosition =
        //            ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        //}

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        //recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.scrollToPosition(scrollPosition);
    }

}
