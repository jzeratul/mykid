import React, { useState, useMemo } from "react";
import './App.css'
import { UserContext } from "./routes/UserContext";
import { BrowserRouter, Switch } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import PageNotFound from "./pages/PageNotFound";
import SessionExpiredPage from "./pages/SessionExpiredPage";

import PublicRoute from "./routes/PublicRoute";
import PrivateRoute from "./routes/PrivateRoute";

function AppRouter() {
  const [user, setUser] = useState(null);

  const value = useMemo(() => ({ user, setUser }), [user, setUser]);

  return (
    <BrowserRouter>
      <UserContext.Provider value={value}>
        <Switch>
          <PublicRoute restricted={true} component={Login} path="/mykid/login" exact />
          <PublicRoute restricted={true} component={Register} path="/mykid/register" exact />
          <PublicRoute restricted={true} component={LandingPage} path="/mykid" exact />
          <PrivateRoute component={Dashboard} path="/mykid/dashboard" exact />
          <PrivateRoute component={SessionExpiredPage} path="/mykid/sessionexpired" exact />
          <PrivateRoute component={PageNotFound} path="*" />
        </Switch>
      </UserContext.Provider>
    </BrowserRouter>
  );
}

export default AppRouter;