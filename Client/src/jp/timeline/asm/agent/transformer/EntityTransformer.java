package jp.timeline.asm.agent.transformer;

import javassist.CtClass;
import javassist.CtMethod;
import jp.timeline.EventSystem.EventManager;

import javax.swing.*;

public class EntityTransformer extends MasterTransformer {
    public EntityTransformer() {
        super("pk");
    }

    @Override
    public CtClass transform(ClassLoader loader, String className, Class<?> classBeingRedefined, CtClass cls) throws Exception {
        System.out.println("Find Entity Hook");

        CtMethod hitbox = cls.getDeclaredMethod("ao");

        // this a hitbox
        hitbox.setBody("return 1.0F;");

        return cls;
    }
}
