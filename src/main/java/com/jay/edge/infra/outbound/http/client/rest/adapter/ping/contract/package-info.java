/**
 * Dummy response contract for a downstream ping endpoint.
 *
 * <p>
 * This type represents the shape of a ping response returned by an outbound
 * HTTP dependency. In a real service, this would typically be a generated POJO
 * derived from an API contract (for example, OpenAPI).
 * </p>
 *
 * <p>
 * It exists here to model the dependency boundary without introducing code
 * generation into the edge.
 * </p>
 */
package com.jay.edge.infra.outbound.http.client.rest.adapter.ping.contract;