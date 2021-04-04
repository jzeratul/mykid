import React from "react";
import { Link } from "react-router-dom/cjs/react-router-dom.min";

const SessionExpiredPage = () => (
  <div className="app-404" isHeader={false}>
    <h1>Session expried</h1>
    <Link to="/mykid">Back to main page</Link>
  </div>
);

export default SessionExpiredPage;
