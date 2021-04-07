import { IconLogin, IconAtom2, IconAtom } from "@tabler/icons";
import React from "react";

import { Container, Image } from "react-bootstrap";
import { Link } from "react-router-dom";
import AppLogo from "../images/logo.svg";

const LandingPage = () => {
  return (
    <main>
      <section id="cover" className="min-vh-100">
        <div id="cover-caption">
          <Container>
            <div className="col-xl-5 col-lg-6 col-md-8 col-sm-10 mx-auto text-center form p-4">
              <div className="px-2">

                <Image src={AppLogo} rounded fluid className="mb-3" width={50} height={50} />
                <Container>
                  <Link to={"/login"}>
                    <IconLogin
                      size={36}
                      color="red"
                      stroke={3}
                      strokeLinejoin="miter"
                    />
                  Login </Link>
                </Container>
                <Container>
                  <Link to={"/register"}><IconAtom
                    size={36}
                    color="blue"
                    stroke={3}
                    strokeLinejoin="miter"
                  /> Register Account </Link>
                </Container>
                <Container>
                  <Link to={"/dashboard/testme"}>
                    <IconAtom2
                      size={36}
                      color="green"
                      stroke={3}
                      strokeLinejoin="miter"
                    />
                   Test me </Link>
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

export default LandingPage;
