import axios from "axios";

const BASE_URL = "http://localhost:8085/gateway";

export async function planRoute(from, to, prefer) {
  const params = { from, to };
  if (prefer) params.prefer = prefer;

  const response = await axios.get("http://localhost:8085/gateway/planRoute", { params });
  // On descend dans data.data pour accéder à planRoute
  return response.data.data.planRoute;
}

