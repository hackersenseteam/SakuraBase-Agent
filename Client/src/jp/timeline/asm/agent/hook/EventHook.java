package jp.timeline.asm.agent.hook;

import jp.timeline.EventSystem.EventListener;

public class EventHook {
    @EventListener
    public void onTick()
    {
        System.out.println("SUCCESS");
    }
}
