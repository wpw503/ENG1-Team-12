#!/bin/bash

for f in ./plantUML_source/*
do
  echo "Processing $f file..."
  java -jar plantuml.jar -tpng $f -o "../PNG/"
  java -jar plantuml.jar -tsvg $f -o "../SVG/"
done
