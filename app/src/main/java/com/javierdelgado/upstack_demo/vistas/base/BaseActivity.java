package com.javierdelgado.upstack_demo.vistas.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.javierdelgado.upstack_demo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseActivity extends AppCompatActivity {
    public static final int CLEAR_STACK = 1;
    private ViewGroup overlayContainerView;
    /**
     * Si autoRegisterBus es falso la activity no sera registrada del bus automaticamente
     */
    private boolean autoRegisterBus = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (autoRegisterBus) getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (autoRegisterBus) getBus().unregister(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //<editor-fold desc="Utility methods">
    protected void goToActivity(Class<? extends Activity> activityClass) {
        goToActivity(activityClass, -1);
    }

    protected void goToActivity(Class<? extends Activity> activityClass, int flag) {
        Intent intent = new Intent(this, activityClass);
        switch (flag) {
            case CLEAR_STACK:
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }
    //</editor-fold>

    //<editor-fold desc="Overlay methods">
    /**
     * Setea la vista sobre la que se mostrara el overlay para esta activity.
     * Si no se setea o se pasa null, el overlay se mostrara sobre toda la pantalla.
     * @param overlayContainerView la vista donde se mostrara el overlay
     */
    protected void setActivityOverlayContainer(ViewGroup overlayContainerView) {
        this.overlayContainerView = overlayContainerView;
    }

    protected void showActivityOverlay() {
        View overlay = findViewById(R.id.lytNetworkIndicatorOverlay);
        if (overlay == null) {
            overlay = createNetworkOverlay();
        }

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        overlay.startAnimation(anim);
        overlay.setVisibility(View.VISIBLE);
    }

    protected void hideActivityOverlay() {
        final View overlay = findViewById(R.id.lytNetworkIndicatorOverlay);
        if (overlay != null && overlay.getVisibility() == View.VISIBLE) {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    overlay.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            overlay.startAnimation(anim);
        }
    }

    private View createNetworkOverlay() {
        if (overlayContainerView == null) {
            overlayContainerView = (ViewGroup)(getWindow().getDecorView().getRootView());
        }
        View overlay = getLayoutInflater().inflate(R.layout.network_indicator_overlay, overlayContainerView, false);
        overlayContainerView.addView(
                overlay,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        );
        return overlay;
    }
    //</editor-fold>

    @SuppressWarnings("ConstantConditions")
    protected void showUpButton(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    protected EventBus getBus() {
        return EventBus.getDefault();
    }

    public void setAutoRegisterBus(boolean autoRegisterBus) {
        this.autoRegisterBus = autoRegisterBus;
    }

    /**
     * Este metodo evita el error de que no hay suscriptores de eventbus
     * @param event
     */
    @Subscribe
    public void dummyEvent(Void event) {}
}
