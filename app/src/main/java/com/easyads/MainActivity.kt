package com.easyads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easyads.databinding.ActivityMainBinding
import com.my_ads.*

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        loadNativeAd(
            "ca-app-pub-3940256099942544/2247696110",
            "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
            AdsPriority.GOOGLE_THEN_FACEBOOK.value,
            binding?.includedAdLayout?.shimmer,
            { ad ->
                showNativeAd(
                    ad!!,
                    binding?.includedAdLayout?.includedAdmobLayout?.nativeAdView!!,
                    binding?.includedAdLayout?.includedFacebookLayout?.nativeAdContainerList!!,
                    binding?.includedAdLayout?.shimmer!!
                )
            },
            { error ->
                toast("Native Ad failed to load -> $error")
            }
        )


        loadInterstitialAd(
            "",
            "",

            AdsPriority.FACEBOOK_THEN_GOOGLE.value,
            { ad ->
                showInterstitialAd(ad) {
                    toast("Interstitial Ad closed")
                }
            },
            { error ->
                toast("Interstitial Ad failed to load -> $error")
            },
            {
                toast("Interstitial Ad closed")
            }
        )


    }

}