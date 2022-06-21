package com.my_ads

import android.app.Activity
import android.content.Context
import com.facebook.ads.NativeAdLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.facebook.ads.InterstitialAd as InterstitialAdFb
import com.facebook.ads.NativeAd as NativeAdFb

const val PRIORITY_GOOGLE = 0
const val PRIORITY_FACEBOOK = 1
const val PRIORITY_GOOGLE_THEN_FACEBOOK = 2
const val PRIORITY_FACEBOOK_THEN_GOOGLE = 3


fun Context.toastAds(message: String) {
    if (BuildConfig.DEBUG) toast(message)
}

fun printAdsLog(message: String) {
    if (BuildConfig.DEBUG) println(message)
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////  INTERSTITIAL ADs  /////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//region Interstitial Ads

fun Context.loadInterstitialAd(
    adMobAdId: String,
    fbAdId: String,
    priority: Int,
    onAdLoaded: (Any?) -> Unit,
    onAdFailCallback: (Any?) -> Unit,
    onAdCloseCallback: () -> Unit
) {
    if (isInternetConnected()) {
        when ((priority)) {

            PRIORITY_GOOGLE -> {
                toastAds("Loading admob interstitial ad")
                printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadingAdMobAd")

                loadAdMobInterstitialAd(
                    adMobAdId,
                    {
                        if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadedAdMobAd")
                        onAdLoaded(it)
                    },
                    {
                        if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->FailedAdMobAd")
                        onAdFailCallback(it)
                    }
                )

            }
            PRIORITY_FACEBOOK -> {
                toastAds("Loading fb interstitial ad")
                printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadingFbAd")

                loadFacebookInterstitialAd(
                    fbAdId,
                    {
                        if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadedFbAd")
                        onAdLoaded(it)
                    },
                    {
                        if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->FailedFbAd")
                        onAdFailCallback(it)
                    },
                    onAdCloseCallback
                )
            }
            PRIORITY_GOOGLE_THEN_FACEBOOK -> {
                toastAds("Loading admob interstitial ad")
                printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadingAdMobAd")

                loadAdMobInterstitialAd(
                    adMobAdId,
                    {
                        if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadedAdMobAd")
                        onAdLoaded(it)
                    },
                    { error ->
                        toastAds("Loading Fb interstitial ad")
                        printAdsLog("InterstitialAdsManager->newInterstitialAd()->FailedAdMobAd: ${(error as LoadAdError).message}")
                        printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadingFbAd")

                        loadFacebookInterstitialAd(
                            fbAdId,
                            {
                                if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadedFbAd")
                                onAdLoaded(it)
                            },
                            {
                                if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->FailedFbAd")
                                onAdFailCallback(it)
                            },
                            onAdCloseCallback
                        )
                    }
                )
            }
            PRIORITY_FACEBOOK_THEN_GOOGLE -> {
                toastAds("Loading fb interstitial ad")
                printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadingAdMobAd")

                loadFacebookInterstitialAd(
                    fbAdId,
                    {
                        if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadedFbAd")
                        onAdLoaded(it)
                    },
                    {
                        toastAds("Loading admob interstitial ad")
                        printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadingAdMobAd")
                        printAdsLog("InterstitialAdsManager->newInterstitialAd()->FailedFbAd")

                        loadAdMobInterstitialAd(
                            adMobAdId,
                            {
                                if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->LoadedAdMobAd")
                                onAdLoaded(it)
                            },
                            {
                                if (BuildConfig.DEBUG) printAdsLog("InterstitialAdsManager->newInterstitialAd()->FailedAdMobAd")
                                onAdFailCallback(it)
                            }
                        )
                    },
                    onAdCloseCallback
                )
            }

        }
    }
}


fun Activity.showInterstitialAd(
    interstitialAd: Any?,
    onAdCloseCallback: () -> Unit
) {

    if (interstitialAd is InterstitialAd) showAdmobInterstitialAd(interstitialAd, onAdCloseCallback)
    else if (interstitialAd is InterstitialAdFb) showFacebookInterstitialAd(interstitialAd)

}


//endregion


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////  BANNER ADs  /////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//region Banner Ads
/*

private fun performAction(success: Boolean, container: ViewGroup?, shimmer: ViewGroup?) {
    try {
        if (container != null) container.visibility = if (success) View.VISIBLE else View.GONE
        shimmer?.hide()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.loadBannerAd(
    container: ViewGroup?, shimmer: ViewGroup?,
adSize: AdSize,
 adSizeFb: AdSizeFb,
    adMobAdId: String, fbAdId: String,
    priority: Int
) {
    if (isInternetConnected()) {
        when (priority) {
            PRIORITY_GOOGLE ->
                loadAdMobBanner(container,
adSize,
 adMobAdId) { performAction(it, container, shimmer) }
            PRIORITY_FACEBOOK ->
                loadFacebookBanner(container, adSizeFb, fbAdId) { performAction(it, container, shimmer) }
            PRIORITY_GOOGLE_THEN_FACEBOOK ->
                loadAdMobBanner(container,
adSize,
 adMobAdId) { isAdLoaded ->
                    if (isAdLoaded) {
                        performAction(true, container, shimmer)
                    } else {
                        loadFacebookBanner(container, adSizeFb, fbAdId) { performAction(it, container, shimmer)}
                    }
                }
            PRIORITY_FACEBOOK_THEN_GOOGLE ->
                loadFacebookBanner(container, adSizeFb, fbAdId) { isAdLoaded ->
                    if (isAdLoaded) {
                        performAction(true, container, shimmer)
                    } else {
                        loadAdMobBanner(container,
adSize,
 adMobAdId) { performAction(it, container, shimmer)}
                    }
                }
        }
    }
}

private fun Context.loadAdMobBanner(
    container: ViewGroup?,
adSize: AdSize,

    adId: String,
    listener: (Boolean) -> Unit
) {
    try {
        listener(false)
if (container == null) return
        val adView = AdView(this)
        adView.adSize = adSize
        adView.adUnitId = adId
        val adRequest = AdRequest.Builder().build()
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                listener(true)
                printAdsLog("BannerAdsManager->loadAdMobBanner()->onAdLoaded()->Success" )
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                listener(false)
                printAdsLog("BannerAdsManager->loadAdMobBanner()->onAdFailedToLoad()->${loadAdError.message}" )
            }

            override fun onAdOpened() = Unit

            override fun onAdClosed() = Unit
        }
        adView.loadAd(adRequest)
        container.removeAllViews()
        container.addView(adView)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.loadFacebookBanner(
    container: ViewGroup?,
    adSize: AdSizeFb,
    fbAdId: String,
    listener:  (Boolean) -> Unit
) {
    try {
        if (container == null) return
        val adView = AdViewFb(this, fbAdId, adSize)
        val adListener = object : AdListenerFb {
            override fun onAdLoaded(ad: Ad?) {
                listener(true)
                printAdsLog("BannerAdsManager->loadFacebookBanner($fbAdId)->onAdLoaded()->Success")
            }

            override fun onError(ad: Ad?, adError: AdError?) {
                listener(false)
                printAdsLog("BannerAdsManager->loadFacebookBanner($fbAdId)->onError()->${adError?.errorMessage}")
            }

            override fun onAdClicked(ad: Ad?) = Unit
            override fun onLoggingImpression(ad: Ad?) = Unit

        }
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build())
        container.removeAllViews()
        container.addView(adView)
    } catch (ex: Exception) {
        ex.printStackTrace()
        printAdsLog("exception: $ex")
    }
}
*/

//endregion


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////  NATIVE ADs  /////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//region Native Ads


fun Context.loadNativeAd(
    adMobAdId: String,
    fbAdId: String,
    priority: Int,
    shimmer: ShimmerFrameLayout?,
    onAdLoadedCallback: (Any?) -> Unit,
    onAdFailedCallback: (Any?) -> Unit
) {
    if (isInternetConnected()) {
        when (priority) {
            PRIORITY_GOOGLE -> {
                toastAds("Loading admob native ad")
                printAdsLog("NativeAdsManager->loadingAdmobNativeAd")
                loadAdmobNativeAd(adMobAdId, onAdLoadedCallback) {
                    shimmer?.hideShimmer()
                    shimmer?.hide()
                }
            }
            PRIORITY_FACEBOOK -> {
                toastAds("Loading fb native ad")
                printAdsLog("NativeAdsManager->loadingFacebookNativeAd~$fbAdId")
                loadFacebookNativeAd(fbAdId, onAdLoadedCallback) {
                    onAdFailedCallback(it)
                    shimmer?.hideShimmer()
                    shimmer?.hide()
                }
            }
            PRIORITY_GOOGLE_THEN_FACEBOOK -> {
                toastAds("Loading admob native ad")
                printAdsLog("NativeAdsManager->loadingAdmobNativeAd")
                loadAdmobNativeAd(adMobAdId, onAdLoadedCallback) {
                    toastAds("Loading fb native ad")
                    printAdsLog("NativeAdsManager->loadingFacebookNativeAd~$fbAdId")
                    loadFacebookNativeAd(fbAdId, {
                        printAdsLog("fb native ad loaded")
                        onAdLoadedCallback(it)
                    }) {
                        onAdFailedCallback(it)
                        shimmer?.hideShimmer()
                        shimmer?.hide()
                    }
                }
            }
            PRIORITY_FACEBOOK_THEN_GOOGLE -> {
                toastAds("Loading fb native ad")
                printAdsLog("NativeAdsManager->loadingFacebookNativeAd~$fbAdId")
                loadFacebookNativeAd(fbAdId, onAdLoadedCallback) {
                    toastAds("Loading admob native ad")
                    printAdsLog("NativeAdsManager->loadingAdmobNativeAd")
                    loadAdmobNativeAd(adMobAdId, onAdLoadedCallback) {
                        onAdFailedCallback(it)
                        shimmer?.hideShimmer()
                        shimmer?.hide()
                    }
                }
            }
        }
    }
    else {
        onAdFailedCallback("No internet available.")
        shimmer?.hideShimmer()
        shimmer?.hide()
    }
}



fun Context.showNativeAd(
    nativeAd: Any,
    adView: NativeAdView,
    nativeAdLayout: NativeAdLayout,
    shimmer: ShimmerFrameLayout,
    largeBanner: Boolean = false,
    isDarkMode: Boolean = true
) {
    if (nativeAd is NativeAd) {
        when {
            largeBanner -> showAdmobLargeNativeAd(nativeAd, adView, shimmer)
            else -> showAdmobSmallNativeAd(nativeAd, adView, shimmer, isDarkMode)
        }
    }
    else if (nativeAd is NativeAdFb) {
        if (nativeAd.isAdLoaded && nativeAd.isAdInvalidated.not()) {
            if (largeBanner) showFacebookLargeNativeAd(nativeAd, nativeAdLayout, shimmer)
            else showFacebookSmallNativeAd(nativeAd, nativeAdLayout, shimmer)
        }
    }
}



//endregion

