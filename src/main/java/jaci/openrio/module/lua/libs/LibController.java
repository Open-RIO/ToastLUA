package jaci.openrio.module.lua.libs;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import jaci.openrio.module.lua.LuaLibrary;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class LibController extends LuaLibrary {

    Joystick[] joysticks = new Joystick[5];

    public LibController() {
        super("controllers");

        registerFunction("get", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                int id = arg.checkint();
                if (joysticks[id] == null)
                    joysticks[id] = new Joystick(id);
                Joystick j = joysticks[id];
                LuaTable table = new LuaTable();
                table.set("x-left", j.getX(GenericHID.Hand.kLeft));
                table.set("x-right", j.getX(GenericHID.Hand.kRight));
                table.set("y-left", j.getY(GenericHID.Hand.kLeft));
                table.set("y-right", j.getY(GenericHID.Hand.kRight));
                table.set("z-left", j.getZ(GenericHID.Hand.kLeft));
                table.set("z-right", j.getZ(GenericHID.Hand.kRight));
                table.set("twist", j.getTwist());
                table.set("throttle", j.getThrottle());
                table.set("trigger-left", valueOf(j.getTrigger(GenericHID.Hand.kLeft)));
                table.set("trigger-right", valueOf(j.getTrigger(GenericHID.Hand.kRight)));
                table.set("top-left", valueOf(j.getTop(GenericHID.Hand.kLeft)));
                table.set("top-right", valueOf(j.getTop(GenericHID.Hand.kRight)));
                table.set("button", new OneArgFunction() {
                    @Override
                    public LuaValue call(LuaValue arg) {
                        return valueOf(j.getRawButton(arg.checkint()));
                    }
                });
                table.set("pov", j.getPOV());
                table.set("magnitude", j.getMagnitude());
                table.set("direction", j.getDirectionDegrees());
                return table;
            }
        });
    }
}
