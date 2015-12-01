package jaci.openrio.module.lua;

import jaci.openrio.toast.core.ToastBootstrap;
import jaci.openrio.toast.core.io.Storage;
import jaci.openrio.toast.core.io.usb.MassStorageDevice;
import jaci.openrio.toast.core.io.usb.USBMassStorage;
import org.luaj.vm2.LuaValue;

import java.io.File;

/**
 * The File loader for ToastLua. This class finds .lua and .lualib files and loads them into the Lua instance.
 *
 * This class follows the {@link jaci.openrio.toast.core.io.usb.USBMassStorage} overriding the concurrent modules rules.
 *
 * @author Jaci
 */
public class LuaFiles {

    static File rootDir;

    public static void init() {
        rootDir = new File(ToastBootstrap.toastHome, "lua");
        rootDir.mkdirs();

        String loadFile = ToastLUA.luaConfig.getString("main.file", "main.lua");

        Storage.USB_Module("lua/" + loadFile, (file, usb, device) -> {
            if (file.exists())
                loadFile(file).call();
        });

    }

    public static LuaValue loadRelativeFile(String filename) {
        final File[] the_file = {null};
        Storage.USB_Module("lua/" + filename, (file, usb, device) -> {
            File autoEnding = new File(file.getAbsolutePath() + ".lua");
            if (file.exists())
                the_file[0] = file;
            else if (autoEnding.exists())
                the_file[0] = autoEnding;
        });
        return loadFile(the_file[0]);
    }

    public static LuaValue loadFile(File f) {
        return ToastLUA.luaGlobals.loadfile(f.getAbsolutePath());
    }

}
