package jp.timeline.asm.agent.transformer;

import javassist.CtClass;
import javassist.CtMethod;

public class MinecraftTransformer extends MasterTransformer {
    public MinecraftTransformer() {
        super("ave");
    }

    @Override
    public CtClass transform(ClassLoader loader, String className, Class<?> classBeingRedefined, CtClass cls) throws Exception {
        System.out.println("Find Minecraft Hook");
        pool.importPackage("java.lang.reflect.Method");
        // this a runTick

        CtMethod tick = cls.getDeclaredMethod("s");

        // this broken
        //tick.insertBefore(
        //        "        Class eventRegister = Class.forName(\"jp.timeline.asm.agent.EventRegister\");\n" +
        //                "        if (!eventRegister.getDeclaredMethod(\"callTick\", null).isAccessible())\n" +
        //                "            eventRegister.getDeclaredMethod(\"callTick\", null).setAccessible(true);\n" +
        //                "        eventRegister.getDeclaredMethod(\"callTick\", null).invoke(eventRegister, null);"
        //);

        // this a shutDown
        CtMethod shutdown = cls.getDeclaredMethod("m");

        shutdown.insertBefore("System.out.println(\"SHUTDOWN\");");

        return cls;
    }
}