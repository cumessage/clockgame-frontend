local function createEmailField()
	local emailField = CCEditBox:create(
		CCSizeMake(300, 35), CCScale9Sprite:create(getRes("inputbj.jpg")))
	emailField:setFontColor(ccc3(255,255,255))
	emailField:setPlaceHolder("input email");  
	emailField:setPosition(ccp(
		origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2 + 50))

	emailField:setInputMode(kEditBoxInputModeSingleLine);  
	emailField:setInputFlag(kEditBoxInputFlagSensitive);  
	-- emailField.setDelegate();

	return emailField
end

local function createPasswordField()
	local passwordField = CCEditBox:create(
		CCSizeMake(300, 35), CCScale9Sprite:create(getRes("inputbj.jpg")))
	passwordField:setFontColor(ccc3(255,255,255))
	passwordField:setPlaceHolder("input password");  
	passwordField:setPosition(ccp(
		origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2))

	passwordField:setInputMode(kEditBoxInputModeSingleLine);
	passwordField:setInputFlag(kEditBoxInputFlagPassword);  
	return passwordField
end

local function createLoginButton()
	CCMenuItemFont:setFontName("Arial")
    CCMenuItemFont:setFontSize(28);
    local pMenuItemFont = CCMenuItemFont:create("LOGIN");
    pMenuItemFont:setColor(ccc3(0,0,0))
    pMenuItemFont:setPosition(
    	ccp(origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2 - 80))
    local function menuCallback()
        print("hit button")
    end
    pMenuItemFont:registerScriptTapHandler(menuCallback)
    local pMenu = CCMenu:create()
    pMenu:addChild(pMenuItemFont)
    pMenu:setPosition(ccp(origin.x, origin.y))
    return pMenu
end

local function hitLoginButton()
	print("hit button");
end

function createLogin()
	local loginScene = CCScene:create()

	local bg = CCSprite:create(getRes("loginbj.jpg"))
	bg:setPosition(origin.x + visibleSize.width / 2, origin.y + visibleSize.height / 2)
	loginScene:addChild(bg)

	loginScene:addChild(createEmailField())
	loginScene:addChild(createPasswordField())
	loginScene:addChild(createLoginButton())

	return loginScene
end


