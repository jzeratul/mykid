import axios from "axios";
import authHeader from './AuthHeader'

const API_URL = '/api/v1/stats';

const saveStat = (stat) => {
  return axios.post(`${API_URL}`, stat, { headers: authHeader() })
}

const getStats = () => {
  return axios.get(`${API_URL}`, { headers: authHeader() })
}

const deleteStat = (stat) => {
  return axios.post(`${API_URL}/del-stat`, stat, { headers: authHeader() })
}

export default {
  saveStat,
  getStats,
  deleteStat
}