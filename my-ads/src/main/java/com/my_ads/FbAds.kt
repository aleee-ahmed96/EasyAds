package com.my_ads

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.facebook.ads.*
import com.facebook.shimmer.ShimmerFrameLayout


//region InterstitialAd


internal fun Context.loadFacebookInterstitialAd(
    adId: String,
    onAdLoadedCallback: (Any?) -> Unit,
    onAdFailedCallback: (Any?) -> Unit,
    onAdCloseCallback: () -> Unit) {

    val fbAd = InterstitialAd(this, adId)
    val listener = object : InterstitialAdListener {

        override fun onError(p0: Ad?, error: AdError?) {
            onAdFailedCallback(error?.errorMessage ?: "Unknown error occurred")
        }

        override fun onAdLoaded(ad: Ad?) {
            onAdLoadedCallback(ad)
        }

        override fun onAdClicked(p0: Ad?) = Unit

        override fun onLoggingImpression(p0: Ad?) = Unit

        override fun onInterstitialDisplayed(p0: Ad?) = Unit

        override fun onInterstitialDismissed(ad: Ad?) {
            onAdCloseCallback()
        }
    }
    fbAd.loadAd(fbAd.buildLoadAdConfig().withAdListener(listener).build())

}


internal fun showFacebookInterstitialAd(
    interstitialAd: InterstitialAd
) {
    if (interstitialAd.isAdLoaded && interstitialAd.isAdInvalidated.not()) interstitialAd.show()
}


//endregion



//region NativeAd


internal fun Context.loadFacebookNativeAd(
    fbAdId: String,
    onAdLoadedCallback: (Any?) -> Unit,
    onAdFailedCallback: (Any?) -> Unit
) {

    val nativeAd = NativeAd(this, fbAdId)
    val listener = object : NativeAdListener {
        override fun onError(p0: Ad?, p1: AdError?) {
            printAdsLog("NativeAdsManager->loadFbNativeAd->onAdFailed~$fbAdId: ${p1?.errorMessage}")
            onAdFailedCallback(p1?.errorMessage ?: "Unknown error occurred")
        }

        override fun onAdLoaded(p0: Ad?) {
            onAdLoadedCallback(nativeAd)
            printAdsLog("NativeAdsManager->loadFbNativeAd->onAdLoaded: $fbAdId")
//            showFacebookNativeAd(nativeAd, nativeAdLayout, shimmer)
        }

        override fun onAdClicked(p0: Ad?) {
            printAdsLog("NativeAdsManager->loadFbNativeAd->onAdClicked")
        }

        override fun onLoggingImpression(p0: Ad?) = Unit

        override fun onMediaDownloaded(p0: Ad?) = Unit
    }
    nativeAd.loadAd(
        nativeAd
            .buildLoadAdConfig()
            .withAdListener(listener)
            .build()
    )
}

internal fun Context.showFacebookSmallNativeAd(
    nativeAd: NativeAd,
    nativeAdLayout: NativeAdLayout,
    shimmer: ShimmerFrameLayout
) {
    try {

        nativeAd.unregisterView()
        nativeAdLayout.removeAllViews()

        val inflater = LayoutInflater.from(this)
        val adView = inflater.inflate(R.layout.layout_native_ad_small_facebook, nativeAdLayout, false) as LinearLayout
        nativeAdLayout.addView(adView)

        val adChoicesContainer: RelativeLayout = adView.findViewById(R.id.ad_choices_container)
        val adOptionsView = AdOptionsView(this, nativeAd, nativeAdLayout)
        adChoicesContainer.removeAllViews()
        adChoicesContainer.addView(adOptionsView, 0)

        val nativeAdIcon: MediaView = adView.findViewById(R.id.native_icon_view)
        val nativeAdTitle: TextView = adView.findViewById(R.id.native_ad_title)
        val nativeAdSocialContext: TextView = adView.findViewById(R.id.native_ad_social_context)
        val nativeAdCallToAction: Button = adView.findViewById(R.id.native_ad_call_to_action)

        nativeAdTitle.text = nativeAd.advertiserName
        nativeAdSocialContext.text = nativeAd.adSocialContext
        nativeAdCallToAction.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction

        val clickableViews: MutableList<View> = ArrayList()
//        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        nativeAd.registerViewForInteraction(adView, nativeAdIcon, clickableViews)

        nativeAdLayout.show()
        shimmer.hideShimmer()
        shimmer.hide()

    } catch (e: Exception) {
        e.printStackTrace()
        nativeAdLayout.hide()
        shimmer.hideShimmer()
        shimmer.hide()
    }
}

internal fun Context.showFacebookLargeNativeAd(
    nativeAd: NativeAd,
    nativeAdLayout: NativeAdLayout,
    shimmer: ShimmerFrameLayout
) {
    try {

        nativeAd.unregisterView()
        nativeAdLayout.removeAllViews()

        val inflater = LayoutInflater.from(this)
        val adView = inflater.inflate(R.layout.layout_native_ad_large_facebook, nativeAdLayout, false) as LinearLayout
        nativeAdLayout.addView(adView)

        val adChoicesContainer: RelativeLayout = adView.findViewById(R.id.ad_choices_container)
        val adOptionsView = AdOptionsView(this, nativeAd, nativeAdLayout)
        adChoicesContainer.removeAllViews()
        adChoicesContainer.addView(adOptionsView, 0)

        val nativeAdIcon: MediaView = adView.findViewById(R.id.native_icon_view)
        val nativeAdTitle: TextView = adView.findViewById(R.id.native_ad_title)
        val nativeAdCallToAction: Button = adView.findViewById(R.id.native_ad_call_to_action)
        val cardCallToAction: CardView = adView.findViewById(R.id.cardCallToAction)

        nativeAdTitle.text = nativeAd.advertiserName
        cardCallToAction.visibility = if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction

        val clickableViews: MutableList<View> = ArrayList()
//        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        nativeAd.registerViewForInteraction(adView, nativeAdIcon, clickableViews)

        nativeAdLayout.show()
        shimmer.hideShimmer()
        shimmer.hide()

    } catch (e: Exception) {
        e.printStackTrace()
        nativeAdLayout.hide()
        shimmer.hideShimmer()
        shimmer.hide()
    }
}

//endregion