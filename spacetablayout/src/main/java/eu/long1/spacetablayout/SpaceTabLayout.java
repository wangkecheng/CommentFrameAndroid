/*
 * Copyright (c) 2016 Lung Razvan
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package eu.long1.spacetablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SpaceTabLayout extends RelativeLayout implements TabOnclickLisener {

    private TabLayout tabLayout;

    private List<String> fragmentTitList;//tab的标题
    private List<Tab> tabList;//tab的标题
    private List<TextView> textViewList;//标题 TextView
    private List<ImageView> imgViewList;//按钮图片 ImageView

    private List<Integer> fragImgList;
    private RelativeLayout parentLayout;
    private RelativeLayout selectedTabLayout;
    private FloatingActionButton actionButton;
    private ImageView backgroundImage;
    private ImageView backgroundImage2;

    private String text_one;
    private String text_two;
    private String text_three;

    private List<Tab> tabs = new ArrayList<>();
    private List<Integer> tabSize = new ArrayList<>();

    private int numberOfTabs = 3;
    private int currentPosition;
    private int startingPosition;

    private TabOnclickLisener tabOnClickListener;

    private int defaultTextColor;
    private int defaultButtonColor;
    private int defaultTabColor;

    private boolean iconOnly = false;

    private boolean SCROLL_STATE_DRAGGING = false;

    private static final String CURRENT_POSITION_SAVE_STATE = "buttonPosition";

    public SpaceTabLayout(Context context) {
        super(context);
        init();
    }

    public SpaceTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initArrts(attrs);
    }

    public SpaceTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArrts(attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.three_tab_space_layout, this);
        parentLayout = (RelativeLayout) findViewById(R.id.tabLayout);
        selectedTabLayout = (RelativeLayout) findViewById(R.id.selectedTabLayout);
        backgroundImage = (ImageView) findViewById(R.id.backgroundImage);
        backgroundImage2 = (ImageView) findViewById(R.id.backgroundImage2);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        tabLayout = (TabLayout) findViewById(R.id.spaceTab);
        defaultTextColor = ContextCompat.getColor(getContext(), android.R.color.primary_text_dark);
        defaultButtonColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        defaultTabColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    }

    private void initArrts(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SpaceTabLayout);
        for (int i = 0; i < a.getIndexCount(); ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SpaceTabLayout_number_of_tabs) {
                numberOfTabs = a.getInt(attr, 3);

            } else if (attr == R.styleable.SpaceTabLayout_starting_position) {
                startingPosition = a.getInt(attr, 0);

            } else if (attr == R.styleable.SpaceTabLayout_tab_color) {
                defaultTabColor = a.getColor(attr, getContext().getResources().getIdentifier("colorAccent", "color", getContext().getPackageName()));

            } else if (attr == R.styleable.SpaceTabLayout_button_color) {
                defaultButtonColor = a.getColor(attr, getContext().getResources().getIdentifier("colorPrimary", "color", getContext().getPackageName()));

            } else if (attr == R.styleable.SpaceTabLayout_text_color) {
                defaultTextColor = a.getColor(attr, ContextCompat.getColor(getContext(), android.R.color.primary_text_dark));

            } else if (attr == R.styleable.SpaceTabLayout_icon_one) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_tab_five);
                drawable = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_icon_two) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_tab_five);
                drawable = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_icon_three) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_tab_five);
                drawable = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_icon_four) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.ic_tab_five);

                drawable = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_icon_five) {
                Drawable drawable = a.getDrawable(attr);
                drawable = a.getDrawable(attr);

            } else if (attr == R.styleable.SpaceTabLayout_text_one) {
                text_one = a.getString(attr);

            } else if (attr == R.styleable.SpaceTabLayout_text_two) {
                text_two = a.getString(attr);

            } else if (attr == R.styleable.SpaceTabLayout_text_three) {
                text_three = a.getString(attr);

            }
        }
        a.recycle();
    }

    /**
     * This will initialize the View and the ViewPager to properly display
     * the fragments from the list.
     *
     * @param viewPager       where you want the fragments to show
     * @param fragmentManager needed for the fragment transactions
     * @param fragments       fragments to be displayed
     */
    public void initialize(ViewPager viewPager, FragmentManager fragmentManager, List<Fragment> fragments, List<String> fragTitList, List<Integer> fragImgList) {
        this.fragmentTitList = fragTitList;
        this.fragImgList = fragImgList;
        startingPosition = fragImgList.size() / 2;
        if (fragments.size() != fragTitList.size() || fragments.size() != fragImgList.size() || fragTitList.size() != fragImgList.size())
            throw new IllegalArgumentException("三者数量必须一致");
        viewPager.setAdapter(new PagerAdapter(fragmentManager, fragments));
        tabLayout.setupWithViewPager(viewPager);
        getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Tab tab = tabList.get(0);
                        tabSize.add(tab.getCustomView().getWidth());
                        tabSize.add(getWidth());
                        tabSize.add(backgroundImage.getWidth());

                        moveTab(tabSize, startingPosition);

                        if (currentPosition == 0) {
                            currentPosition = startingPosition;
                            tabs.get(startingPosition).getCustomView().setAlpha(0);
                        } else moveTab(tabSize, currentPosition);

                        if (Build.VERSION.SDK_INT < 16)
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        else getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
        viewPager.setCurrentItem(startingPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (SCROLL_STATE_DRAGGING) {
                    tabs.get(position).getCustomView().setAlpha(positionOffset);
                    if (position < numberOfTabs - 1) {
                        tabs.get(position + 1).getCustomView().setAlpha(1 - positionOffset);
                    }

                    if (!tabSize.isEmpty()) {
                        if (position < currentPosition) {
                            final float endX = -tabSize.get(2) / 2 + getX(position, tabSize) + 42;
                            final float startX = -tabSize.get(2) / 2 + getX(currentPosition, tabSize) + 42;

                            if (!tabSize.isEmpty()) {
                                float a = endX - (positionOffset * (endX - startX));
                                selectedTabLayout.setX(a);
                            }

                        } else {
                            position++;
                            final float endX = -tabSize.get(2) / 2 + getX(position, tabSize) + 42;
                            final float startX = -tabSize.get(2) / 2 + getX(currentPosition, tabSize) + 42;

                            float a = startX + (positionOffset * (endX - startX));
                            selectedTabLayout.setX(a);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                for (Tab t : tabs) t.getCustomView().setAlpha(1);
                tabs.get(position).getCustomView().setAlpha(0);
                moveTab(tabSize, position);
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                SCROLL_STATE_DRAGGING = state == ViewPager.SCROLL_STATE_DRAGGING;
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    for (Tab t : tabs) t.getCustomView().setAlpha(1);
                    tabs.get(currentPosition).getCustomView().setAlpha(0);
                    moveTab(tabSize, currentPosition);
                }
            }
        });
        tabList = new ArrayList<Tab>();
        textViewList = new ArrayList<TextView>();
        imgViewList = new ArrayList<ImageView>();
        for (int i = 0; i < fragments.size(); i++) {
            tabList.add(tabLayout.getTabAt(i));
        }

        if (!iconOnly) {
            for (int i = 0; i < fragments.size(); i++) {
                Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(R.layout.icon_text_tab_layout);
                tabs.add(tab);
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabTextView);
                textView.setText(fragTitList.get(i));
                textViewList.add(textView);

                ImageView imageView = (ImageView) tab.getCustomView().findViewById(R.id.tabImageView);
                imageView.setImageDrawable(getContext().getResources().getDrawable(fragImgList.get(i)));
                imgViewList.add(imageView);
            }
        }
        selectedTabLayout.bringToFront();
        tabLayout.setSelectedTabIndicatorHeight(0);
        setAttrs();
    }

    private void setAttrs() {
        setTabColor(defaultTabColor);
        setButtonColor(defaultButtonColor);
        for (int i = 0; i < fragmentTitList.size(); i++) {
            ((TextView) textViewList.get(i)).setTextColor(defaultTextColor);
            ((ImageView) imgViewList.get(i)).setImageDrawable(getContext().getResources().getDrawable(fragImgList.get(i)));
        }
    }

    private void moveTab(List<Integer> tabSize, int position) {
        if (!tabSize.isEmpty()) {
            float backgroundX = -tabSize.get(2) / 2 + getX(position, tabSize) + 42;
            actionButton.setImageDrawable(getContext().getResources().getDrawable(fragImgList.get(position)));
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tabOnClickListener != null) {
                        tabOnClickListener.tabOnclick(currentPosition, fragmentTitList.get(currentPosition));
                    }
                }
            });
            selectedTabLayout.animate().x(backgroundX).setDuration(100);
            if (tabOnClickListener != null) {
                tabOnClickListener.tabMoveAction(position, fragmentTitList.get(position));
            }
        }
    }

    private float getX(int position, List<Integer> sizesList) {
        if (!sizesList.isEmpty()) {
            float tabWidth = sizesList.get(0);

            float numberOfMargins = 2 * numberOfTabs;
            float itemsWidth = (int) (numberOfTabs * tabWidth);
            float marginsWidth = sizesList.get(1) - itemsWidth;

            float margin = marginsWidth / numberOfMargins;

            //TODO: this is a magic number
            float half = 42;
            float x = 0;
            switch (position) {
                case 0:
                    x = tabWidth / 2 + margin - half;
                    break;
                case 1:
                    x = tabWidth * 3 / 2 + 3 * margin - half;
                    break;
                case 2:
                    x = tabWidth * 5 / 2 + 5 * margin - half;
                    break;
                case 3:
                    x = tabWidth * 7 / 2 + 7 * margin - half;
                    break;
                case 4:
                    x = tabWidth * 9 / 2 + 9 * margin - half;
                    break;
            }
            return x;
        }
        return 0;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = this.currentPosition;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        this.currentPosition = ss.currentPosition;
    }

    //TODO: get this working :))
    private void setCurrentPosition(int currentPosition) {

    }

    /**
     * You can use it, for example, if want to set the same listener to all
     * buttons and you can use a switch using this method to identify the
     * button that was pressed.
     *
     * @return the current tab position
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /***********************************************************************************************
     * getters and setters for the OnClickListeners of each tab position
     **********************************************************************************************/
    public TabOnclickLisener getTabOnClickListener() {
        return tabOnClickListener;
    }

    public void setTabOnClickListener(TabOnclickLisener listener) {
        tabOnClickListener = listener;
    }

    @Override
    public void tabOnclick(Integer index, String tabTit) {

    }

    @Override
    public void tabMoveAction(Integer index, String tabTit) {

    }

    public View getTabLayout() {
        return parentLayout;
    }

    public void setTabColor(@ColorInt int backgroundColor) {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
        Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.background);
        Drawable image2 = ContextCompat.getDrawable(getContext(), R.drawable.background2);
        image.setColorFilter(porterDuffColorFilter);
        image2.setColorFilter(porterDuffColorFilter);

        backgroundImage.setImageDrawable(image);
        backgroundImage2.setImageDrawable(image2);
    }

    public FloatingActionButton getButton() {
        return actionButton;
    }

    public void setButtonColor(@ColorInt int backgroundColor) {
        actionButton.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
    }


    public View getTabViewAtIndex(int index) {
        if (index > fragImgList.size())
            throw new IllegalArgumentException("You have " + numberOfTabs + " tabs.");
        else
            return ((Tab) tabList.get(index)).getCustomView();
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
