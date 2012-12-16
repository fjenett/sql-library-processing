#! /bin/sh

# fjenett 20080601
#
# mail@florianjenett.de
# http://www.florianjenett.de
#

connectors="http://www.zentus.com/sqlitejdbc/ \n"
connectors=$connectors"http://www.mysql.com/products/connector/j/ \n"
connectors=$connectors"http://jdbc.postgresql.org/download.html \n"

projectName="sql"

CLASSPATH="/System/Library/Frameworks/JavaVM.framework/Classes/classes.jar:/System/Library/Frameworks/JavaVM.framework/Classes/ui.jar:/System/Library/Java/Extensions/QTJava.zip"

classp="/Users/fjenett/Repos/processing/build/macosx/work/lib/core.jar:./mysql-connector-java-3.1.14-bin.jar:./build:./src:$CLASSPATH"

jikesp="/Users/fjenett/Repos/processing/build/macosx/dist/jikes"
javacp=`which javac`
compilerp=$javacp
build="./build"
dist="./dist"

echo ""
date

#
# remove old jar and previous build
#
echo "Cleaning ..."

test -f $projectName.jar && rm $projectName.jar
test -d $build && rm -R $build
test -d $dist && rm -R $dist
mkdir $build


files=`find ./ -iname *.java -print`

#
# compile it
#
#javac -d build/ -classpath "/Applications/Processing 0123/lib/core.jar" src/*.java

echo "Compiling sources ..."

#$compilerp -target 1.1 +D -classpath $classp -d $build $files
$compilerp -classpath $classp -d $build $files

#
# create new jar
#
echo "Zipping jar ..."

cd $build
zip -rq "../$projectName.zip" ./*
mv "../$projectName.zip" "../$projectName.jar"
cd ..

if [ $1 == "-dist" ]
then
	echo "Packaging distribution ..."

	test -f $projectName.zip && rm $projectName.zip
	test -d $dist && rm -R $dist
	
	mkdir -p $dist/$projectName/library/
	
	cp $projectName.jar $dist/$projectName/library/$projectName.jar
	cp README.txt $dist/$projectName/README.txt
	cp -r src $dist/$projectName/src
	
	touch $dist/$projectName/library/PUT_CONNECTOR_JARS_HERE
	
	echo $connectors > $dist/$projectName/library/PUT_CONNECTOR_JARS_HERE
	
	cd $dist
	zip -rq $projectName.zip $projectName
	ts=`date +"%Y%m%d"`
	mv $projectName.zip "../$projectName.$ts.zip"
	cd ..
fi


#
# copy over to processing/libraries
#

echo "Copying to Processing libraries ..."

rm -R /Users/fjenett/Documents/Processing/libraries/$projectName
mkdir -p /Users/fjenett/Documents/Processing/libraries/$projectName/library/
cp $projectName.jar /Users/fjenett/Documents/Processing/libraries/$projectName/library/$projectName.jar
cp mysql-connector-java-3.1.14-bin.jar /Users/fjenett/Documents/Processing/libraries/$projectName/library/mysql-connector-java-3.1.14-bin.jar


if [ $2 == "-clean" ]
then
	echo "Cleaning ..."

	test -d $build && rm -R $build
	test -d $dist && rm -R $dist
fi

echo "Done."
echo ""