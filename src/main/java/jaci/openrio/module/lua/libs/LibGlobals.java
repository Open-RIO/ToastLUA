package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaFiles;
import jaci.openrio.module.lua.ToastLUA;
import jaci.openrio.toast.core.Environment;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import static org.luaj.vm2.LuaValue.valueOf;

/**
 * A small set of global variables stored in the Lua instance, including functions
 *
 * @author Jaci
 */
public class LibGlobals {

    public static void init() {
        ToastLUA.luaGlobals.set("require", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaFiles.loadRelativeFile(arg.checkjstring()).call();
            }
        });

        setupEnvironment();
    }

    public static void setupEnvironment() {
        LuaTable table = new LuaTable();
        table.set("verification", valueOf(Environment.isVerification()));
        table.set("simulation", valueOf(Environment.isSimulation()));
        table.set("embedded", valueOf(Environment.isEmbedded()));
        table.set("type", valueOf(Environment.getEnvironmentalType()));
        table.set("toast_home", valueOf(Environment.getHome().getAbsolutePath()));
        table.set("os", valueOf(Environment.getOS().toString()));
        table.set("os_arch", valueOf(Environment.getOS_Architecture()));
        table.set("java_vendor", valueOf(Environment.getJava_vendor()));
        table.set("java_home", valueOf(Environment.getJava_home()));
        table.set("java_version", valueOf(Environment.getJava_version()));
        ToastLUA.luaGlobals.set("Env", table);
    }

}
