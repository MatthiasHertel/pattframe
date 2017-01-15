# add cert to runtime environment needed for the ssl handshake

e.g.
`..\..\bin\keytool -import -trustcacerts -keystore cacerts -storepass changeit -noprompt -alias yourAliasName -file path\to\certificate.cer`

sollte es zu einer pw abfrage kommen ist das default passwort von java `changeit`

## for unix
`sudo keytool -import -file cert.pem -alias mhertel.de -keystore /usr/lib/jvm/java-8-oracle/jre/lib/security/cacerts`
## for mac
`sudo keytool -import -file cert.pem -alias mhertel.de -keystore /Library/Java/Home/jre/lib/security/cacerts`

## for windows
kein plan
