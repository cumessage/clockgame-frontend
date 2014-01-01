function createLogin()
	local loginScene = CCScene:create()

	local bg = CCSprite:create(getRes("loginbj.jpg"))
	bg:setPosition(origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2)
	loginScene:addChild(bg)

	local userNameField = CCTextFieldTTF:textFieldWithPlaceHolder(
		"input text", "Arial", 28)
	userNameField:setColor(ccc3(255,255,255));
	userNameField:setPosition(
		origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2 + 50)
	userNameField:attachWithIME();

	local passwordField = CCTextFieldTTF:textFieldWithPlaceHolder(
		"input password", "Arial", 28)
	passwordField:setColor(ccc3(255,255,255));
	passwordField:setPosition(
		origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2)
	passwordField:attachWithIME();

	loginScene:addChild(userNameField)
	loginScene:addChild(passwordField)
	return loginScene
end