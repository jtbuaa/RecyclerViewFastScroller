/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package xyz.danoz.recyclerviewfastscroller.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import xyz.danoz.recyclerview.sample.R;
import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;

/**
 * Adapted from sample code that demonstrates the use of {@link WebView} with a {@link LinearLayoutManager}
 */
public class RecyclerViewWithFastScrollerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_with_fast_scroller_fragment, container, false);

        // Grab the RecyclerView and the RecyclerViewFastScroller from the layout
        WebView webView = (WebView) rootView.findViewById(R.id.web_view);
        VerticalRecyclerViewFastScroller fastScroller = (VerticalRecyclerViewFastScroller) rootView.findViewById(R.id.fast_scroller);

        // Connect the recycler to the scroller (to let the scroller scroll the list)
        fastScroller.setRecyclerView(webView);

        // Connect the scroller to the recycler (to let the recycler scroll the scroller's handle)
        //webView.setOnScrollListener(fastScroller.getOnScrollListener());

        setRecyclerViewLayoutManager(webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl("https://www.baidu.com/");
        return rootView;
    }

    /**
     * Set RecyclerView's LayoutManager
     */
    public void setRecyclerViewLayoutManager(WebView webview) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        //if (webview.getLayoutManager() != null) {
        //    scrollPosition =
        //            ((LinearLayoutManager) webview.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        //}

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        //webview.setLayoutManager(linearLayoutManager);
        //webview.scrollToPosition(scrollPosition);
    }

}
