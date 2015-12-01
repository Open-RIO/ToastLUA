-- A sample file that will register a new command on the
-- Toast command bus that prints the arguments given to it
Command.new("hello", function(...)
	local arguments = { ... } 
	Log.info("I got " .. #arguments .. " argument(s)!")
	for i = 1, #arguments do						-- In Lua, array indexes start from 1
		Log.info("Arg #" .. i .. " = " .. arguments[i])
	end
end)