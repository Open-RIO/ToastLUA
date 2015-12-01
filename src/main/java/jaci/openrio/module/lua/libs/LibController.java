package jaci.openrio.module.lua.libs;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import jaci.openrio.module.lua.LuaLibrary;
import jaci.openrio.toast.lib.device.XboxController;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class LibController extends LuaLibrary {

    Joystick[] joysticks = new Joystick[5];
    XboxController[] xbox = new XboxController[5];

    public LibController() {
        super("Controller");

        registerFunction("joy", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                int id = arg.checkint();
                if (joysticks[id] == null)
                    joysticks[id] = new Joystick(id);
                Joystick j = joysticks[id];
                LuaTable table = new LuaTable();
                table.set("leftX", j.getX(GenericHID.Hand.kLeft));
                table.set("rightX", j.getX(GenericHID.Hand.kRight));
                table.set("leftY", j.getY(GenericHID.Hand.kLeft));
                table.set("rightY", j.getY(GenericHID.Hand.kRight));
                table.set("leftZ", j.getZ(GenericHID.Hand.kLeft));
                table.set("rightZ", j.getZ(GenericHID.Hand.kRight));
                table.set("twist", j.getTwist());
                table.set("throttle", j.getThrottle());
                table.set("leftTrigger", valueOf(j.getTrigger(GenericHID.Hand.kLeft)));
                table.set("rightTrigger", valueOf(j.getTrigger(GenericHID.Hand.kRight)));
                table.set("leftTop", valueOf(j.getTop(GenericHID.Hand.kLeft)));
                table.set("rightTop", valueOf(j.getTop(GenericHID.Hand.kRight)));
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

        registerFunction("xbox", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                int id = arg.checkint();
                if (xbox[id] == null)
                    xbox[id] = new XboxController(id);

                XboxController j = xbox[id];
                LuaTable table = new LuaTable();
                table.set("leftTrigger", j.leftTrigger());
                table.set("rightTrigger", j.rightTrigger());
                table.set("leftX", j.leftX());
                table.set("leftY", j.leftY());
                table.set("rightX", j.rightX());
                table.set("rightY", j.rightY());
                table.set("pov", j.pov());
                table.set("a", valueOf(j.a()));
                table.set("b", valueOf(j.b()));
                table.set("x", valueOf(j.x()));
                table.set("y", valueOf(j.y()));
                table.set("leftBumper", valueOf(j.leftBumper()));
                table.set("rightBumper", valueOf(j.rightBumper()));
                table.set("leftStick", valueOf(j.leftStick()));
                table.set("rightStick", valueOf(j.rightStick()));
                table.set("select", valueOf(j.select()));
                table.set("start", valueOf(j.start()));
                table.set("leftStickMagnitude", j.leftStickMagnitude());
                table.set("leftStickHeading", j.leftStickHeading());
                table.set("rightStickMagnitude", j.rightStickMagnitude());
                table.set("rightStickHeading", j.rightStickHeading());
                return table;
            }
        });
    }
}
