#!/bin/sh
java -cp /gil/local/pub/github.com/wcn00/xerris_java/XerraBattleship/build/libs/XerraBattleship.jar -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=6661 xerris.battleship.main.Game --quiet --manual
