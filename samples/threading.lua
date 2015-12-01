-- A sample lua file representing multithreaded behaviour
-- on the Toast async pool

Thread.async(function()				-- Create a new Async Task and run it
	Thread.sleep(1000)				-- Wait 1000 ms (1 second)
	Log.info("Hello World")
end)

Thread.async(1, true, function(a, b)			-- Optionally pass arguments through
	Log.info(tostring(a) .. " " .. tostring(b))
end)

Thread.heartbeat(function(skipped_beats)
	-- Do something exactly once every 100ms (10Hz)
end)