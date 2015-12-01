-- A sample lua file for getting information regarding the environment
-- of the Robot

local isSimulation = Env.simulation		-- A boolean stating if we're in a simulator
local isRoboRIO	   = Env.embedded		-- A boolean stating if we're on the RoboRIO
Log.info(Env.type)						-- Human readable state (Simulation, Embedded, etc)
Log.info(Env.os)						-- Operating System, capitalized (WINDOWS, MAC, LINUX)

-- Print out all our Environment data
for i, v in pairs(Env) do
	Log.info(i .. ": " .. tostring(v))
end