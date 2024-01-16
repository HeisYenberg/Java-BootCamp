# compile
javac -d target src/java/edu/school21/printer/*/*.java

# copy resources
cp -r src/resources target/.

# compress to jar
jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target/ .

# run
java -jar target/images-to-chars-printer.jar . o