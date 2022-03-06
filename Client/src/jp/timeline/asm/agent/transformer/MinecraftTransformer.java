package jp.timeline.asm.agent.transformer;

import javassist.CtClass;
import javassist.CtMethod;

public class MinecraftTransformer extends MasterTransformer {
    public MinecraftTransformer() {
        super("ave");
    }

    @Override
    public CtClass transform(ClassLoader loader, String className, Class<?> classBeingRedefined, CtClass cls) throws Exception {
        System.out.println("Find Minecraft");
        
        // this a runTick
        CtMethod tick = cls.getDeclaredMethod("s");

        // this a shutDown
        CtMethod shutdown = cls.getDeclaredMethod("m");

        shutdown.insertBefore("System.out.println(\"SHUTDOWN\");");

        return cls;
    }
}
