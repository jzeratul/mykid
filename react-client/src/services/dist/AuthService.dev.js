"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _axios = _interopRequireDefault(require("axios"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var API_URL = "/api/v1/auth/";

var register = function register(username, email, password) {
  return _axios["default"].post(API_URL + "signup", {
    username: username,
    email: email,
    password: password
  });
};

var login = function login(username, password) {
  return _axios["default"].post(API_URL + "login", {
    username: username,
    password: password
  }).then(function (response) {
    if (response.data.jwtToken) {
      localStorage.setItem("user", JSON.stringify(response.data));
    }

    return response.data;
  });
};

var logout = function logout() {
  localStorage.removeItem("user");
};

var getCurrentUser = function getCurrentUser() {
  return JSON.parse(localStorage.getItem("user"));
};

var isLoggedIn = function isLoggedIn() {
  return !!getCurrentUser();
};

var _default = {
  register: register,
  login: login,
  logout: logout,
  getCurrentUser: getCurrentUser,
  isLoggedIn: isLoggedIn
};
exports["default"] = _default;