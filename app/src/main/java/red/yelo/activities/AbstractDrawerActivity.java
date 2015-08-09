/*
 *
 *  * Copyright (C) 2015 yelo.red
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */package red.yelo.activities;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import red.yelo.R;
import red.yelo.fragments.NavDrawerFragment;
import red.yelo.utils.AppConstants;


/**
 * Base class for all Activities that need to use a Navigation drawer. The drawer must be present in
 * the layout
 * <p/>
 */
public abstract class AbstractDrawerActivity extends AbstractYeloActivity implements NavDrawerFragment.INavDrawerActionCallback {


    /**
     * Drawer Layout that contains the Navigation Drawer
     */
    private DrawerLayout          mDrawerLayout;
    /**
     * Drawer toggle for Action Bar
     */
    private ActionBarDrawerToggle mDrawerToggle;
    /**
     * {@link android.widget.FrameLayout} that provides the navigation items
     */
    private FrameLayout mNavFrameContent;

    /**
     * Whether the drawer is loaded on screen, or off screen
     */
    private boolean mIsDrawerLoadedOnScreen;

    /**
     * Initializes the Navigation drawer. Call this in onCreate() of your Activity AFTER setting the
     * content view
     *
     * @param drawerLayoutResId  The layout res id of the drawer
     * @param drawerContentResId The layout res id of the drawer content
     * @param isDrawerLockedOpen Whether the drawer is loaded directly on screen(for ex, in the case
     *                           of tablets) or whether it should be loaded off the screen
     */
    protected void initDrawer(final int drawerLayoutResId, final int drawerContentResId, final boolean isDrawerLockedOpen) {

        mIsDrawerLoadedOnScreen = isDrawerLockedOpen;
        mDrawerLayout = (DrawerLayout) findViewById(drawerLayoutResId);

        if (mDrawerLayout == null) {
            throw new IllegalArgumentException(
                    "Drawer Layout with id R.id.drawer_layout not found. Check your layout/resource id being sent");
        }
        mNavFrameContent = (FrameLayout) findViewById(drawerContentResId);

        if (mNavFrameContent == null) {
            throw new IllegalArgumentException(
                    "Drawer content with id R.id.frame_nav_drawer not found. Check the layout/resource id being sent");
        }

        if (isDrawerActionBarToggleEnabled() && !isDrawerLockedOpen) {
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                                                      R.drawable.ic_navigation_drawer,
                                                      R.string.drawer_open,
                                                      R.string.drawer_closed) {

                @Override
                public void onDrawerOpened(final View drawerView) {
                    super.onDrawerOpened(drawerView);
                    //  mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    invalidateOptionsMenu();

                }

                @Override
                public void onDrawerClosed(final View drawerView) {
                    super.onDrawerClosed(drawerView);
                    //   mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    invalidateOptionsMenu();

                }

            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerToggle.setDrawerIndicatorEnabled(true);

        }

        if (isDrawerLockedOpen) {

            mDrawerLayout.setScrimColor(Color.TRANSPARENT);
            mDrawerLayout.setDrawerShadow(null, Gravity.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.START);
        } else {
            mDrawerLayout.setScrimColor(getResources()
                                                .getColor(R.color.overlay_black_40p));
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.START);
        }

        /* In the case that the activity was destroyed in background, the system will take care of reinitializing the fragment for us*/
        NavDrawerFragment fragment = (NavDrawerFragment) getSupportFragmentManager()
                .findFragmentByTag(AppConstants.FragmentTags.NAV_DRAWER);

        if (fragment == null) {
            fragment = (NavDrawerFragment) Fragment
                    .instantiate(this, NavDrawerFragment.class.getName());
            loadFragment(drawerContentResId, fragment, AppConstants.FragmentTags.NAV_DRAWER,
                         false, null);

        } else {

            loadFragment(drawerContentResId, fragment, AppConstants.FragmentTags.NAV_DRAWER,
                         false, null, false, true);
        }

    }

    /**
     * Whether the drawer is loaded directly on screen(for ex, in the case of tablets) or whether it
     * is loaded off the screen
     */
    public boolean isDrawerLoadedOnScreen() {
        return mIsDrawerLoadedOnScreen;
    }

    /**
     * Used to control whether the Activity should attach the drawer layout to the Action Bar.
     *
     * @return <code>true</code> to attach the drawer layout to the action bar
     */
    protected abstract boolean isDrawerActionBarToggleEnabled();

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (isDrawerActionBarToggleEnabled()) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isDrawerActionBarToggleEnabled()) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (isDrawerActionBarToggleEnabled()) {
            setOptionsGroupHidden(menu, mDrawerLayout.isDrawerOpen(mNavFrameContent));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setOptionsGroupHidden(final Menu menu, final boolean drawerOpen) {

        menu.setGroupEnabled(R.id.group_hide_on_drawer_open, !drawerOpen);
        menu.setGroupVisible(R.id.group_hide_on_drawer_open, !drawerOpen);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        /* Pass the event to ActionBarDrawerToggle, if it returns
         true, then it has handled the app icon touch event */
        if (isDrawerActionBarToggleEnabled() && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActionTaken() {

        if(!mIsDrawerLoadedOnScreen) {
            mDrawerLayout.closeDrawer(mNavFrameContent);
        }
    }


}
