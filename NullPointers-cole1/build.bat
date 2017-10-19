javac -d . src/*.java -cp "lib/*"
jar cvfm dist/deploy.jar Manifest.txt steamget/ lib/*
pause