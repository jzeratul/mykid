import React, { useState, useRef } from "react"
import Form from "react-validation/build/form"
import { isEmail } from "validator"
import AppLogo from "../images/logo.svg"

import AuthService from "../services/AuthService"
import { Image } from "react-bootstrap"
import { Link } from "react-router-dom/cjs/react-router-dom.min"

const required = (value) => {
  if (!value) {
    return (<div className="alert alert-danger" role="alert"> This field is required! </div>)
  }
};

const validEmail = (value) => {
  if (!isEmail(value)) {
    return (<div className="alert alert-danger" role="alert"> This is not a valid email. </div>)
  }
};

const vusername = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (<div className="alert alert-danger" role="alert"> The username must be between 3 and 20 characters. </div>)
  }
}

const vpassword = (value) => {
  if (value.length < 6 || value.length > 40) {
    return (<div className="alert alert-danger" role="alert"> The password must be between 6 and 40 characters. </div>)
  }
}

const Register = (props) => {
  const form = useRef()
  const checkBtn = useRef()

  const [username, setUsername] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [password1, setPassword1] = useState("")
  const [successful, setSuccessful] = useState(false)
  const [message, setMessage] = useState("")

  const onChangeUsername = (e) => {
    const username = e.target.value
    setUsername(username)
  }

  const onChangeEmail = (e) => {
    const email = e.target.value
    setEmail(email)
  }

  const onChangePassword = (e) => {
    const password = e.target.value
    setPassword(password)
  };

  const onChangePassword1 = (e) => {
    const password1 = e.target.value
    setPassword1(password1)
  };

  const handleRegister = (e) => {
    e.preventDefault()

    setMessage("")
    setSuccessful(false)

    if (password !== password1) {
      setMessage("passwords do not match")
      return
    }

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.register(username, email, password).then(
        (response) => {
          setMessage(response.data.message)
          setSuccessful(true)
        },
        (error) => {
          const resMessage = (error.response && error.response.data && error.response.data.message) ||
            error.message ||
            error.toString()

          setMessage(resMessage)
          setSuccessful(false)
        }
      )
    }
  }

  return (

    <main className="hold-transition register-page">

      <div className="register-box">
        <div className="register-logo">
          <Image src={AppLogo} rounded fluid className="mb-3" width={50} height={50} />
        </div>

        <div className="card">
          <div className="card-body register-card-body">
            <p className="login-box-msg">Register a new membership</p>

            <Form onSubmit={handleRegister} ref={form}>
              <div className="input-group mb-3">
                <input type="text" className="form-control" name="username" value={username} onChange={onChangeUsername} validations={[required, vusername]} />
                <div className="input-group-append">
                  <div className="input-group-text">
                    <span className="fas fa-user"></span>
                  </div>
                </div>
              </div>
              <div className="input-group mb-3">
                <input type="text" className="form-control" name="email" value={email} onChange={onChangeEmail} validations={[required, validEmail]} />
                <div className="input-group-append">
                  <div className="input-group-text">
                    <span className="fas fa-envelope"></span>
                  </div>
                </div>
              </div>
              <div className="input-group mb-3">
                <input type="password" className="form-control" name="password" value={password} onChange={onChangePassword} validations={[required, vpassword]} />
                <div className="input-group-append">
                  <div className="input-group-text">
                    <span className="fas fa-lock"></span>
                  </div>
                </div>
              </div>
              <div className="input-group mb-3">
                <input type="password" className="form-control" name="password1" value={password1} onChange={onChangePassword1} validations={[required, vpassword]} />
                <div className="input-group-append">
                  <div className="input-group-text">
                    <span className="fas fa-lock"></span>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col-8">
                  <div className="icheck-primary">
                    <input type="checkbox" id="agreeTerms" name="terms" value="agree" />
                    <label for="agreeTerms">
                      TODO: I agree to the <a href="#">terms</a>
                    </label>
                  </div>
                </div>
                <div className="col-4">
                  <button type="submit" className="btn btn-primary btn-block">Register</button>
                </div>
              </div>
            </Form>

            <div className="social-auth-links text-center">

              <div>
                <Link className="mb-2" to={"/login"}> I have an account </Link><br />
                <Link className="mb-2" to={"/dashboard/testme"}>Test me </Link><br />
                <Link className="mb-2" to={"/"}>Back </Link><br />
                <b>Version</b> 1.0
            </div>
            </div>
          </div>

        </div>
      </div>
    </main >
  )
}

export default Register