# ToastLua
The Toast Module for Scripting in Lua

# An Introduction
ToastLua is exactly what it sounds like, a module that allows you to code the Robot in Lua. This is made possible by the LuaJ library. A simple Lua program is shown below
```lua
State.transition(function(newState)
  Log.info("I'm in: " .. newState .. "!")
  if newState == "Autonomous" then
    doAutonomous()
  end
end)

local drive = Motor.drive(0, 1)
function doAutonomous()
  Log.info "Executing Autonomous"
  drive.tank(0.5, 1.0)
end

local mecanumDrive = Motor.drive(Motor.can(0), Motor.can(1), Motor.can(2), Motor.can(3))
State.teleop(function() 
  local xbox = Controller.xbox(0)
  mecanumDrive.polar(xbox.leftStickMagnitude, xbox.leftStickHeading, xbox.rightX)
end)

Thread.async(function()
	Thread.sleep(1000)
	Log.info("Hello World from a new Thread!")
end)
```

# Sample Programs
Sample programs can be found under the `samples/` directory.

# Dependency
To use ToastLua as a dependency in your own Toast module, add this to your `build.gradle` file:

```
dependencies {
  compile group: 'jaci.openrio.lua', name: 'ToastLua', version: '+'
}
```

# Downloads
Downloads (stable) can be found on the [releases](https://github.com/Open-RIO/ToastLUA/releases), and is similar in deployment to any other Toast module. For more details, refer to the Toast Repository.

If you wish to build the Module yourself from the SRC, you may do the following:

Fork this Repo
Mirror it to your local machine
Run gradlew idea or gradlew eclipse, depending on your Development Environment.
Run gradlew deploy to deploy to the RoboRIO
