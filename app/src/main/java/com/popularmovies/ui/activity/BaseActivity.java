package com.popularmovies.ui.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.popularmovies.R;

import butterknife.ButterKnife;

/**
 * Created by andiisfh on 01/07/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private boolean hasBack = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResource());
        ButterKnife.bind(this);

        settingToolbar(setIconColor(), setTitle(), isToolbarHasBack());

        hasBack = isToolbarHasBack();

        onViewReady(savedInstanceState);
    }

    protected abstract int setLayoutResource();

    protected abstract String setTitle();

    protected abstract int setIconColor();

    protected abstract boolean isToolbarHasBack();

    private void settingToolbar(int iconColor, String title, boolean hasBack) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);

            if (hasBack) {
                toolbarBackDrawable(iconColor);
            }
        }
    }

    private void toolbarBackDrawable(int color) {
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back);
        upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected abstract void onViewReady(Bundle savedInstanceState);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (hasBack) {
                    onBackPressed();
                }
                break;

        }

        return true;
    }
}
