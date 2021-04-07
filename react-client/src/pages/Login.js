import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import AuthService from "../services/AuthService";
import { Col, Container, Image, Row } from "react-bootstrap";
import { IconUserPlus, IconUserX } from "@tabler/icons";
import AppLogo from "../images/logo.svg";
import { Link } from "react-router-dom";

const Login = (props) => {
  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
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
    e.preventDefault();

    setMessage("");
    setLoading(true);

    if (username === "") {
      setMessage("username must not be empty");
    } else if (password === "") {
      setMessage("password must not be empty");
    } else {
      AuthService.login(username, password).then(
        () => {
          props.history.push("/dashboard");
          window.location.reload();
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          setMessage(resMessage);
        }
      );
    }

    setLoading(false);
  };

  return (
    <main>
      <section id="cover" className="min-vh-100">
        <div id="cover-caption">
          <Container>
            <div className="col-xl-5 col-lg-6 col-md-8 col-sm-10 mx-auto text-center form p-4">
              <div className="px-2">

                <Image src={AppLogo} rounded fluid className="mb-3" width={50} height={50} />

                <p className="login-box-msg">Sign in</p>

                <Form onSubmit={handleLogin} ref={form}>

                  <div className="input-group mb-12">
                    <Input
                      type="text"
                      className="form-control"
                      placeholder="username"
                      name="username"
                      value={username}
                      onChange={onChangeUsername}
                    />
                    <div className="input-group-append">
                      <div className="input-group-text">
                        <IconUserPlus
                          size={16}
                          color="gray"
                          stroke={3}
                          strokeLinejoin="miter"
                        />
                      </div>
                    </div>
                  </div>

                  <div className="input-group mb-12 mt-3">

                    <Input
                      type="password"
                      className="form-control"
                      name="password"
                      value={password}
                      onChange={onChangePassword}
                      placeholder="password"
                    />

                    <div className="input-group-append">
                      <div className="input-group-text">
                        <IconUserX
                          size={16}
                          color="gray"
                          stroke={3}
                          strokeLinejoin="miter"
                        />
                      </div>
                    </div>
                  </div>

                  <div className="row mt-3">
                    <div className="col-4">

                      <div className="form-group">
                        <button
                          className="btn btn-primary btn-block"
                          disabled={loading}
                        >
                          {loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                          )}
                          <span>Login</span>
                        </button>
                      </div>


                    </div>
                  </div>

                  {message && (
                    <div className="form-group">
                      <div className="alert alert-danger" role="alert">
                        {message}
                      </div>
                    </div>
                  )}
                  <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>

                {/* <div className="social-auth-links text-center mb-3">
              <p>- OR -</p>

              <div className="btn-group">
                <a className="btn btn-app" href="/"> <IconBrandApple size={36} color="blue" stroke={2} /> </a>
                <a className="btn btn-app" href="/"> <IconBrandBitbucket size={36} color="blue" stroke={2} /> </a>
                <a className="btn btn-app" href="/"> <IconBrandDiscord size={36} color="gray" stroke={2} /> </a>
                <a className="btn btn-app" href="/"> <IconBrandGoogle size={36} color="red" stroke={2} /> </a>
              </div>

              <div className="btn-group">
                <a className="btn btn-app" href="/"> <IconBrandFacebook size={36} color="blue" stroke={2} /> </a>
                <a className="btn btn-app" href="/"> <IconBrandGithub size={36} color="black" stroke={2} /> </a>
                <a className="btn btn-app" href="/"> <IconBrandGitlab size={36} color="orange" stroke={2} /> </a>
                <a className="btn btn-app" href="/"> <IconBrandLinkedin size={36} color="blue" stroke={2} /> </a>
              </div>

            </div> */}


                <Container>
                  <Row>
                    <Col><Link to={"/register"}> Register Account </Link></Col>
                    <Col><Link to={"/dashboard/testme"}>Test me </Link></Col>
                    <Col><Link to={"/"}>Back </Link></Col>
                  </Row>
                </Container>

              </div>
            </div>
            <b>Version</b> 1.0
          </Container>
        </div>
      </section >
    </main >
  );
};

export default Login;
