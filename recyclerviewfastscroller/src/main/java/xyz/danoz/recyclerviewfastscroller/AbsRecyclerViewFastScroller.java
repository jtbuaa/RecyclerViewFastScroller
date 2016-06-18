package xyz.danoz.recyclerviewfastscroller;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import xyz.danoz.recyclerviewfastscroller.calculation.progress.ScrollProgressCalculator;
import xyz.danoz.recyclerviewfastscroller.calculation.progress.TouchableScrollProgressCalculator;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.SectionIndicator;

/**
 * Defines a basic widget that will allow for fast scrolling a RecyclerView using the basic paradigm of
 * a handle and a bar.
 *
 * TODO: More specifics and better support for effectively extending this base class
 */
public abstract class AbsRecyclerViewFastScroller extends FrameLayout implements RecyclerViewScroller {

    private static final int[] STYLEABLE = R.styleable.AbsRecyclerViewFastScroller;
    /** The long bar along which a handle travels */
    protected final View mBar;
    /** The handle that signifies the user's progress in the list */
    protected final View mHandle;

    /* TODO:
     *      Consider making RecyclerView final and should be passed in using a custom attribute
     *      This could allow for some type checking on the section indicator wrt the adapter of the RecyclerView
    */
    private WebView mWebView;
    private SectionIndicator mSectionIndicator;

    /** If I had my druthers, AbsRecyclerViewFastScroller would implement this as an interface, but Android has made
     * {@link OnTouchListener} an abstract class instead of an interface. Hmmm */
    protected OnTouchListener mOnScrollListener;

    public AbsRecyclerViewFastScroller(Context context) {
        this(context, null, 0);
    }

    public AbsRecyclerViewFastScroller(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsRecyclerViewFastScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = getContext().getTheme().obtainStyledAttributes(attrs, STYLEABLE, 0, 0);

        try {
            int layoutResource = attributes.getResourceId(R.styleable.AbsRecyclerViewFastScroller_rfs_fast_scroller_layout,
                    getLayoutResourceId());
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(layoutResource, this, true);

            mBar = findViewById(R.id.scroll_bar);
            mHandle = findViewById(R.id.scroll_handle);

            Drawable barDrawable = attributes.getDrawable(R.styleable.AbsRecyclerViewFastScroller_rfs_barBackground);
            int barColor = attributes.getColor(R.styleable.AbsRecyclerViewFastScroller_rfs_barColor, Color.GRAY);
            applyCustomAttributesToView(mBar, barDrawable, barColor);

            Drawable handleDrawable = attributes.getDrawable(R.styleable.AbsRecyclerViewFastScroller_rfs_handleBackground);
            int handleColor = attributes.getColor(R.styleable.AbsRecyclerViewFastScroller_rfs_handleColor, Color.GRAY);
            applyCustomAttributesToView(mHandle, handleDrawable, handleColor);
        } finally {
            attributes.recycle();
        }

        setOnTouchListener(new FastScrollerTouchListener(this));
    }

    private void applyCustomAttributesToView(View view, Drawable drawable, int color) {
        if (drawable != null) {
            setViewBackground(view, drawable);
        } else {
            view.setBackgroundColor(color);
        }
    }

    @TargetApi(VERSION_CODES.JELLY_BEAN)
    private void setViewBackground(View view, Drawable background) {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(background);
        }
    }

    @Override
    public void setRecyclerView(WebView webView) {
        mWebView = webView;
    }

    public void setSectionIndicator(SectionIndicator sectionIndicator) {
        mSectionIndicator = sectionIndicator;
    }

    @Nullable
    public SectionIndicator getSectionIndicator() {
        return mSectionIndicator;
    }

    @Override
    public void scrollTo(float scrollProgress, boolean fromTouch) {
        mWebView.scrollBy(0, (int)scrollProgress);
    }

    int mode;
    private static final int QUICKSCROLL = 1;
    private static final int NONE = 0;
    /**
     * Classes that extend AbsFastScroller must implement their own {@link OnTouchListener} to respond to scroll
     * events when the {@link #mWebView} is scrolled NOT using the fast scroller.
     * @return an implementation for responding to scroll events from the {@link #mWebView}
     */
    @NonNull
    public OnTouchListener getOnScrollListener() {
        if (mOnScrollListener == null) {
            mOnScrollListener = new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    float scrollProgress = 0;
                    ScrollProgressCalculator scrollProgressCalculator = getScrollProgressCalculator();
                    if (scrollProgressCalculator != null) {
                        scrollProgress = scrollProgressCalculator.calculateScrollProgress(view);
                    }
                    moveHandleToPosition(scrollProgress);

                    /*switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            if(event.getX()>this.getWidth()-50&&(this.canScrollVertically(0)||this.canScrollVertically(1))){
                                scrollbarTop = (float)this.computeVerticalScrollExtent()/this.computeVerticalScrollRange()*this.computeVerticalScrollOffset();
                                scrollbarBtm = (float)this.computeVerticalScrollExtent()/this.computeVerticalScrollRange()*(this.computeVerticalScrollExtent()+this.computeVerticalScrollOffset());
                                if((scrollbarBtm-scrollbarTop)<50){
                                    scrollbarTop = scrollbarTop-20;
                                    scrollbarBtm = scrollbarBtm+20;
                                }
                                if(event.getY() > scrollbarTop && event.getY() < scrollbarBtm){
                                    scrollOffset = event.getY() - scrollbarTop;
                                    mode = QUICKSCROLL;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            mode = NONE;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(mode==QUICKSCROLL){
                                scrollbarTop = (float)this.computeVerticalScrollExtent()/this.computeVerticalScrollRange()*this.computeVerticalScrollOffset();
                                scrollbarBtm = (float)this.computeVerticalScrollExtent()/this.computeVerticalScrollRange()*(this.computeVerticalScrollExtent()+this.computeVerticalScrollOffset());
                                if((scrollbarBtm-scrollbarTop)<50){
                                    scrollbarTop = scrollbarTop-20;
                                    scrollbarBtm = scrollbarBtm+20;
                                }
                                int scrollto = Math.round((float)this.computeVerticalScrollRange()
                                        /this.computeVerticalScrollExtent()*
                                        (event.getY()-scrollOffset));
                                if(scrollto>-1
                                        &&(scrollto+this.computeVerticalScrollExtent())<this.computeVerticalScrollRange()+1
                                        ){
                                    this.scrollTo(0,scrollto);
                                }
                                return true;
                            }
                            break;
                    }*/
                    return false;
                }
            };
        }
        return mOnScrollListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (getScrollProgressCalculator() == null) {
            onCreateScrollProgressCalculator();
        }

        // synchronize the handle position to the RecyclerView
        float scrollProgress = getScrollProgressCalculator().calculateScrollProgress(mWebView);
        moveHandleToPosition(scrollProgress);
    }

    /**
     * Sub classes have to override this method and create the ScrollProgressCalculator instance in this method.
     */
    protected abstract void onCreateScrollProgressCalculator();

    /**
     * Takes a touch event and determines how much scroll progress this translates into
     * @param event touch event received by the layout
     * @return scroll progress, or fraction by which list is scrolled [0 to 1]
     */
    public float getScrollProgress(MotionEvent event) {
        ScrollProgressCalculator scrollProgressCalculator = getScrollProgressCalculator();
        if (scrollProgressCalculator != null) {
            return getScrollProgressCalculator().calculateScrollProgress(event);
        }
        return 0;
    }

    /**
     * Define a layout resource for your implementation of AbsFastScroller
     * Currently must contain a handle view (R.id.scroll_handle) and a bar (R.id.scroll_bar)
     * @return a resource id corresponding to the chosen layout.
     */
    protected abstract int getLayoutResourceId();

    /**
     * Define a ScrollProgressCalculator for your implementation of AbsFastScroller
     * @return a chosen implementation of {@link ScrollProgressCalculator}
     */
    @Nullable
    protected abstract TouchableScrollProgressCalculator getScrollProgressCalculator();

    /**
     * Moves the handle of the scroller by specific progress amount
     * @param scrollProgress fraction by which to move scroller [0 to 1]
     */
    public abstract void moveHandleToPosition(float scrollProgress);

}