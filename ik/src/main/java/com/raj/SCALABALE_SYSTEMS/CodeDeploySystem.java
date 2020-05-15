package com.raj.SCALABALE_SYSTEMS;

public class CodeDeploySystem {
/**
 * Build a sclable code build & deploy system
 * https://www.youtube.com/watch?v=q0KGYwNbf-0
 *
 * == Requirements:
 * # Thousands of parallel Builds can take place
 * # Binaries can be of GB in size
 * # Global deployments
 *
 * == Build Service
 * - Hooks with source code repo, that will trigger a build in async manner over a Kafka Q w/ SHA of commit
 * [Streaming pattern]
 * - A fault tolerant consumer dequeue build events & sends to build to workers
 * - Worker pulls the source code from central repo & builds binaries
 * - Acks if build succeeds else fails with error which is relayed back to the build UI
 * - If the worker dies, the master can retry after a set interval onto another worker, until it hears back a successful ACK
 * - Master can update a ACID MySQL DB with (build_id, build_worker_ip, created_date, states - START,RUNNING,SUCCESS,FAIL, end_date, error_desc)
 * - UI can use this DB to periodically pull build info & current state
 * - If more debug info is needed about build, then UI can use the build_worker_ip to pull logs
 * - DB can handle concurrent writes as the master workers will be few & maintain consistent state
 * - Workers may be in 1000s which we are not allowing to write to DB
 *
 * == Deploy Service
 * - Once the build is done, we need to upload binaries globally for efficient deployment
 * - Build worker can upload master copy of binary into cloud storage like S3 / GCS etc (which guarantees availability)
 * - S3 buckets shud be available in diff regions of the world like Asia,America,Europe etc
 * - Otherwise we can also use CDNs for faster transfers
 * - A Deploy Service will take build further into deploys.
 * - MySQL DB schema(deploy_id_by_region, build_id, state, binary_url, start_date, end_date)
 * - Each region has a master to co-ordinate deploys to servers & has a separate deploy_id
 * - It kicks off a download onto servers via sftping a script to wget from cloud storage, deflate, stop, start process
 * - Since the binary can be in Gigs & 1000s of servers can download the same binary, we can use P2P or Peer-2-Peer n/w
 * - In P2P, each server now acts as a both client & server to other nodes in regional n/w. The binary is broken down into multiple parts.
 * - It maintains a tracker/zookeeper state for nodes in the region, what parts have they downloaded that can be shared etc
 * - Part files are sftped to servers where they start downloading either from S3 or neighboring nodes, if they have it
 *
 * P2P Network: https://en.wikipedia.org/wiki/Peer-to-peer
 * Peer-to-peer networks generally implement some form of virtual overlay network on top of the physical network topology,
 * where the nodes in the overlay form a subset of the nodes in the physical network. Data is still exchanged directly
 * over the underlying TCP/IP network, but at the application layer peers are able to communicate with each other directly,
 * via the logical overlay links (each of which corresponds to a path through the underlying physical network). Overlays
 * are used for indexing and peer discovery, and make the P2P system independent from the physical network topology.
 * In P2P networks, clients both provide and use resources. This means that unlike client-server systems, the content-serving
 * capacity of peer-to-peer networks can actually increase as more users begin to access the content (especially with
 * protocols such as Bittorrent that require users to share, refer a performance measurement study[42]). This property is
 * one of the major advantages of using P2P networks because it makes the setup and running costs very small for the original
 * content distributor.
 * eg. WebTorrents, Bitcoin, Microsoft in Windows 10 uses a proprietary peer to peer technology called "Delivery Optimization" to deploy operating system updates using end-users PCs either on the local network or other PCs. According to Microsoft's Channel 9 it led to a 3%-50% reduction in Internet bandwidth usage
 */
}
