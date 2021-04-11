import React from "react"
import { Tab, Container, Navbar, Image, Nav } from "react-bootstrap"
import Tabs from 'react-bootstrap/Tabs'
import AppLogo from "../images/logo.svg"
import AuthService from "../services/AuthService"
import TabNewEntry from "./TabNewEntry"
import TabStats from "./TabStats"
import TabGraphs from "./TabGraphs"

const App = (props) => {

  const logout = () => {
    AuthService.logout()
    props.history.push("/login")
    window.location.reload()
  }

  return (
    <main>

      <Navbar bg="dark" expand="lg" variant="dark">

        <Image src={AppLogo} width={20} height={20} rounded fluid className="mr-3" />
        <Navbar.Brand>MyKid</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">

          </Nav>
          <Nav className="justify-content-end">

            <Nav.Item>
              <Nav.Link to="" onClick={logout}>
                logout
              </Nav.Link>
            </Nav.Item>
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      <Container className="mt-4 mb-4">

        <Tabs defaultActiveKey="home" transition={false} id="noanim-tab-example">
          <Tab eventKey="home" title="New Entry">

            <TabNewEntry />

          </Tab>
          <Tab eventKey="stats" title="Stats">

            <TabStats />

          </Tab>
          <Tab eventKey="graphs" title="Graphs">

            <TabGraphs />

          </Tab>
        </Tabs>
      </Container>
    </main >
  )
}

export default App
