package jaci.openrio.module.lua;

import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.ToastModule;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * The Toast LUA module. This modules adds the ability to load the LUA lightweight programming language into
 * an FRC robotics environment. Modules that add ToastLUA as a dependency or dependency branch can add their
 * Library bindings to the LUA global environment.
 *
 * @author Jaci
 */
public class ToastLUA extends ToastModule {

    public static Globals luaGlobals;
    public static Logger logger;

    @Override
    public String getModuleName() {
        return "ToastLUA";
    }

    @Override
    public String getModuleVersion() {
        return "0.0.1";
    }

    @Override
    public void prestart() {
        logger = new Logger("LUA", Logger.ATTR_DEFAULT);
        luaGlobals = JsePlatform.standardGlobals();
        CommandBus.registerCommand(new CommandLUA());
    }

    @Override
    public void start() {
        LuaRegistrar.register_defaults();
        LuaRegistrar.applyLibraries(luaGlobals);
        LuaFiles.init();
    }

    /**
     * Refresh the libraries loaded in the LUA Globals. Call this if you register your own library AFTER
     * {@link jaci.openrio.toast.lib.state.LoadPhase#PRE_START}
     */
    public static void refresh_library() {
        LuaRegistrar.applyLibraries(luaGlobals);
    }
}
