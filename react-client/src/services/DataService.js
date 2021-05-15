import axios from "axios";
import authHeader from './AuthHeader'

const API_URL = '/api/v1';

export const saveStat = (stat) => {
  return axios.post(`${API_URL}/stats`, stat, { headers: authHeader() })
}

export const getStats = () => {
  return axios.get(`${API_URL}/stats`, { headers: authHeader() })
}

export const deleteStat = (stat) => {
  return axios.post(`${API_URL}/stats/del-stat`, stat, { headers: authHeader() })
}

export const getDailyStats = (filter) => {
  return axios.get(`${API_URL}/stats-daily?history=${filter}`, { headers: authHeader() })
}

export const getDailySleep = (filter) => {
  return axios.get(`${API_URL}/sleep-daily?history=${filter}`, { headers: authHeader() })
}

export const saveSleep = (sleep) => {
  return axios.post(`${API_URL}/sleep`, sleep, { headers: authHeader() })
}

export const deleteSleep = (sleep) => {
  return axios.post(`${API_URL}/sleep/del-sleep`, sleep, { headers: authHeader() })
}

export const startSleep = (sleep) => {
  return axios.post(`${API_URL}/start-sleep`, sleep, { headers: authHeader() })
}

export const endSleep = (sleep) => {
  return axios.post(`${API_URL}/end-sleep`, sleep, { headers: authHeader() })
}

export const getAdaptiveInput = () => {
  return axios.get(`${API_URL}/adaptive-input`, { headers: authHeader() })
}
