import com.sun.tools.attach.*;
import joptsimple.*;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Run {
    public static void main(String[] args) throws IOException, AttachNotSupportedException {
        OptionParser optionParser = new OptionParser();

        AbstractOptionSpec<Void> help = optionParser.accepts("help").forHelp();

        joptsimple.OptionSet optionSet;

        try {
            optionSet = optionParser.parse(args);
        } catch (OptionException e) {
            System.err.println("Failed to parse options: " + e.getMessage() + " (try --help)");
            return;
        }

        if (optionSet.has(help)) {
            optionParser.printHelpOn(System.err);
            return;
        }

        File agent = new File("C:\\Agent.jar");

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
            return;
        }

        VirtualMachine attach = VirtualMachine.attach(first.get());

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
    }

    public static boolean check(String displayName)
    {
        //                                      Vanilla                                                          OptiFine
        return displayName.startsWith("net.minecraft.client.main.Main") || displayName.startsWith("net.minecraft.launchwrapper.Launch");
    }
}
