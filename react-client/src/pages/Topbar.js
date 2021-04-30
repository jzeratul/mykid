import React from "react"
import { Navbar, Image, Nav } from "react-bootstrap"
import AppLogo from "../images/logo.svg"
import AuthService from "../services/AuthService"

const Topbar = (props) => {

  const logout = () => {
    AuthService.logout()
    props.history.push("/login")
    window.location.reload()
  }

  return (
    <>
      <Navbar bg="dark" expand="lg" variant="dark">

        <Image src={AppLogo} width={20} height={20} rounded fluid className="mr-3" />
        <Navbar.Brand>MyKid</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">

            <Nav.Item>
              <Nav.Link href="/dashboard">
                Home
              </Nav.Link>
            </Nav.Item>

            <Nav.Item>
              <Nav.Link href="/stats">
                Stats
              </Nav.Link>
            </Nav.Item>

            <Nav.Item>
              <Nav.Link href="/new">
                New entry
  </Nav.Link>
            </Nav.Item>

            <Nav.Item>
              <Nav.Link href="/sleep">
                Sleep
  </Nav.Link>
            </Nav.Item>

          </Nav>
          <Nav className="justify-content-end">

            <Nav.Item>
              <Nav.Link href="" onClick={logout}>
                logout
              </Nav.Link>
            </Nav.Item>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    </>
  )
}

export default Topbar
