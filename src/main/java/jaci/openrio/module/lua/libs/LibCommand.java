package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.toast.core.command.AbstractCommand;
import jaci.openrio.toast.core.command.CommandBus;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 * Simple library for using the Toast Command Bus from within Lua
 *
 * @author Jaci
 */
public class LibCommand extends LuaLibrary {

    public LibCommand() {
        super("Command");

        registerFunction("new", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                String name = arg1.checkjstring();
                LuaClosure closure = arg2.checkclosure();

                CommandBus.registerCommand(new AbstractCommand() {
                    @Override
                    public String getCommandName() {
                        return name;
                    }

                    @Override
                    public void invokeCommand(int argLength, String[] args, String command) {
                        LuaValue[] argTransform = new LuaValue[argLength];
                        for (int i = 0; i < argLength; i++) argTransform[i] = valueOf(args[i]);
                        closure.invoke(argTransform);
                    }
                });
                return null;
            }
        });
    }
}
