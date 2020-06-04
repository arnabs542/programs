package com.raj.SCALABALE_SYSTEMS.browserinternet_security;

public class Security {
 /**
  * # BASICS
  * * https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
  * * https://www.synopsys.com/blogs/software-security/cryptographic-hash-functions/
  * * Passwords shouldn't be stored in plain text. Just hash them using SHA/MD5 functions
  * * Or better use key stretching algorithms like PBKDF2/bcrypt/SHA-512
  * * Hashes is 1-way function, meaning u can't guess the original password from a hash
  * * Encryption is 2-way function, so u need to decrypt message to use it
  * * Passwords should be hashed rather than encrypted(unless decryption is necessary for an app without SSO to auth against legacy auth server),
  *   as this makes it difficult or impossible for an attacker to obtain the original passwords from the hashes.
  *
  * * In short for storing passwords:
  *   1. Use Bcrypt unless you have a good reason not to.
  *   2. Set a reasonable work factor for you system.
  *   3. Use a salt (modern algorithms do this for you automatically).
  *   4. Consider using a pepper to provide an additional layer of security.
  *
  *
  * # SECURITY for DATA at REST
  * * User passwords must be stored using secure hashing techniques with strong algorithms like PBKDF2, bcrypt, or SHA-512.
  *   Simply hashing the password a single time does not sufficiently protect the password.
  *   It can be easily cracked by using brute/dictionary/rainbow attack, which basically uses precomputed hash -> password
  * * Attackers use commonly used Hash functions & compute hash for dictionary word & compare with target hash. If they match, hacked!!
  *
  * * [Hashing Concepts]
  *   + Salt: concat a crypto secure function output like java.secure.SecureRandom with password & then generate hash. Store (user,hash,salt) in DB. Salt is generated per password.
  *   + Pepper: additional layer of security by global concat along with salt. Not sored in DB but globally somewhere secure in app config.
  *     Provides security in case the attacker hacked DB hash+salt records somehow.
  *   + Adaptive hashing (a work factor), which is iterating 'n' times over hash function to make it more secure. Save this number along with salt,hash.
  *
  * * General workflow for account registration and authentication in a hash-based account system is as follows:
  *   https://crackstation.net/hashing-security.htm
  *   https://security.stackexchange.com/questions/19525/help-understanding-basic-user-authentication-with-salts-and-hashing
  *   1. The user creates an account.
  *   2. Their password is hashed and stored in the database. (user_id,hash,salt)
  *   3. When the user comes back to re-login, first look up the supplied user_id in your database and return the hash and the salt.
  *   4. Take the provided user_id and rerun the hashing function using the salt obtained in 1 on password submitted on form.
  *   5. If the hash generated in 2, matches the on retrieved from the database in 1 then you know they provided the correct password.
  *
  *
  *
  * # SECURITY for DATA in TRANSIT
  * * https://www.websecurity.digicert.com/security-topics/what-is-ssl-tls-https
  * * Need to ensure the data going over the network is encrypted somehow. Most commonly used is by obtaining SSL certificate from CA.
  * * SSL stands for Secure Sockets Layer. Keeps data b/w client & server secure from reading & modifying transmitted data.
  * * Uses encryption algorithms to scramble data in transit, preventing hackers from reading it.
  * * TLS (Transport Layer Security) is just an updated, more secure, version of SSL.
  * * When you are buying SSL from DigiCert you are actually buying the most up to date TLS certificates with the option of ECC, RSA or DSA encryption.
  * * HTTPS (Hyper Text Transfer Protocol Secure) appears in the URL when a website is secured by an SSL certificate. The details of the certificate, including the issuing authority and the corporate name of the website owner, can be viewed by clicking on the lock symbol on the browser bar.
  *
  * * SSL/TLS uses RSA Asymmetric Encryption Algorithm to establish a secure client-server session
  * * And Symmetric Encryption Algorithm to exchange information securely over the established secured session (also called as “SSL Handshake”) between server and client.
  *
  * # How does SSL/TLS work?
  * https://www.cloudflare.com/learning/ssl/how-does-ssl-work/
  * These are the essential principles to grasp for understanding how SSL/TLS works:
  * - Secure communication begins with a TLS handshake, in which the two communicating parties open a secure connection and exchange the public key
  * - During the TLS handshake, the two parties generate session keys, and the session keys encrypt and decrypt all communications after the TLS handshake
  * - Different session keys are used to encrypt communications in each new session
  * - TLS ensures that the party on the server side, or the website the user is interacting with, is actually who they claim to be
  * - TLS also ensures that data has not been altered, since a message authentication code (HMAC) is included with transmissions
  * - With TLS, both HTTP data that users send to a website (by clicking, filling out forms, etc.) and the HTTP data that websites send to users is encrypted. Encrypted data has to be decrypted by the recipient using a key.
  *
  * The TLS handshake
  * - TLS communication sessions begin with a TLS handshake. A TLS handshake uses something called asymmetric encryption,
  *   meaning that two different keys are used on the two ends of the conversation. This is possible because of a technique called public key cryptography.
  *   In public key cryptography, two keys are used: a public key, which the server makes available publicly, and a private key,
  *   which is kept secret and only used on the server side. Data encrypted with the public key can only be decrypted with the private key, and vice versa.
  * - During the TLS handshake, the client and server use the public and private keys to exchange randomly generated data, and this random data is used to create new keys for encryption, called the session keys.
  *
  * Symmetric encryption with session keys
  * - Unlike asymmetric encryption, in symmetric encryption the two parties in a conversation use the same key.
  *   After the TLS handshake, both sides use the same session keys for encryption. Once session keys are in use,
  *   the public and private keys are not used anymore. Session keys are temporary keys that are not used again once the session is terminated.
  *
  * Asymmetrical Encryption Symmetric Encryption
  * - Authenticating the origin server: TLS communications from the server include a hash message authentication code, or HMAC,
  *   which is a digital signature confirming that the communication originated from the actual website.
  *   This authenticates the server, preventing man-in-the-middle attacks and domain spoofing. It also ensures that the data has not been altered in transit.
  *
  * What is an SSL certificate?
  * - An SSL certificate is a file installed on a website's origin server. It's simply a data file containing the public key
  *   and the identity of the website owner, along with other information. Without an SSL certificate, a website's traffic
  *   can't be encrypted with TLS. Technically, any website owner can create their own SSL certificate, and such certificates
  *   are called self-signed certificates. However, browsers do not consider self-signed certificates to be as trustworthy
  *   as SSL certificates issued by a certificate authority.
  *
  * How does a website get an SSL certificate?
  * - Website owners need to obtain an SSL certificate from a certificate authority, and then install it on their origin/web server (often a web host can handle this process).
  *   A certificate authority is an outside party who can confirm that the website owner is who they say they are. They keep a copy of the certificates they issue.
  *
  * # Pretty Good Privacy (PGP) https://en.wikipedia.org/wiki/Pretty_Good_Privacy
  * An encryption program that provides cryptographic privacy and authentication for data communication.
  * PGP is used for signing, encrypting, and decrypting texts, e-mails, files, directories, and whole disk partitions and
  * to increase the security of e-mail communications.
  *
  * # Attacks?
  * CSRF (Cross Site Request Forgery) Attack - mimics an authenticated user and a request from the user.
  * Browser will send cookie info automatically for a domain site. Attackers ask u to click pages that look original "phising"
  * & it sends unintended request on ur behalf. They replicate PUT/POST actions on html pages w/ cookie data.
  * Can change password, issue refund etc.
  * To prevent: (add as hidden field to form, never as cookie)
  * 1. A non-predictable, random token is generated using a secure function. This value is then provided to the client to return. The token is often generated per-session; a per-request token is more robust, if possible.
  * 2. Encryption based Token: A token is generated based on the user’s session id and a timestamp using a key only available on the server. The token is valid for a limited time period and limited number of requests or even single usage. The client then includes this value on future requests.
  * 3. HMAC Token: Using key K, generate HMAC(session ID + timestamp) and append the same timestamp value to it which results in your CSRF token. At the server, re-generate the token & compare.
  *
  */
}
