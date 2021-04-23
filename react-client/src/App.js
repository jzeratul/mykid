import React, { useState, useMemo } from "react";
import './App.css'
import { UserContext } from "./routes/UserContext";
import { BrowserRouter, Switch } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import NewEntry from "./pages/NewEntry";
import Stats from "./pages/Stats";
import Graphs from "./pages/Graphs";
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
          <PublicRoute restricted={true} component={Login} path="/login" exact />
          <PublicRoute restricted={true} component={Register} path="/register" exact />
          <PublicRoute restricted={true} component={LandingPage} path="/" exact />
          <PrivateRoute component={Dashboard} path="/dashboard" exact />
          <PrivateRoute component={NewEntry} path="/new" exact />
          <PrivateRoute component={Stats} path="/stats" exact />
          <PrivateRoute component={Graphs} path="/graphs" exact />
          <PrivateRoute component={SessionExpiredPage} path="/sessionexpired" exact />
          <PrivateRoute component={PageNotFound} path="*" />
        </Switch>
      </UserContext.Provider>
    </BrowserRouter>
  );
}

export default AppRouter;