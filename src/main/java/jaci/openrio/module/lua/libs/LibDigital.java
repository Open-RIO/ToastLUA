package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.toast.lib.registry.Registrar;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 * Lua Library for Digital IO support
 *
 * @author Jaci
 */
public class LibDigital extends LuaLibrary {

    public LibDigital() {
        super("Digital");

        registerFunction("set", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                int port = arg1.checkint();
                boolean value = arg2.checkboolean();

                Registrar.digitalOutput(port).set(value);
                return null;
            }
        });

        registerFunction("get", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                int port = arg.checkint();
                return valueOf(Registrar.digitalInput(port).get());
            }
        });
    }
}
