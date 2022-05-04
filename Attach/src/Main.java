import com.sun.tools.attach.*;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.File;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        File agent = new File("C:\\agent.jar");

        if (!agent.exists()) {
            System.err.println("Agent doesn't exist");
            return;
        }

        Optional<VirtualMachineDescriptor> first = VirtualMachine.list()
                .stream()
                .filter(vm_desc -> check(vm_desc.displayName()))
                .findFirst();

        if (!first.isPresent()) {
            System.err.println("Minecraft session wasn't found");
            //ByteBuddyAgent.attach(agent, first.get());
            return;
        }

        ByteBuddyAgent.attach(agent, first.get().id());

        /*
        VirtualMachine attach = VirtualMachine.attach(first.get());

        System.out.println("SUCCESS!");

        try {
            attach.loadAgent(agent.getAbsolutePath());
            attach.detach();
            System.out.println("SUCCESS!");
        } catch (AgentLoadException e) {
            System.err.println("Agent failed to load: ");
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            System.err.println("Agent loaded, but failed to initialize: ");
            e.printStackTrace();
        }
         */
    }

    public static boolean check(String displayName)
    {
        //                                      Vanilla                                                          OptiFine
        return displayName.startsWith("net.minecraft.client.main.Main") || displayName.startsWith("net.minecraft.launchwrapper.Launch");
    }
}
