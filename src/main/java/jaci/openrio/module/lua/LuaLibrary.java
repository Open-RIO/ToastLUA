package jaci.openrio.module.lua;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

/**
 * The base class for LUA Libraries. These libraries consist of functions that are automatically loaded into
 * LUA classes. The library name is the object to call these functions from, for example, a Library named
 * 'myLib' with a registered function named 'myFunction', it would be called in LUA as 'myLib.myFunction'.
 *
 * A library require('') statement is not required, as the libraries are loaded by default.
 *
 * @author Jaci
 */
public class LuaLibrary extends TwoArgFunction {

    String lib_name;
    LuaTable table;

    public LuaLibrary(String library_name) {
        lib_name = library_name;
        table = new LuaTable();
    }

    public void registerFunction(String name, LuaValue val) {
        table.set(name, val);
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        env.set(lib_name, table);
        env.get("package").get("loaded").set(lib_name, table);
        return table;
    }

}
