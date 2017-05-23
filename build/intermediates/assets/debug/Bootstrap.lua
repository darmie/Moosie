package.preload['lpeg'] = function (...)
    return require "com.akinlaja.darmie.lpeg"
end

package.preload['debug'] = function (...)
    --return require "org.luaj.vm2.lib.DebugLib"
end

AndroidActivity = ...

local moonscript = require("moonscript.base")
local parse = require("moonscript.parse")
local MoonApp = AndroidActivity:readFile("init.moon")
local run = moonscript.loadstring(MoonApp)
return run()