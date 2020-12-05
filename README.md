# TreadCraft
This project depends on TreadmillController, a program which asynchronously controls the speed of a treadmill via a remote connection. In this case, we will use a minecraft mod to communicate with the server.

## Requirements
-Minecraft ForgeModloader

## Setup
Edit the ip and port of line 34 in src/main/java/com/keveloper/treadmill/EventHandler.java
In command line, navigate to your install directory and run 'gradlew build'
This will create a mod-id.jar file in your build/libs folder, which can then be installed like any other mod.

## Please note
-If using an external ip address, make sure the port you're hosting on is open.
-Speed is only updated when the player is on the ground, making things like boats, swimming, elytras, creative flight, etc behave strangely. If you would like the treadmill to be active at all times regardless of player state, set the if statement on line 41 of src/main/java/com/keveloper/treadmill/EventHandler.java to always be true (or remove it).
