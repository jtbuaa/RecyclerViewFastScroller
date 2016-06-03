package xyz.danoz.recyclerviewfastscroller.sectionindicator.title;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import xyz.danoz.recyclerviewfastscroller.R;
import xyz.danoz.recyclerviewfastscroller.sectionindicator.AbsSectionIndicator;

/**
 * Popup view that gets shown when fast scrolling
 */
public abstract class SectionTitleIndicator<T> extends AbsSectionIndicator<T> {

    private static final int DEFAULT_TITLE_INDICATOR_LAYOUT = R.layout.section_indicator_with_title;

    public SectionTitleIndicator(Context context) {
        this(context, null);
    }

    public SectionTitleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectionTitleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @return the default layout for a section indicator with a title. This closely resembles the section indicator
     *         featured in Lollipop's Contact's application
     */
    @Override
    protected int getDefaultLayoutId() {
        return DEFAULT_TITLE_INDICATOR_LAYOUT;
    }

}
