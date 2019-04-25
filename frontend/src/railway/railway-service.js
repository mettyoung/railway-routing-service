import axios from "axios";

const BASE_URL = '/railway-routing-service';

export default class RailwayService {

  static getStations() {
    return axios.get(BASE_URL + "/stations")
      .then(response => response.data);
  }

  static computePaths(origin, target) {
    return axios.get(BASE_URL + "/compute-path", {
      params: {
        origin,
        target
      }
    }).then(response => response.data);
  }
}