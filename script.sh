javac -d framework/build framework/src/*.java

cd framework/build

jar -cf fw.jar .

cp fw.jar ../../test-framework/WEB-INF/lib/

cd ../../test-framework/src

cp -r * ../WEB-INF/classes/

cd ..

jar -cf testfw.war .

cp testfw.war /home/ravalison/apache-tomcat-10.0.27/webapps