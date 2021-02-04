#!/usr/bin/sh

cd Implementation &&
chmod +x gradlew &&
echo Generating new javadocs &&
./gradlew javadoc &&
echo Removing all javadocs from website &&
rm -rf ../docs/* &&
echo Copying javadocs to website &&
cp -r core/build/docs/javadoc/* ../docs/  &&
cd .. &&
echo Generating UML images &&
cd UML &&
chmod +x generate_images.sh &&
./generate_images.sh &&
cd .. &&
echo Set up Git &&
git config --local user.email "13720823+Frinksy@users.noreply.github.com" &&
git config --local user.name "Frinksy Workflow" &&
echo Stage all changes &&
git add docs/ &&
git add UML/PNG/ && git add UML/SVG/ &&
echo Commit changes &&
git commit -m "Update docs via Github Workflow" &&
echo Push changes &&
git push &&
echo Done!