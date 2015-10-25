# Asteroids
Asteroids is a simple OpenGL clone of Atari's Asteroids.

Asteroids was built with [LWJGL2](http://legacy.lwjgl.org) and [Slick-Util](http://slick.ninjacave.com).

## Running

Asteroids requires Java 8 to run. This game will run directly from Eclipse.

Without an IDE you can build by difining the source as src/, librarys including lwjgl.jar and slick-util.jar, and setting lwjgl.jar's natives as native/your_os.

## Controls

### Menus

All menus can be navigated with the arrow keys, and Enter/Return is for select.

### Gameplay

WASD moves; Left and right arrows turn; Space fires; Escape pauses.

## Performance

Asteroids requires OpenGl support.

If you encounter poor performance you can decrease the amount of particals in the background or the particals from impacts; these changes will not affect gameplay in any way, but can increase framerate.

Asteroids is capped at 60Hz by design, for now.

## License

Asteroids is licensed under the terms of the MIT license. You are free to use this code for any reason.
