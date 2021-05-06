import React from "react"
import { Navbar, Image, Nav } from "react-bootstrap"
import AppLogo from "../images/logo.svg"
import { logout } from "../services/AuthService"
import { useHistory } from "react-router-dom";


const Topbar = (props) => {

  let history = useHistory();

  const _logout = () => {
    logout()
    history.push('/login')

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
              <Nav.Link href="" onClick={_logout}>
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
