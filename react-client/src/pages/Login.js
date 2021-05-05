import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";

import AuthService from "../services/AuthService";
import { Image } from "react-bootstrap";
import AppLogo from "../images/logo.svg";
import { Link } from "react-router-dom";

const Login = (props) => {
  const form = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault()

    setMessage("")

    if (username === "") {
      setMessage("username must not be empty")
    } else if (password === "") {
      setMessage("password must not be empty")
    } else {
      AuthService.login(username, password).then(
        () => {
          props.history.push("/dashboard")
          window.location.reload()
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
          setMessage(resMessage)
        }
      )
    }
  }

  return (
    <main className="hold-transition login-page">

      <div className="login-box">
        <div className="login-logo">
          <Image src={AppLogo} rounded fluid className="mb-3" width={50} height={50} />
        </div>

        <div className="card">
          <div className="card-body login-card-body">
            <p className="login-box-msg">Sign in to start your session</p>

            <Form onSubmit={handleLogin} ref={form}>
              <div className="input-group mb-3">
                <input
                  type="text"
                  className="form-control"
                  placeholder="username"
                  name="username"
                  value={username}
                  onChange={onChangeUsername}
                />
                <div className="input-group-append">
                  <div className="input-group-text">
                    <span className="fas fa-envelope"></span>
                  </div>
                </div>
              </div>
              <div className="input-group mb-3">
                <input
                  type="password"
                  className="form-control"
                  name="password"
                  value={password}
                  onChange={onChangePassword}
                  placeholder="password"
                />                  <div className="input-group-append">
                  <div className="input-group-text">
                    <span className="fas fa-lock"></span>
                  </div>
                </div>
              </div>

              <div className="col-4">
                <button type="submit" className="btn btn-primary btn-block">Sign In</button>
              </div>
              {message && (
                <div className="form-group">
                  <div className="alert alert-danger" role="alert">
                    {message}
                  </div>
                </div>
              )}
            </Form>

            <div>
              <Link className="mb-2" to={"/register"}> Register Account </Link><br />
              <Link className="mb-2" to={"/dashboard/testme"}>Test me </Link><br />
              <Link className="mb-2" to={"/"}>Back </Link><br />
              <b>Version</b> 1.0
            </div>
          </div>
        </div>
      </div>
    </main >
  );
};

export default Login;
