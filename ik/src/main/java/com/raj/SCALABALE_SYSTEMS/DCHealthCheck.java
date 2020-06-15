package com.raj.SCALABALE_SYSTEMS;

public class DCHealthCheck {
/**
 *
 * Datacenter: 10k machines
 * Design a system to automatically monitor and repair machines as they fail.
 *
 * Check machine health ? Health agent provides health, tells what is wrong
 * Repair ? Manual replacement. Redundancy is built in applications.
 *
 * 1 m/c: periodically checks agent. Alerting / Dashboard.
 *
 * Fleet:
 * Sharding of the fleet:
 * 1-100 => Health Check server 1
 *  1-50 serv01 51-100 server02
 * 101-200 => Server 2
 * Consistent Hashing
 *
 * Redundancy + Availability
 *
 *
 *
 * Pros and cons for health server polling vs machines pushing to health
 * Pull Based:
 * Health check app pulls state of machine ip ranges it owns (Zookeeper)
 * Paralley call the list of ips it has to do health check
 * Save this state in DB
 * If it is a failure, repair() + Component that has gone bad in case of failure
 * If the poll fails consistently for 5 mins -> mark that m/c bad
 * State per m/c
 * State co-ordination
 *
 * Push Based:
 * Machine has an agent that keeps pushing state into Health Check App URL
 * Every 10 secs, send state (good/bad)
 * Component that has gone bad in case of failure
 * Keep track of all 10K last updated pings
 * Agent can send the m/c ip, state => LB => Health Check App to update
 * To maintain state use of DB ()
 * 50% n/w chatter
 * LB can do Round Robin
 * Health Check Apps are stateless
 * OK ping : update in DB
 * Bad ping (Component bad?) : update DB + Repair()
 * No pings : Offline Periodic Batch Job running that detects the last updated ts > 5 mins the  repair()
 * 30 mins as a separate microservice - go to DB * Select bad m/c
 * Async Event Driven Messaging Architecture
 * State changing - url, async message queue
 * ADD, UP, DOWN, DELETED - with cause
 *
 * Bad:
 * 1. Component that is bad => Saved in DB. Then send to Recovery Service from App + Offline Job.
 *    Recovery Microservice
 *    Workflow based resolution
 *      State : ts, desc
 *      BAD,=> ASSIGNED_TECH, => UNDER_REPAIR, => REPAIRED
 *      Steps:
 * Create Ticket()
 * Rule based system to assign to owners
 * Track state of ticket
 * If Resolve, update DB (ip, state, desc, ticket)
 * Simple sanity check() if ping is done
 * Recovery workflow is complete.
 */
}
