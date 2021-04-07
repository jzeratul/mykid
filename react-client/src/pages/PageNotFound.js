import React from "react";
import { Link } from "react-router-dom/cjs/react-router-dom.min";

const PageNotFound = () => (
  <div className="app-404" isHeader={false}>
    <h1>Page not found 404</h1>
    <Link to="/dashboard">Back_To_Home</Link>
  </div>
);

export default PageNotFound;
