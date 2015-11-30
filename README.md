# ToastLua
The Toast Module for Scripting in Lua

# An Introduction
ToastLua is exactly what it sounds like, a module that allows you to code the Robot in Lua. This is made possible by the LuaJ library. A simple Lua program is shown below
```lua
states.transition(function(newState)
  print("I'm in: " .. newState .. "!")
  if newState == "Autonomous" then
    doAutonomous()
  end
end)

function doAutonomous()
  print "Executing Autonomous"
  motors.pwm(4)
  motors.pwm(5, "Talon")
  
  local drive = motors.drive(0, 1)
  drive.tank(0.5, 1.0)
end
```

# Downloads
Downloads (stable) can be found on the [releases](https://github.com/Open-RIO/ToastLUA/releases), and is similar in deployment to any other Toast module. For more details, refer to the Toast Repository.

If you wish to build the Module yourself from the SRC, you may do the following:

Fork this Repo
Mirror it to your local machine
Run gradlew idea or gradlew eclipse, depending on your Development Environment.
Run gradlew deploy to deploy to the RoboRIO
