"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports["default"] = void 0;

var _axios = _interopRequireDefault(require("axios"));

var _AuthHeader = _interopRequireDefault(require("./AuthHeader"));

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { "default": obj }; }

var API_URL = '/mykid/api/v1/stats';

var saveStat = function saveStat(stat) {
  return _axios["default"].post("".concat(API_URL), stat, {
    headers: (0, _AuthHeader["default"])()
  });
};

var getStats = function getStats(stat) {
  return _axios["default"].get("".concat(API_URL), {
    headers: (0, _AuthHeader["default"])()
  });
};

var _default = {
  saveStat: saveStat,
  getStats: getStats
};
exports["default"] = _default;