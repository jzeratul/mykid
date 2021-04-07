import React from "react";
import { Route, Redirect } from "react-router-dom";
import AuthService from "../services/AuthService";

const PublicRoute = ({ component: Component, restricted, ...rest }) => (
  // restricted = false meaning public route
  // restricted = true meaning restricted route
  <Route
    {...rest}
    render={(props) =>
      AuthService.isLoggedIn() && restricted ? (
        <Redirect to="/dashboard" />
      ) : (
        <Component {...props} />
      )
    }
  />
);

export default PublicRoute;
