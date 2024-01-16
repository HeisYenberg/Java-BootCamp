# compile
javac -d target -cp "lib/*" src/java/edu/school21/printer/*/*.java

# copy resources
cp -r src/resources target/.

# unpack libraries
cd target/
jar -xf ../lib/jcommander-1.82.jar
jar -xf ../lib/JCDP-4.0.0.jar
cd ..

# compress to jar
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target/ .

# run
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN