# Rdp Helper
* Encrypt password to the RDP file format (the password will be 1329 bytes length) ant print to the output
* Creates RDP file with encrypted password and basic rdp info (username, domainname, hostname)
This file could be used to automatically connect to the Windows OS. The file should be generated at the RDP client side, e.g. to connecto from host A to the host B, the file must be generated at the A side.
* Open connection to the RDP host (creates temporary file and executes <code>mstsc.exe {filename}</code>)
* Update .rdp with the encrypted password line, e.g. adds new line <code>password 51:b:{encrypted password}</code> 
* Usage:
* To generate rdp password: 
*	<code>java -jar rdpgenerator.jar /password $PASSWORD</code>
* To create .rdp file: 
*	<code>java -jar rdpgenerator.jar /file $PASSWORD $USERNAME $HOST $FILENAME $DOMAIN</code>
* To start rdp session: 
*	<code>java -jar rdpgenerator.jar /open $PASSWORD $USERNAME $HOST $DOMAIN</code>
* To start rdp session and skip warning windows (untrusted publisher)
*	<code>java -jar rdpgenerator.jar /force $PASSWORD $USERNAME $HOST $DOMAIN</code>
* To add password line to the existing file: 
*	<code>java -jar rdpgenerator.jar /update $PASSWORD $FILENAME</code>
* Leave $DOMAIN empty if absent
