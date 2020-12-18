# pixel-boat

Originally developed by ENG1-Team-12

This repository is for developing the single player, Java-based game inspired by the Annual Dragon Boat Race in York.

## Developing (IntelliJ IDEA)

After cloning the repo you will want to open it in the IDE.

Then you will want to open `Edit Run/Debug Configurations` available on the top bar.
Add a new `application` then you will want to configure it with the following options:

Main class: `com.hardgforgif.dragonboatracing.desktop.DesktopLauncher`  
Working directory: `complete path to cloned repo\game\desktop\src\assets`  
Use classpath of module: `game.desktop.main`  

Then select the new configuration, and you will be able to run it.

## Releasing

gradlew desktop:dist

## Original website

Team 12 Assessment Website.
Please click the link: https://wpw503.github.io/ENG1-Team-12/

## Description

This repository contains documentation and Java code for the single player, Java-based game inspired by the Annual Dragon Boat Race in York. It aims to capture the excitement of the real event and satisfy the needs of a client at the University of York Computer Science Department.

## Documentation
* [User Requirements](https://github.com/wpw503/ENG1-Team-12/blob/main/Documentation/Req1.pdf)
* [Architecture](https://github.com/wpw503/ENG1-Team-12/tree/main/Documentation/Arch1.pdf)
* [Method selection and planning](https://github.com/wpw503/ENG1-Team-12/tree/main/Documentation/Plan1.pdf)
* [Risk assessment and mitigation](https://github.com/wpw503/ENG1-Team-12/tree/main/Documentation/Risk1.pdf)
* [Implementation](https://github.com/wpw503/ENG1-Team-12/tree/main/Documentation/Impl1.pdf)

## Production
![Gantt Chart](https://github.com/wpw503/ENG1-Team-12/blob/main/images/week8.png?raw=true)

## Inspiration

Inspiration: https://www.yorkrotary.co.uk/dragon-boat-challenge

## Authors

Contributors names and contact info

University of York Engineering 1 Cohort 2 Team 12:

* [@UmerFakher](https://github.com/UmerFakher)
* [@JamesFrost](https://github.com/Fritzbox2000)
* [@WilliamWalton](https://github.com/wpw503)
* [@RichardLiiv](https://github.com/sumsare)
* [@OllyWortley](https://github.com/orw511)
* [@JoeCambridge](https://github.com/JoeCambridge)

## References

* LibGDX Java Game development library: https://libgdx.badlogicgames.com/
* Software Engineering Skills Development: I. Sommerville, Software Engineering, Pearson Education, 2008
