# RdpGenerator
* Encrypt password to the RDP file format (the password will be 1329 bytes length) ant print to the output
* Creates RDP file with encrypted password and basic rdp info (username, domainname, hostname)
This file could be used to automatically connect to the Windows OS. The file should be generated at the RDP client side, e.g. to connecto from host A to the host B, the file must be generated at the A side.
* Open connection to the RDP host (creates temporary file and executes <code>mstsc.exe {filename}</code>)
* Update .rdp with the encrypted password line, e.g. adds new line <code>password 51:b:{encrypted password}</code> 
