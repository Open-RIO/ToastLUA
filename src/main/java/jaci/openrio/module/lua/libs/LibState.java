package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.lib.state.RobotState;
import jaci.openrio.toast.lib.state.StateListener;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LibState extends LuaLibrary {

    public LibState() {
        super("State");

        registerFunction("disabled", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(state -> {
                    if (state.equals(RobotState.DISABLED))
                        closure.call();
                });
                return null;
            }
        });

        registerFunction("auto", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(state -> {
                    if (state.equals(RobotState.AUTONOMOUS))
                        closure.call();
                });
                return null;
            }
        });

        registerFunction("teleop", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(state -> {
                    if (state.equals(RobotState.TELEOP))
                        closure.call();
                });
                return null;
            }
        });

        registerFunction("tick", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(state -> {
                    closure.call(state.state);
                });
                return null;
            }
        });

        registerFunction("transition", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTransition((newstate, oldstate) -> {
                    if (oldstate != null)
                        closure.call(valueOf(newstate.state), valueOf(oldstate.state));
                });
                return null;
            }
        });

        registerFunction("current", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return valueOf(StateTracker.currentState.state);
            }
        });
    }
}
