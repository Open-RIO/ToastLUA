-- A sample lua file that demonstrates the usage of Joysticks

-- Keep in mind that whenever Controller.joy is called, it returns
-- a table of all the -current- values of the joystick. This means
-- that each tick you must call Controller.joy again to retrieve
-- updated values.
local joystick = Controller.joy(0)
Log.info("Left X Axis: " .. joystick.leftX)
Log.info("Button 1: " .. tostring(joystick.button(1)))

-- Print all the values we can access, and their values
for i, v in pairs(joystick) do
	Log.info(i .. ": " .. tostring(v))
end

-- The same rules about Controller.joy apply to Controller.xbox, aswell
local xbox = Controller.xbox(1)
Log.info("POV Heading: " .. xbox.pov)

-- Print all the values we can access, and their values
for i, v in pairs(xbox) do
	Log.info(i .. ": " .. tostring(v))
end



-- Sample drive code:
State.teleop(function()
	local xboxController = Controller.xbox(1)
	-- doDrive would contain your Drive Code
	doDrive(xboxController.leftY, xboxController.rightY)
end)