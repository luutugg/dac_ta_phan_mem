package com.example.dactaphanmem.common.eventbus

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

interface IEventHandler {
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEvent(event: IEvent)
}
