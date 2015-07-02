## Rdp Helper
* Encrypts password to the RDP file format (the password will be 1329 bytes length) ant print to the output
* Creates RDP file with encrypted password and basic rdp info (username, domainname, hostname)
This file could be used to automatically connect to the Windows OS. The file should be generated at the RDP client side, e.g. to connecto from host A to the host B, the file must be generated at the A side.
* Opens connection to the RDP host (creates temporary file and executes <code>mstsc.exe {filename}</code>)
* Updates .rdp with the encrypted password line, e.g. adds new line <code>password 51:b:{encrypted password}</code>
<p> 
* Usage:
* To generate rdp password: 
*	<code>java -jar rdp-helper-*.jar  /password $PASSWORD</code>
* To create .rdp file: 
*	<code>java -jar rdp-helper-*.jar /file $PASSWORD $USERNAME $HOST $FILENAME $DOMAIN</code>
* To start rdp session: 
*	<code>java -jar rdp-helper-*.jar /open $PASSWORD $USERNAME $HOST $DOMAIN</code>
* To start rdp session and skip warning windows (untrusted publisher)
*	<code>java -jar rdp-helper-* /force $PASSWORD $USERNAME $HOST $DOMAIN</code>
* To close rdp session 
*	<code>java -jar rdp-helper-*.jar /logoff $USERNAME</code>
* To add password line to the existing file: 
*	<code>java -jar rdp-helper-* /update $PASSWORD $FILENAME</code>
* Leave $DOMAIN empty if absent

<p>
<code>mvn clean compile assembly:single</code>
<br>or<br>
<code>mvn install</code>