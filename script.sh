compile_source="framework/src/*.java"
to_build="framework/build"
to_tomcat="/home/ravalison/apache-tomcat-10.0.27/webapps"
to_lib="../../test-framework/WEB-INF/lib/"
to_project="../../test-framework/src"
framework_name="fw.jar"
project_name="testfw.war"

javac -d $to_build $compile_source

cd $to_build

jar -cf $framework_name .

cp $framework_name $to_lib

cd $to_project

javac -d ../WEB-INF/classes *.java

cd ..

jar -cf $project_name .

cp $project_name $to_tomcat