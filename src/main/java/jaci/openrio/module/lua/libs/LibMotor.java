package jaci.openrio.module.lua.libs;

import edu.wpi.first.wpilibj.*;
import jaci.openrio.module.lua.LuaLibrary;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.HashMap;
import java.util.UUID;

import static jaci.openrio.toast.lib.registry.Registrar.*;

public class LibMotor extends LuaLibrary {

    SpeedController[] pwm_controllers = new SpeedController[10];
    SpeedController[] can_controllers = new SpeedController[10];
    HashMap<String, RobotDrive> drives = new HashMap<>();

    public LibMotor() {
        super("Motor");

        registerFunction("pwm", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return call(arg, valueOf("Talon"));
            }

            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                int pwm = arg1.checkint();
                String type = arg2.checkjstring();

                switch (type.toLowerCase()) {
                    case "talon":
                        pwm_controllers[pwm] = talon(pwm);
                        break;
                    case "jaguar":
                        pwm_controllers[pwm] = jaguar(pwm);
                        break;
                    case "victor":
                        pwm_controllers[pwm] = victor(pwm);
                        break;
                    case "talonsrx":
                        pwm_controllers[pwm] = talonSRX(pwm);
                        break;
                    case "victorsp":
                        pwm_controllers[pwm] = victorSP(pwm);
                        break;
                }

                LuaTable table = new LuaTable();
                table.set("interface", "pwm");
                table.set("channel", pwm);
                table.set("type", type);
                return table;
            }

            public Varargs invoke(Varargs args) {
                if (args.narg() == 1) {
                    return call(args.arg1()).eval();
                } else if (args.narg() >= 2) {
                    return call(args.arg(1), args.arg(2)).eval();
                }
                return null;
            }
        });

        registerFunction("can", new VarArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return call(arg, valueOf("Talon"));
            }

            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                int can = arg1.checkint();
                String type = arg2.checkjstring();

                switch (type.toLowerCase()) {
                    case "talon":
                        can_controllers[can] = canTalon(can);
                        break;
                    case "jaguar    ":
                        can_controllers[can] = canJaguar(can);
                        break;
                }

                LuaTable table = new LuaTable();
                table.set("interface", "can");
                table.set("channel", can);
                table.set("type", type);
                return table;
            }

            public Varargs invoke(Varargs args) {
                if (args.narg() == 1) {
                    return call(args.arg1()).eval();
                } else if (args.narg() >= 2) {
                    return call(args.arg(1), args.arg(2)).eval();
                }
                return null;
            }
        });

        registerFunction("set", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue motor_id, LuaValue value) {
                int pwm = motor_id.checkint();
                double speed = value.checkdouble();
                pwm_controllers[pwm].set(speed);
                return null;
            }
        });

        registerFunction("setCAN", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue motor_id, LuaValue value) {
                int can = motor_id.checkint();
                double speed = value.checkdouble();
                can_controllers[can].set(speed);
                return null;
            }
        });

        registerFunction("drive", new VarArgFunction() {
            @Override
            public Varargs onInvoke(Varargs args) {
                int narg = args.narg();

                if (narg == 2 || narg == 4) {
                    SpeedController[] controllers = new SpeedController[narg];
                    for (int i = 0; i < narg; i++) {
                        LuaValue val = args.arg(i + 1);
                        if (val.istable()) {
                            LuaTable table = val.checktable();
                            SpeedController controller = null;
                            if (table.get("interface").checkjstring().equals("pwm")) {
                                controller = pwm_controllers[table.get("channel").checkint()];
                            } else if (table.get("interface").checkjstring().equals("can")) {
                                controller = can_controllers[table.get("channel").checkint()];
                            }
                            controllers[i] = controller;
                        } else if (val.isint()) {
                            controllers[i] = new Talon(val.checkint());
                        }
                    }

                    RobotDrive drive;
                    if (narg == 2)
                        drive = new RobotDrive(controllers[0], controllers[1]);
                    else drive = new RobotDrive(controllers[0], controllers[1], controllers[2], controllers[3]);
                    String driveUUID = UUID.randomUUID().toString();
                    drives.put(driveUUID, drive);

                    LuaTable table = new LuaTable();
                    table.set("_id", driveUUID);
                    table.set("tank", new TwoArgFunction() {
                        @Override
                        public LuaValue call(LuaValue left, LuaValue right) {
                            drives.get(table.get("_id").checkjstring()).tankDrive(left.checkdouble(), right.checkdouble());
                            return null;
                        }
                    });
                    table.set("polar", new ThreeArgFunction() {
                        @Override
                        public LuaValue call(LuaValue mag, LuaValue direction, LuaValue rotation) {
                            drives.get(table.get("_id").checkjstring()).mecanumDrive_Polar(mag.checkdouble(), direction.checkdouble(), rotation.checkdouble());
                            return null;
                        }
                    });
                    table.set("cartesian", new ThreeArgFunction() {
                        @Override
                        public LuaValue call(LuaValue x, LuaValue y, LuaValue rotation) {
                            drives.get(table.get("_id").checkjstring()).mecanumDrive_Cartesian(x.checkdouble(), y.checkdouble(), rotation.checkdouble(), 0);
                            return null;
                        }
                    });
                    return table;
                }
                return null;
            }
        });
    }


}
