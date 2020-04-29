package com.raj.SCALABALE_SYSTEMS;

public class Instagram {

/**
 * https://www.educative.io/courses/grokking-the-system-design-interview/m2yDVZnQ8lG
 *
 *
 1. Design a Photo Upload/View App
 2. End to End
 3. Picture Sharing, upload
 4. Mobile phone app, view pics
 - Search user & see their pics
 5. Desktop app
 6. API
 7. Secured, logged in


 1 MB max pic upload
 1B uploads per day
 1B Users
 10B Pic Downloads/day

 # Capacity Estimates
 * Storage (Very High)
 1,000,000,000 * 1MB / day = 1000 TB / day * 5 * 300 = 1500,000 TB for foreseeable future

 * Read QPS / Write QPS
 10B pic downloads
 1B uploads
 1B users
 Reads = 100K reads/sec
 Writes = 10K writes/sec

 App = 1000 reads/sec = 100 App VMs
 DB = 1500 TB = 10TB = 150 DB boxes

 * B/W (Very High)
 - Use of CDNs for Photos, use microservices for meta info & backend logic

 # Architecture:

 LB(s) ----> AppServer ----> Cache ----> DB

 Components/Microservices:
 -- RESTful APIs --

 * Login / Registration service - user_id, email_id, first name, last name, encrypted pwd
 - user_id generated : uuid+firstname+ts
 other approaches: id generation service - DB sequence, pull based range based ids
 - We'll authenticate on login, user_id+token in cache for further validations

 * Photo Upload Service:
 - upload(user_id,<blob> photo)
 Distributed KV Store
 DB Schema: user_id(PK), ts(Clustering Key), photo_url ---> Amazon S3, HDFS
 - Shard in user_id (Cons - hot-spotting on popular users)
 - For celebrity we can have their data cached so that hot-spotting cpould be avoided.
 - Map Reduce to update or Kafka -> Streaming Storm -> Cache
 - Photos upload can be made visible through use of LongPoll/WebSocket connections

 * Search Service:
 - search(user name) : Returns searched users
 - Solr Lucene Search Index (inverted on the names -> user_id)
 - Precomputed HashMap (names -> user_id) .. Map Reduce precomputed index and sends to different shard
 - Shard Key: names

 * Photo Download Service:
 - getPhotos(user_id)
 - Cache photos on CDNs


 * Common Design Themes
 - Microservices: RESTful APIs
 - Sharded
 - Consistent Hashing - helps add/remove machines range based hashes for each shard 0-50, 51-100, 101-200
 - Replication: Shard replicas (3)
 - Caching: Write/Read Through Cache, LRU eviction policy
 - CDNs Geo-located around globe for faster retrieval of media

 *
 *
 *
 *
 *
 *
 */
}
