package jp.timeline.asm.agent;

import jp.timeline.EventSystem.EventManager;
import jp.timeline.asm.agent.hook.EventHook;
import jp.timeline.asm.agent.transformer.*;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.*;

public class AgentMain {
    public static final Map<String, MasterTransformer> classList = new HashMap<String, MasterTransformer>() {{
        put("ave", new MinecraftTransformer());
        put("bda", new PlayerControllerMPTransformer());
        put("pk", new EntityTransformer());
        put("bew", new EntityPlayerSPTransformer());
    }};

    public static void premain(String agentArgs, Instrumentation inst) {
        load(inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        load(inst);
    }

    private static void load(Instrumentation inst)
    {
        EventManager.addListener(new EventHook());

        try {
            System.out.println("Injected");

            Arrays.stream(inst.getAllLoadedClasses())
                    .filter(c -> classList.containsKey(c.getName()))
                    .forEach(cls -> {
                        try {
                            inst.addTransformer(classList.get(cls.getName()), true);
                            inst.retransformClasses(cls);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
