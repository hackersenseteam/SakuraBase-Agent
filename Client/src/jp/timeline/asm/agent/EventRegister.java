package jp.timeline.asm.agent;

import jp.timeline.EventSystem.EventManager;
import jp.timeline.EventSystem.events.TickEvent;

public class EventRegister {
    public static void callTick()
    {
        EventManager.call(new TickEvent());
    }
}
