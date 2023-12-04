# Barbie_work

The assets folder, situated within the Android directory, serves as the visual and audio library for our game. It contains the various PNG files that 
bring Barbie to life through animations, the sprites for the enemies, and the tiled map used in the game screen. Additionally, this folder houses JPEGs 
of our user interface, including the game over screen, the win screen, and the opening screen. To complement the visuals, we've also included the soundtrack 
and sound effects, such as the distinctive death sound, in this directory. 

In contrast, the core folder is the nexus of our game's functionality. Within it, we've structured the com.mygdx.barbie package into three distinct directories 
to maintain clean code separation. The Sprites directory contains the code for our protagonist, Barbie, encapsulating her physics and movement mechanics, as 
well as the classes for our adversaries. The Tools directory is pivotal for defining the game's physics with the B2WorldCreator class for Barbie and the snowmen. 
The WorldContactListener class resides here as well, detailing the various collision scenarios that trigger different animations and alter Barbie's current state.

The Screens directory houses four critical classes – GameOverScreen, OpeningScreen PlayScreen, and WinScreen – each corresponding to the different game states that 
Barbie can find herself in. Ultimately, these screens all funnel back to the OpeningScreen, which is the first interface the player encounters before starting the game. 
