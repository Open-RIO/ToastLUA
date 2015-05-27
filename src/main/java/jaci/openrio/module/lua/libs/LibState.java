package jaci.openrio.module.lua.libs;

import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.lib.state.RobotState;
import jaci.openrio.toast.lib.state.StateListener;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class LibState extends LuaLibrary {

    public LibState() {
        super("states");

        registerFunction("disabled", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(new StateListener.Ticker() {
                    @Override
                    public void tickState(RobotState state) {
                        if (state.equals(RobotState.DISABLED))
                            closure.call();
                    }
                });
                return null;
            }
        });

        registerFunction("auto", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(new StateListener.Ticker() {
                    @Override
                    public void tickState(RobotState state) {
                        if (state.equals(RobotState.AUTONOMOUS))
                            closure.call();
                    }
                });
                return null;
            }
        });

        registerFunction("teleop", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTicker(new StateListener.Ticker() {
                    @Override
                    public void tickState(RobotState state) {
                        if (state.equals(RobotState.TELEOP))
                            closure.call();
                    }
                });
                return null;
            }
        });

        registerFunction("transition", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                LuaClosure closure = arg.checkclosure();
                StateTracker.addTransition(new StateListener.Transition() {
                    @Override
                    public void transitionState(RobotState state, RobotState oldState) {
                        if (oldState != null)
                            closure.call(valueOf(state.state), valueOf(oldState.state));
                    }
                });
                return null;
            }
        });
    }
}
