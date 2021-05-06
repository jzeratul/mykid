import axios from "axios"

const API_URL = "/api/v1/auth/"

export const register = (username, email, password) => {
  return axios.post(API_URL + "signup", { username, email, password })
}

export const login = (username, password) => {
  return axios
    .post(API_URL + "login", { username, password })
    .then((response) => {
      if (response.data.jwtToken) {
        localStorage.setItem("user", JSON.stringify(response.data))
      }
      return response
    })
}

export const logout = () => {
  localStorage.removeItem("user")
}

export const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"))
}

export const isLoggedIn = () => {
  return !!getCurrentUser()
}