resPath = "res/"

function printLog(...)
	print(string.format(...))
end

function getRes(file)
	return resPath .. file
end 

function __G__TRACKBACK__(msg)
    print("----------------------------------------")
    print("LUA ERROR: " .. tostring(msg) .. "\n")
    print(debug.traceback())
    print("----------------------------------------")
end

function cclog(...)
    print(string.format(...))
end