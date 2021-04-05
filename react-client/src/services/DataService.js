import axios from "axios";
import authHeader from './AuthHeader'

const API_URL = '/mykid/api/v1/stats';

const saveStat = (stat) => {
  return axios.post(`${API_URL}`, stat, { headers: authHeader() })
}

const getStats = (stat) => {
  return axios.get(`${API_URL}`, { headers: authHeader() })
}

export default {
  saveStat,
  getStats
}