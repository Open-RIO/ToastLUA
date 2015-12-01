package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.toast.core.thread.Async;
import jaci.openrio.toast.core.thread.Heartbeat;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * A Lua Library for accessing Toast's Threading utilities
 *
 * @author Jaci
 */
public class LibThreading extends LuaLibrary {

    public LibThreading() {
        super("Thread");

        registerFunction("async", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                int numargs = args.narg();
                LuaClosure closure = args.arg(numargs).checkclosure();
                Async.schedule(() -> {
                    closure.invoke(args);
                });
                return args;
            }
        });

        registerFunction("sleep", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                try {
                    Thread.sleep(arg.checklong());
                } catch (InterruptedException e) { }
                return null;
            }
        });

        registerFunction("heartbeat", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                Heartbeat.add(skipped -> {
                    closure.call(valueOf(skipped));
                });
                return null;
            }
        });
    }

}
