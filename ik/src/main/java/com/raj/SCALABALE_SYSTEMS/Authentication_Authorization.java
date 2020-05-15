package com.raj.SCALABALE_SYSTEMS;

public class Authentication_Authorization {
/**
 * Build Authentication & Authorization for a microservice?
 * https://medium.com/tech-tajawal/microservice-authentication-and-authorization-solutions-e0e5e74b248a
 *
 * Basics:
 * Authentication: Verify who you are, so you need to use username and password for authentication.
 * Authorization: What you can do, for example access, edit or delete permissions to some documents, and this happens after verification passes.
 *
 * Approaches:
 * [Client Server]
 * 1. Distributed Session Mgmt: Session Id generated after auth.
 *    - Session replication? all servers syncs sessionId - bad
 *    - Sticky Session - goes to 1 server - not horizontal scalable
 *    - Central Session storage - stored in a centralized server - better
 *
 * 2. Client Token:
 *    - Main difference between Token and Session is where the storage is different.
 *    - Sessions are stored centrally in the server; Tokens are held by the user themselves and are typically stored in the browser in the form of cookies.
 *    - The Token holds the user’s identity information, and each time the request is sent to the server, the server can therefore determine the identity of the visitor and determine whether it has access to the requested resource.
 *
 * 3. Client Token with API Gateway [refer to Auth_Token.jpg]
 * - All login through a single Auth Service at the API Gateway layer
 * - It authenticates user_id & pwd & generates a token or sessionId which can be stored on client's cookie or url-rewrite
 * - It can also authorize permissions for the user
 * - Use TLS security/encryption for reducing chance of attacks
 * - Effectively hides the microservices. On request, the API gateway translates the original user token into an opaque token that only itself can resolve (extra layer of security to JWT)
 *
 * Single Sign On
 * - Mostly an extension of above where a agent on app server talks to SSO server using SAML or Kerberos to authenticate & authorize
 * - An access token is generated which is then used to grant access to services.
 *
 * OAuth2 + Json Web Token (JWT)
 * - Instead of building your own Auth service, use OAuth to delegate it to Google,FB etc
 * - Once authenticated, use signed JWT tokens for further communication
 * - JWT is structured & specification driven
 * - They are signed:
 *   -> Use either a shared secret signing algo like HMAC
 *      - HMAC stands for Hash based Message Authentication Code & is used to verify integrity of message(not tampered by someone)
 *      - Can be used by any crypto hash fn like SHA-1/256/512, MD5
 *      - Bob & Alice want to communicate. They share a secret key K.
 *      - Bob generates a HMAC-SHA1 hash using (key K & message M). Sends it over to Alice with message M & hash HMAC.
 *      - Alice recalculates HMAC hash using (M,K) & verifies if the hashes match. This way she gets accurate message & knows it comes from Bob as he has key K.
 *   -> Public/Private key signing algo like RSA Asymmetric Encryption: https://www.youtube.com/watch?v=AQDCe585Lnc
 *      - Alice & Bob want to communicate. They both generate Public & Private keys using RSA.
 *      - They share their public keys w/ each other. When communicating, they will use other's public key to encrypt message.
 *      - Only the recipient can decrypt w/ their private key.
 *      - Now someone spoofing the messages only sees encrypted messages and even if they have the public key, they can't decrypt it.
 *      - Asymmetric encryption is used by SSH,Bitcoin & HTTPS(for encryption)/TLS(only for signature & encryption is negotiated by a key exchange algo)
 * - JWT has Base-64 Header telling the algo like SHA256, a Body w/ expiration & RSA or HMAC Signature
 * - You can rotate keys & specify the key_version
 * - Use 2 Factor Authentication for more security:
 *   - After verifying credentials, ask for a 2FA code that is pushed to user's phone
 *   - User sends 2FA code & then login service sends back the JWT (authenticated w/ permissions)
 *
 * [Server Server]
 * 1. Mutual Authentication
 * Through mutual SSL, mutual authentication between microservices can be achieved, and data transmission b/w microservices
 * can be encrypted through TLS. A certificate needs to be generated for each microservice, and the microservices are
 * authenticated with each other’s certificates. May become unmanageable if too many services exists & they are added/deleted.
 * We can create a private certificate center (Internal PKI/CA) to provide certificate management for various microservices
 * such as issuing, revoking, and updating.
 */
}
