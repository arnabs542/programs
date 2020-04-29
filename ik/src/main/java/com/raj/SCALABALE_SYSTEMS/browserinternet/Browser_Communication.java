package com.raj.SCALABALE_SYSTEMS.browserinternet;

public class Browser_Communication {
 /**
  * # How do browsers communicate over internet?
  * When you type url, browsers lookup their IP from DNS & attempt to connect to the IP server. The communication
  * happens by diff protocols - telnet, ftp, websockets, http, https
  *
  * # HTTP vs HTTPS : https://www.youtube.com/watch?v=hExRDVZHhig
  *  => "Hyper Text Transfer Protocol" - Former most widely used format of data exchange over web. Data is sent in clear
  *   text & susceptible to hacks, especially if it has sensitive data.
  *  => Secure HTTP - Data is encrypted & transferred over web which is impossible to hack. HTTPS secures data using one of the 2 protocols:
  *   1. SSL (Secure Socket Layer) - Uses public key encryption to secure data.
  *      Web browser connects to website & asks identify itself. Website sends a copy of it's SSL certificate.
  *      Browser authenticates the identity of website from CA & sends trusted OK msg to webserver.
  *      Webserver responds with an ACK so that ssl session can proceed. Encrypted data can now be exchanged over internet.
  *   2. TLS (Transport Layer Security) - SSL Successor & Latest industry standard cryptographic protocol.
  *      Based on SSL specs which Auths server, client & encrypts data. Most websites use HTTPS by default else Google
  *      flags them as "not secure" & penalizes them in search rankings.
  *
  */
}
