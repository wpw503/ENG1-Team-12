#!/bin/bash

echo Converting to SVG
plantuml -tsvg UML.txt
echo Converting to PNG
plantuml -tpng UML.txt
echo Done
