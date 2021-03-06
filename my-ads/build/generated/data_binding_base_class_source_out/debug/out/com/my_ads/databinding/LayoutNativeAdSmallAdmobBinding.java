// Generated by view binder compiler. Do not edit!
package com.my_ads.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.my_ads.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LayoutNativeAdSmallAdmobBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final MediaView adMedia;

  @NonNull
  public final AppCompatTextView btnRedirection;

  @NonNull
  public final CardView cardRedirection;

  @NonNull
  public final CardView cvLogoOuter;

  @NonNull
  public final AppCompatImageView ivAdImg;

  @NonNull
  public final LinearLayout llNativeAd;

  @NonNull
  public final NativeAdView nativeAdView;

  @NonNull
  public final CardView parentLayout;

  @NonNull
  public final TextView tvAdDesc;

  @NonNull
  public final TextView tvAdSplash;

  @NonNull
  public final TextView tvAdTitle;

  private LayoutNativeAdSmallAdmobBinding(@NonNull CardView rootView, @NonNull MediaView adMedia,
      @NonNull AppCompatTextView btnRedirection, @NonNull CardView cardRedirection,
      @NonNull CardView cvLogoOuter, @NonNull AppCompatImageView ivAdImg,
      @NonNull LinearLayout llNativeAd, @NonNull NativeAdView nativeAdView,
      @NonNull CardView parentLayout, @NonNull TextView tvAdDesc, @NonNull TextView tvAdSplash,
      @NonNull TextView tvAdTitle) {
    this.rootView = rootView;
    this.adMedia = adMedia;
    this.btnRedirection = btnRedirection;
    this.cardRedirection = cardRedirection;
    this.cvLogoOuter = cvLogoOuter;
    this.ivAdImg = ivAdImg;
    this.llNativeAd = llNativeAd;
    this.nativeAdView = nativeAdView;
    this.parentLayout = parentLayout;
    this.tvAdDesc = tvAdDesc;
    this.tvAdSplash = tvAdSplash;
    this.tvAdTitle = tvAdTitle;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static LayoutNativeAdSmallAdmobBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayoutNativeAdSmallAdmobBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.layout_native_ad_small_admob, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayoutNativeAdSmallAdmobBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ad_media;
      MediaView adMedia = rootView.findViewById(id);
      if (adMedia == null) {
        break missingId;
      }

      id = R.id.btnRedirection;
      AppCompatTextView btnRedirection = rootView.findViewById(id);
      if (btnRedirection == null) {
        break missingId;
      }

      id = R.id.cardRedirection;
      CardView cardRedirection = rootView.findViewById(id);
      if (cardRedirection == null) {
        break missingId;
      }

      id = R.id.cvLogoOuter;
      CardView cvLogoOuter = rootView.findViewById(id);
      if (cvLogoOuter == null) {
        break missingId;
      }

      id = R.id.ivAdImg;
      AppCompatImageView ivAdImg = rootView.findViewById(id);
      if (ivAdImg == null) {
        break missingId;
      }

      id = R.id.llNativeAd;
      LinearLayout llNativeAd = rootView.findViewById(id);
      if (llNativeAd == null) {
        break missingId;
      }

      id = R.id.nativeAdView;
      NativeAdView nativeAdView = rootView.findViewById(id);
      if (nativeAdView == null) {
        break missingId;
      }

      CardView parentLayout = (CardView) rootView;

      id = R.id.tvAdDesc;
      TextView tvAdDesc = rootView.findViewById(id);
      if (tvAdDesc == null) {
        break missingId;
      }

      id = R.id.tvAdSplash;
      TextView tvAdSplash = rootView.findViewById(id);
      if (tvAdSplash == null) {
        break missingId;
      }

      id = R.id.tvAdTitle;
      TextView tvAdTitle = rootView.findViewById(id);
      if (tvAdTitle == null) {
        break missingId;
      }

      return new LayoutNativeAdSmallAdmobBinding((CardView) rootView, adMedia, btnRedirection,
          cardRedirection, cvLogoOuter, ivAdImg, llNativeAd, nativeAdView, parentLayout, tvAdDesc,
          tvAdSplash, tvAdTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
