-- A sample class demonstrating the proper use of Motors
-- and RobotDrive in ToastLua

-- Initiate a motor and its type
Motor.pwm(0)			-- New Talon on PWM Port #0
Motor.pwm(1, "Jaguar")	-- New Jaguar on PWM Port #0

Motor.can(2)			-- New Talon SRX on CAN ID #2
Motor.can(3, "Jaguar")	-- New CAN Jaguar on CAN ID #3

Motor.set(0, 0.5)		-- Set PWM Motor #0 to 50% Throttle
Motor.setCAN(3, 0.5)	-- Set CAN Motor #0 to 50% Throttle

local motor1 = Motor.pwm(5)		-- Store a table containing motor details
local motor2 = Motor.pwm(6)

local drive1 = Motor.drive(motor1, motor2)	-- Create a new RobotDrive instance
local drive2 = Motor.drive(0, 1, 2, 3)		-- Or create it with PWM Talons with ports

drive1.tank(0.5, 0.5)				-- Tank Drive forward at 50% Speed

drive2.polar(0.5, 45, 0)			-- Mecanum polar drive at 50% speed, 45 degree heading and 0 rotation
drive2.cartesian(0.5, 0.5, 0.1)	-- Mecanum cartesian with x and y at 50% and rotation at 10%



-- Sample drive with Xbox Controller

local drive = Motor.drive(0, 1)		-- Talons on PWM #0 and #1
State.teleop(function()
	local xbox = Controller.xbox(0)
	drive.tank(xbox.leftY, xbox.rightY)
end)

-- or --

local drive = Motor.drive(0, 1, 2, 3)	-- Talons on PWM #0, #1, #2 and #3
State.teleop(function()
	local xbox = Controller.xbox(0)
	drive.polar(xbox.leftStickMagnitude, xbox.leftStickHeading, xbox.rightX)
end)