// src/api/graphqlClient.js
import { request, gql } from "graphql-request";
const GRAPHQL_URL = "http://localhost:8085/graphql";

export async function getZoneInfo(zone) {
    const query = gql`
    query ($zone: String!) {
      zoneInfo(zone: $zone) {
        zone
        aqi
        mainPollutant
        transportAvailable
        transportLines
        activeEmergencies
      }
    }
  `;
    return request(GRAPHQL_URL, query, { zone });
}

export async function planRoute(from, to, prefer) {
    const query = gql`
    query ($from: String!, $to: String!, $prefer: String!) {
      planRoute(from: $from, to: $to, prefer: $prefer) {
        route
        duration
        distance
      }
    }
  `;
    return request(GRAPHQL_URL, query, { from, to, prefer });
}
