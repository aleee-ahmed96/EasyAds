package com.my_ads

enum class AdsPriority(val value: Int) {
    GOOGLE(0),
    FACEBOOK(1),
    GOOGLE_THEN_FACEBOOK(2),
    FACEBOOK_THEN_GOOGLE(3),
}