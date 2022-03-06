package jp.timeline.asm.agent.transformer;

import javassist.CtClass;
import javassist.CtMethod;

public class EntityPlayerSPTransformer extends MasterTransformer {
    public EntityPlayerSPTransformer() {
        super("bew");
    }

    @Override
    public CtClass transform(ClassLoader loader, String className, Class<?> classBeingRedefined, CtClass cls) throws Exception {
        System.out.println("Find EntityPlayerSP");

        CtMethod onUpdate = cls.getDeclaredMethod("p");

        // this a sprint
        onUpdate.insertBefore("if (cl().a() > 6 && !av() && b.b > 0) d(true);");

        return cls;
    }
}
