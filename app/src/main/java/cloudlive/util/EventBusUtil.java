package cloudlive.util;


import org.greenrobot.eventbus.EventBus;

import cloudlive.entity.Event;

/**
 * Created by ccy on 2017/11/13.
 */

public class EventBusUtil {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void postEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void postStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }

}
