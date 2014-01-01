require "script/AudioEngine" 
require "script/util"
require "script/login"

local function main()
    collectgarbage("setpause", 100)
    collectgarbage("setstepmul", 5000)

    visibleSize = CCDirector:sharedDirector():getVisibleSize()
    origin = CCDirector:sharedDirector():getVisibleOrigin()

    -- run
    local loginScene = createLogin()
    CCDirector:sharedDirector():runWithScene(loginScene)
end

xpcall(main, __G__TRACKBACK__)


