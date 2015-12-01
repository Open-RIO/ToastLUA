-- A sample lua file for controlling and receiving Digital IO messages
-- on the RoboRIO

Digital.set(0, true)			-- Set DIO port 0 to output a HIGH signal
Digital.set(0, false)			-- Set DIO port 0 to output a LOW signal

local value = Digital.get(1)	-- Get the input value of DIO port 1 (boolean)