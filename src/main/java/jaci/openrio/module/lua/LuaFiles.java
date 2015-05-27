package jaci.openrio.module.lua;

import jaci.openrio.toast.core.ToastBootstrap;
import jaci.openrio.toast.core.io.usb.MassStorageDevice;
import jaci.openrio.toast.core.io.usb.USBMassStorage;

import java.io.File;

/**
 * The File loader for Toast. This class finds .lua and .lualib files and loads them into the LUA instance.
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

        if (!USBMassStorage.overridingModules()) {
            crawlForLUA(rootDir);
        }

        for (MassStorageDevice device : USBMassStorage.connectedDevices) {
            if (device.concurrent_modules || device.override_modules) {
                File drivelua = new File(device.drivePath, "lua");
                drivelua.mkdirs();
                crawlForLUA(drivelua);
            }
        }
    }

    public static void crawlForLUA(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null)
                for (File file : files)
                    crawlForLUA(file);
        } else if (f.getName().endsWith(".lua")) {
            ToastLUA.luaGlobals.loadfile(f.getAbsolutePath()).call();
        } else if (f.getName().endsWith(".lualib")) {
            ToastLUA.luaGlobals.loadfile(f.getAbsolutePath());
        }
    }

}
