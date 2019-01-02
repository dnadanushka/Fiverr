-----------------------------------------------------------------------------------------
--
-- main.lua
--
-----------------------------------------------------------------------------------------

local tap_count = 0

local background_img = display.newImageRect("img/BrickBack.png",360,570)
background_img.x = display.contentCenterX
background_img.y = display.contentCenterY

local platform = display.newImageRect("img/tabletop.png",700,40)
platform.x = display.contentCenterX
platform.y = display.contentHeight - 20


local ball = display.newImageRect("img/ball.png",120,120)
ball.x = display.contentCenterX
ball.y = display.contentCenterY

local scoreText = display.newText(tap_count,display.contentCenterX - 80,15,native.systemFont,30)

local physics = require("physics")
physics.start()


physics.addBody(ball,"dynamic",{radius = 50,bounce= 0.5})
physics.addBody(platform,"static",{bounce = 0})

local function pushBallUp()

    ball:applyLinearImpulse(0,-0.60,ball.x,ball.y)
    tap_count = tap_count + 1
    scoreText.text = tap_count
end

ball:addEventListener("tap",pushBallUp)
