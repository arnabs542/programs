package com.raj.SCALABALE_SYSTEMS;

public class WhatsApp {
/**
 * https://www.youtube.com/watch?v=vvhC64hQZMk
 * Requirements:
 * 1. Group Messaging
 * 2. Sent + Delivered + Read receipts
 * 3. Online/Last Seen
 * 4. Image/Video sharing
 * 5. Chats are temp/permanent
 * 6. 1:1 chat
 *
 * == Microservices ==
 * # Gateway : Intercepts all requests from user, authenticates using a Profile service & then forward requests to appropriate service.
 * Also, for chat systems, Websocket connections can be maintained on these machines as they are expensive while the
 * internal service communication can happen over leaner more efficient protocols like http2 using protobuf,thrift etc.
 *
 * User --- Gateway --- Profile service
 *             |---- then --- Microservices
 *
 * Communication needs to be 2 way, using HTTP won't work as server also needs to update client.
 * => Long polling with 5s refresh can be used
 * => Better Use XMPP protocol / WebSockets using TCP
 *
 * # Sessions
 * UserA ---> Gateway Router ---> Session service --> DB  (user_id,session_server)
 * send(B)                        where's user B connected to?
 *
 * Workflow:
 * UserA ---> send(B)      ----> Session server 1
 * UserB <--- send(B)      <---- Session server 2
 *
 * >> Both server & DB is sharded on user. We need to know where session+data lies for each user before sending msgs.
 * >> Use message Queues to abstract durable msg delivery, ordering of msgs, exactly once semantics, retries to client/server etc.
 * - We use WebSocket(duplex) connection over TCP. Both Client Server can send messages anytime.
 * - User A sends msg to B. Session server1 stores msg in DB, sends "Received at server" to User A.
 * - Session server then looks up UserB's session. Finds that it's on session server 2. It then sends this message to userB's session server 2.
 * - Session server 2 sees the User B as offline(user_id, last_online)
 * - Session server 2 saves the msg in user B DB row (to: userB, from: userA, msg_content, status: received_server). Message terminates. A delivery is pending.
 * - User B comes Online. The session server marks user online on say session server 2.
 * - Session server 2, looks up pending msgs from DB & forwards it. Marks status as "sent".
 * - User B reads the message, app sends this info & session server 2 updates DB row (to: userB, from: userA, msg_content, status: sent)
 * - Session server 2 looks up user A's server & forwards "sent". Session server 1 saves in user A's sharded DB & if online forwards message "sent" to A.
 *
 * # Group Service:
 * - Maintains a mapping of user's in a group. When a message is sent to a group, Group service handles the orchestration than direct session server.
 * - It finds where sessions are maintained for each user & delivers them.
 *
 * # Media Service:
 * - Stores images/videos on Distributed File Storage / Cloud Storage. We don't need DB for this as it isn't optimized for blob storage & not fast for this.
 * - Store just the file url in DB while actual static content can reside on CDN.
 *
 * # During high loads - Holidays,New Year etc
 * - Services can work in de-prioritized mode, meaning it can do aux features like sent/received in async mode.
 * - Services focus more about prompt sends of message from sender to receiver during these times thereby operating faster.
 * - Rate Limits per User/Group is applied. Communication after Gateway can happen over Message Queues to overcome this as well.
 *
 */
}
