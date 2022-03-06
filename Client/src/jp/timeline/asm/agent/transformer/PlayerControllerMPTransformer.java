package jp.timeline.asm.agent.transformer;

import javassist.CtClass;
import javassist.CtMethod;

public class PlayerControllerMPTransformer extends MasterTransformer {
    public PlayerControllerMPTransformer() {
        super("bda");
    }

    @Override
    public CtClass transform(ClassLoader loader, String className, Class<?> classBeingRedefined, CtClass cls) throws Exception {
        System.out.println("Find PlayerControllerMP");

        CtMethod blockReach = cls.getDeclaredMethod("d");

        // this a blockreach
        blockReach.setBody("return 7.0F;");

        return cls;
    }
}
