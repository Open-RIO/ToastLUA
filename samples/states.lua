-- A sample lua file that demonstrates the usage of States in
-- ToastLua
Log.info("My Current State Is: " .. State.current())

State.transition(function(newstate, oldstate)
	Log.info("I went from " .. oldstate .. " to " .. newstate)
end)

State.auto(function() 
	-- Autonomous Periodic Code
end)

State.teleop(function()
	-- Teleop Periodic Code
end)

State.disabled(function()
	-- Disabled Periodic Code
end)

State.tick(function(state)
	-- General Periodic Code
	Log.info("Ticking in state " .. state)
end)