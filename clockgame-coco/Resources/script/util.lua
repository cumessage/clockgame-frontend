resPath = "res/"

function printLog(...)
	print(string.format(...))
end

function getRes(file)
	return resPath .. file
end 