import axios from "axios";

const API_URL = "/mykid/api/v1/auth/";

const register = (username, email, password) => {
  return axios.post(API_URL + "signup", { username, email, password });
};

const login = (username, password) => {
  return axios
    .post(API_URL + "login", { username, password })
    .then((response) => {
      if (response.data.jwtToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }

      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const isLoggedIn = () => {
  return !!getCurrentUser();
};

export default {
  register,
  login,
  logout,
  getCurrentUser,
  isLoggedIn,
};
