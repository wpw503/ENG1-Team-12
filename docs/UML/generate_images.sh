#!/bin/bash

for f in ./plantUML_source/*
do
  echo "Processing $f file..."
  plantuml -tpng $f -o "../PNG/"
  plantuml -tsvg $f -o "../SVG/"
done
