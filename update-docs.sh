#!/bin/bash

cd Implementation &&
chmod +x gradlew &&

#####################
# Generate javadocs #
#####################
echo Generating new javadocs &&
./gradlew javadoc &&
echo Removing all javadocs from website &&
rm -rf ../../pixelboatWebsite/docs/* &&
echo Copying javadocs to website &&
cp -r core/build/docs/javadoc/* ../../pixelboatWebsite/docs/  &&
cd .. &&

#########################
# Generate UML diagrams #
#########################

echo Generating UML images &&
cd UML &&
chmod +x generate_images.sh &&
./generate_images.sh &&
cd .. &&
echo Copying UML to website &&
cp -r UML/* ../pixelboatWebsite/UML/ &&




#########################
# Generate Test reports #
#########################

cd Implementation &&
./gradlew test &&
cd .. &&
rm -rf ../pixelboatWebsite/reports &&
mv Implementation/core/build/reports ../pixelboatWebsite/ &&


################
# Push Changes #
################

echo Moving to website folder &&
cd ../pixelboatWebsite &&
echo Set up Git &&
git config --local user.email "13720823+Frinksy@users.noreply.github.com" &&
git config --local user.name "Frinksy Workflow" &&
echo Stage all changes &&
git add docs/ &&
git add . &&
echo Check for changes

if git diff --staged --quiet ; then
    echo No changes found
else
    echo Changes found, creating a commit &&
    git commit -m "Update docs via Github Workflow" &&
    echo Push changes &&
    git push 
fi
echo Done!