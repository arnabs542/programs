package com.raj.SCALABALE_SYSTEMS;

public class GoogleMaps {
/**
 * Design google maps
 *
 * == High level
 * - Divide globe into blocks say 2x2 miles
 * - Google Cars & Satellite images are transformed and fed through data pipelines to build images & maps
 * - A GeoSpatial Indexing DB may be a good choice for storing such polygonal objects in space
 * - Store images by varying zoom levels, zoom & scroll will pull images as required from maps DB.
 * - For computing directions, a Graph DB may work where points of interest(address) are represented as vertices & roads as edges
 *
 * == Microservices/API
 *
 * # Search Service
 *   - User Query are normalized using NLP & then fed into sharded-distributed search nodes
 *   - Lucene Search can work, but a faster solution entails precomputing each term->list of addresses. Shard can be on term.
 *   - As we type more terms, results are fetched from shards and intersected yielding finer results
 *   - LRU Cache on shards, co-ordinator node scatter & gathers
 *
 * # Map Service
 *   - Stores maps by blocks
 *   - Caches heavily as map contents don't change frequently & build can be done nightly by MR jobs
 *   - DB: block_id, diag_lat_long, block_size, blob
 *   - Graph DB: block_id -> addresses edges & vertices , lookups by vertices
 *   - Precompute all pairs shortest paths using Dijkstra's/A* within a block and store top N options. Can be done only for most freq visited vertices. The last mile can be done in real time.
 *   - DB: (src,dest) -> top N paths graphs  ... use A* with some heuristics like time of day etc
 *
 * # Routing Service
 *   - Takes in src & dest addresses & returns top 5 best paths
 *   - [Slower] Run Dijkstra's shortest path b/w src & dest w/ path dist as weight. But may become computationally expensive for larger distances.
 *   - Use A* shortest path w/ traffic as heuristics.
 *   - Direction service calls Map Service to get top N precomputed paths b/w src to dest.
 *   - It then calls ETA service to get real time heuristics data & sort path options by shortest time
 *   - LRU cache w/ TTL recent results
 *
 * # ETA Service
 *   - Get updates from weather, traffic, govt sites, highway patrol etc
 *   - It periodically computes a weight based on above per vertex which is returned to Map service to be used as heuristics
 *   - The heuristics is done on block level
 */
}
