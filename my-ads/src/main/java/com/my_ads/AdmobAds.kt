package com.my_ads

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


//region InterstitialAd


internal fun Context.loadAdMobInterstitialAd(
    adId: String,
    adLoaded: (Any) -> Unit,
    adLoadFailed: (Any) -> Unit
) {
    try {
        InterstitialAd.load(
            this,
            adId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    adLoaded(interstitialAd)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    adLoadFailed(loadAdError.message)
                }
            }
        )
    }
    catch (ex: Exception) {
        ex.printStackTrace()
    }
}

internal fun Activity.showAdmobInterstitialAd(
    interstitialAd: InterstitialAd,
    onAdCloseCallback: () -> Unit
) {
    val listener = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            onAdCloseCallback
        }
    }
    interstitialAd.fullScreenContentCallback = listener
    interstitialAd.show(this)
}


//endregion


//region NativeAd


internal fun Context.loadAdmobNativeAd(
    adMobAdId: String,
    onAdLoadedCallback: (Any?) -> Unit,
    onAdFailedCallback: (Any?) -> Unit
) {
//    onAdFailedCallback(null)
    val builder = AdLoader.Builder(this, adMobAdId)

    val videoOptions = VideoOptions.Builder().setStartMuted(true).build()

    val options = NativeAdOptions.Builder()
        .setVideoOptions(videoOptions)
        .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
        .setMediaAspectRatio(NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_LANDSCAPE)
        .build()

    builder.withNativeAdOptions(options)

    builder.forNativeAd { onAdLoadedCallback(it) }

    builder.withAdListener(object : AdListener() {
        override fun onAdLoaded() {
            printAdsLog("NativeAdsManager->loadAdmobNativeAd->onAdLoaded")
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
            printAdsLog("NativeAdsManager->loadAdmobNativeAd->onAdFailedToLoad: ${error.message}")
            onAdFailedCallback(error.message)
        }
    })

    val adLoader = builder.build()

    adLoader.loadAd(AdRequest.Builder().build())
}


internal fun showAdmobSmallNativeAd(
    nativeAd: NativeAd,
    adView: NativeAdView,
    shimmer: ShimmerFrameLayout,
    isDarkMode: Boolean
) {
    try {

        adView.headlineView = adView.findViewById(R.id.tvAdTitle)
        adView.bodyView = adView.findViewById(R.id.tvAdDesc)
        adView.callToActionView = adView.findViewById(R.id.btnRedirection)
        adView.iconView = adView.findViewById(R.id.ivAdImg)
        val details = adView.findViewById<View>(R.id.llNativeAd)
        val cvLogoOuter = adView.findViewById<View>(R.id.cvLogoOuter)

        try {
            adView.mediaView = adView.findViewById(R.id.ad_media)
            adView.mediaView?.setOnHierarchyChangeListener(object :
                ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View, child: View) {
                    try {
                        if (child is ImageView) {
                            child.adjustViewBounds = true
                            child.scaleType = ImageView.ScaleType.FIT_CENTER
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onChildViewRemoved(parent: View, child: View) {}
            })
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            if (isDarkMode) {
                (adView.parent as CardView).backgroundColor(R.color.large_ads_background_night)
                (adView.findViewById<CardView>(R.id.cvLogoOuter)).backgroundColor(R.color.large_ads_background_night)
                adView.iconView?.backgroundColor(R.color.large_ads_background_night)
                adView.mediaView?.backgroundColor(R.color.large_ads_background_night)
                adView.headlineView?.apply { (this as TextView).textColor(android.R.color.white) }
            } else {
                (adView.parent as CardView).backgroundColor(R.color.large_ads_background_light)
                (adView.findViewById<CardView>(R.id.cvLogoOuter)).backgroundColor(R.color.large_ads_background_light)
                adView.iconView?.backgroundColor(R.color.large_ads_background_light)
                adView.mediaView?.backgroundColor(R.color.large_ads_background_light)
                adView.headlineView?.apply { (this as TextView).textColor(android.R.color.black) }
            }

        }
        catch (e: Exception) {
            e.printStackTrace()
        }


        (adView.headlineView as TextView).text = nativeAd.headline

        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.INVISIBLE
        }
        else {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.INVISIBLE
        }
        else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as TextView).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            cvLogoOuter.hide()
        }
        else {
            (adView.iconView as ImageView).setImageDrawable(nativeAd.icon?.drawable)
            cvLogoOuter.show()
        }

        adView.setNativeAd(nativeAd)

        adView.show()

        shimmer.hideShimmer()
        shimmer.hide()

    }
    catch (e: Exception) {
        e.printStackTrace()
        adView.hide()

        shimmer.hideShimmer()
        shimmer.hide()
    }
}

internal fun showAdmobLargeNativeAd(
    nativeAd: NativeAd,
    adView: NativeAdView,
    shimmer: ShimmerFrameLayout
) {
    try {
        adView.headlineView = adView.findViewById(R.id.tvAdTitle)
        adView.bodyView = adView.findViewById(R.id.tvAdDesc)
        adView.callToActionView = adView.findViewById(R.id.btnRedirection)
        adView.iconView = adView.findViewById(R.id.ivAdImg)

        try {
            adView.mediaView = adView.findViewById(R.id.ad_media)
            adView.mediaView?.setOnHierarchyChangeListener(object :
                ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View, child: View) {
                    try {
                        if (child is ImageView) {
                            child.adjustViewBounds = true
                            child.scaleType = ImageView.ScaleType.CENTER_INSIDE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onChildViewRemoved(parent: View, child: View) {}
            })

            if (nativeAd.mediaContent == null) {
                adView.mediaView?.hide()
                adView.iconView?.show()
            }
            else {
                adView.iconView?.hide()
                adView.mediaView?.show()
                nativeAd.mediaContent?.let { adView.mediaView?.setMediaContent(it) }
            }

        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        (adView.headlineView as TextView).text = nativeAd.headline

        if (nativeAd.body == null) {
            adView.bodyView?.disappear()
        } else {
            (adView.headlineView as TextView).post {
                val lineCount = (adView.headlineView as TextView).lineCount
                if (lineCount <= 1) adView.bodyView?.show()
            }
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView?.disappear()
        } else {
            adView.callToActionView?.show()
            (adView.callToActionView as TextView).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView?.hide()
        } else {
            (adView.iconView as ImageView).setImageDrawable(nativeAd.icon?.drawable)
            adView.iconView?.show()
        }

        adView.setNativeAd(nativeAd)

        adView.show()

        shimmer.hideShimmer()
        shimmer.hide()

    }
    catch (e: Exception) {
        e.printStackTrace()
        adView.hide()

        shimmer.hideShimmer()
        shimmer.hide()
    }
}

//endregion


