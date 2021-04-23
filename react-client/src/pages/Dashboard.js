import React from "react"
import Topbar from "./Topbar"

const App = (props) => {

  return (
    <>

      <Topbar />

      <div>
        <div className="content-header">
          <div className="container-fluid">
          </div>
        </div>
        <section className="content">
          <div className="container-fluid">

            <div className="row">
              <div className="col-12 col-sm-6 col-md-3">
                <div className="info-box">
                  <span className="info-box-icon bg-info elevation-1"><i className="fas fa-weight"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">CPU Traffic</span>
                    <span className="info-box-number">
                      10
                  <small>%</small>
                    </span>
                  </div>
                </div>
              </div>
              <div className="col-12 col-sm-6 col-md-3">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-danger elevation-1"><i className="fas fa-thumbs-up"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Likes</span>
                    <span className="info-box-number">41,410</span>
                  </div>
                </div>
              </div>

              <div className="clearfix hidden-md-up"></div>

              <div className="col-12 col-sm-6 col-md-3">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-success elevation-1"><i className="fas fa-shopping-cart"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Sales</span>
                    <span className="info-box-number">760</span>
                  </div>
                </div>
              </div>
              <div className="col-12 col-sm-6 col-md-3">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-warning elevation-1"><i className="fas fa-users"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">New Members</span>
                    <span className="info-box-number">2,000</span>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </section>
      </div>
    </>
  )
}

export default App
