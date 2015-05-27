package jaci.openrio.module.lua;

import jaci.openrio.toast.core.command.AbstractCommand;
import jaci.openrio.toast.core.command.CommandBus;
import org.luaj.vm2.LuaValue;

/**
 * The Command class to use
 */
public class CommandLUA extends AbstractCommand {

    @Override
    public String getCommandName() {
        return "lua";
    }

    @Override
    public void invokeCommand(int argLength, String[] args, String command) {
        boolean running = true;
        while (running) {
            try {
                System.out.print("> ");
                String ln = CommandBus.requestNextMessage();
                if (ln.equals("exit")) {
                    running = false;
                } else {
                    try {
                        LuaValue val = ToastLUA.luaGlobals.load(ln);
                        LuaValue ret = val.call();
                        if (ret != null)
                            System.out.println("=> " + ret);
                    } catch (Exception e) {
                        System.err.println("Compilation Error: " + e);
                    }
                }
            } catch (InterruptedException e) {
                running = false;
                e.printStackTrace();
            }
        }
    }
}
