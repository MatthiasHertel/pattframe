# add cert to runtime environment needed for the ssl handshake

e.g.
`..\..\bin\keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias yourAliasName -file path\to\certificate.cer`

sollte es zu einer pw abfrage kommen ist das default passwort von java `changeit`

## for unix
`sudo keytool -import -file cert.pem -alias mhertel.de -keystore /usr/lib/jvm/java-8-oracle/jre/lib/security/cacerts`
## for mac
`sudo keytool -import -file cert.pem -alias mhertel.de -keystore /Library/Java/Home/jre/lib/security/cacerts`

## for windows
Open a command prompt and run the following “keytool” command from the bin directory of the JRE. This starts the certificate installation. You must insert the directory path to the downloaded certificate from step 1 before running the command. If you are running the Windows operating system, you will need to run the command prompt as an administrator.

`c:\Program Files\Java\jre1.8.0_111\bin>
keytool -importcert -file "cert.pem" -alias mhertel.de -keystore ..\lib\security\cacerts `