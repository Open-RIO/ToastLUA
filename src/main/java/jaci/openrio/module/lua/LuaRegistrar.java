package jaci.openrio.module.lua;

import jaci.openrio.module.lua.libs.LibController;
import jaci.openrio.module.lua.libs.LibLog;
import jaci.openrio.module.lua.libs.LibMotor;
import jaci.openrio.module.lua.libs.LibState;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.List;

/**
 * The registration class for LUA Libraries to be loaded into Globals. Register your libraries here
 *
 * @author Jaci
 */
public class LuaRegistrar {

    static List<LuaValue> libraries = new ArrayList<>();

    /**
     * Register a {@link jaci.openrio.module.lua.LuaLibrary} to the registry.
     */
    public static void registerLibrary(LuaValue lib) {
        libraries.add(lib);
    }

    /**
     * Apply the Library bindings to a Globals instance
     */
    public static void applyLibraries(Globals globals) {
        for (LuaValue val : libraries)
            globals.load(val);
    }

    static void register_defaults() {
        registerLibrary(new LibLog());
        registerLibrary(new LibMotor());
        registerLibrary(new LibState());
        registerLibrary(new LibController());
    }

}
