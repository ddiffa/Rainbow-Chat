package com.example.rainbow_chat

import android.app.Application
import com.ale.rainbowsdk.RainbowSdk

class RainbowChatApp : Application() {


    override fun onCreate() {
        super.onCreate()
        RainbowSdk.instance().initialize(
            this,
            "96ef6e9050e811eaa494f37dafb65619",
            "DFlsaYoi6YzCPuHcjWoahHrgEwEnK5IFYHytIANiD3lZqZ2O8zadnL4DyRPYRStv"
        )
    }


}