package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.module.lua.ToastLUA;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class LibLog extends LuaLibrary {

    public LibLog() {
        super("Log");

        registerFunction("info", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                ToastLUA.logger.info(arg.checkjstring());
                return null;
            }
        });
        registerFunction("error", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                ToastLUA.logger.error(arg.checkjstring());
                return null;
            }
        });
        registerFunction("warn", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                ToastLUA.logger.warn(arg.checkjstring());
                return null;
            }
        });
    }

}
