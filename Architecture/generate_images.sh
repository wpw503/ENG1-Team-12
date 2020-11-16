#!/bin/bash

echo Converting to SVG
plantuml -tsvg UML.txt
echo Converting to PNG
plantuml -tpng UML.txt
echo Converting to SVG for Abstract
plantuml -tsvg UML_abstract.txt
echo Converting to PNG for Abstract
plantuml -tpng UML_abstract.txt
echo Done
