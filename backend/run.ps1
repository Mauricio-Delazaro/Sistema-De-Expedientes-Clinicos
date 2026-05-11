$env:JAVA_HOME = "C:\Program Files\Java\jdk-21.0.10"
$javaExe = "$env:JAVA_HOME\bin\java.exe"
$wrapperJar = "$PSScriptRoot\.mvn\wrapper\maven-wrapper.jar"
$projectDir  = $PSScriptRoot

Set-Location $PSScriptRoot

& $javaExe `
    -classpath $wrapperJar `
    "-Dmaven.multiModuleProjectDirectory=$projectDir" `
    org.apache.maven.wrapper.MavenWrapperMain `
    spring-boot:run
