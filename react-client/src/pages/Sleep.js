import React, { useState, useEffect } from "react"
import Topbar from "./Topbar"
import Timer from "react-compound-timer"
import { getDailySleep, saveSleep } from '../services/DataService'
import { logout } from "../services/AuthService"
import { Button, Card, Row, Tooltip } from "react-bootstrap"
import { CartesianGrid, Line, LineChart, ResponsiveContainer, XAxis, YAxis } from "recharts"
import { useHistory } from "react-router-dom";

// const GENERIC_ACTIVITIES = ["VITAMIN_D", "VITAMIN_K", "BONNISAN", "SPIT", "URINE", "POOP",
//   "BREAST_MILK", "BOTTLE_MOTHER_MILK", "BOTTLE_FORMULAE_MILK", "TEMPERATURE", "PUMP", "WEIGHT"]

const Sleep = (props) => {
  let history = useHistory()

  const [filter, setFilter] = useState("today")
  const [initialTime, setInitialTime] = useState(0);
  const [startImmediately, setStartImmediately] = useState(false);
  const [data, setData] = useState([])
  const [started, setStarted] = useState(false)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = () => {
    getDailySleep().then(
      (response) => {
        setData(response.data.dailyStats)
      },
      (error) => {
        if (403 === error.response.data.status) {
          logout()
          history.push('/login')
        }
      })
  }

  const applyFilter = (filter) => {
    setFilter(filter)

    loadData(filter);
  }

  const timerStarted = () => {
    saveSleep({
      createdAt: new Date(),
      sleepStart: new Date()
    }).then(
      (response) => {
        loadData()
      },
      (error) => {
        if (403 === error.response.data.status) {
          logout()
          history.push('/login')
        }
      })
  }

  const timerStopped = () => {

    setInitialTime(0)
  }

  // const msToTime = (duration) => {
  //   var
  //     // milliseconds = parseInt((duration % 1000) / 100),
  //     seconds = Math.floor((duration / 1000) % 60),
  //     minutes = Math.floor((duration / (1000 * 60)) % 60),
  //     hours = Math.floor((duration / (1000 * 60 * 60)) % 24);

  //   hours = (hours < 10) ? "0" + hours : hours;
  //   minutes = (minutes < 10) ? "0" + minutes : minutes;
  //   seconds = (seconds < 10) ? "0" + seconds : seconds;

  //   return hours + ":" + minutes + ":" + seconds;
  // }

  return (
    <>

      <Topbar />

      <div className="content-header">
        <div className="container-fluid">
          <div className="row mb-2">

            <div className="col-12">
              <div className="breadcrumb float-sm-right">

                <div className="btn-group">
                  <Button type="button" className="btn btn-xs" variant={filter === "today" ? "primary" : "link"} onClick={() => applyFilter("today")}>Today</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "yesterday" ? "primary" : "link"} onClick={() => applyFilter("yesterday")}>Yesterday</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "last24h" ? "primary" : "link"} onClick={() => applyFilter("last24h")}>Last 24h</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "last48h" ? "primary" : "link"} onClick={() => applyFilter("last48h")}>Last 48h</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "last7d" ? "primary" : "link"} onClick={() => applyFilter("last7d")}>Last 7d</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "last1m" ? "primary" : "link"} onClick={() => applyFilter("last1m")}>Last 1m</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "last3m" ? "primary" : "link"} onClick={() => applyFilter("last3m")}>Last 3m</Button>
                  <Button type="button" className="btn btn-xs" variant={filter === "alltime" ? "primary" : "link"} onClick={() => applyFilter("alltime")}>All time</Button>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="mt-4">
        <section className="content">
          <div className="container-fluid">

            <Row>
              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box">
                  <span className="info-box-icon bg-success elevation-1"><i className="fas fa-bed"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Start/Stop sleep </span>
                    <span className="info-box-number">

                      <Timer
                        initialTime={initialTime}
                        startImmediately={startImmediately}
                        lastUnit="h"
                        onStart={() => {
                          setStarted(true)
                          timerStarted()
                        }}
                        // onResume={() => console.log('onResume hook')}
                        // onPause={() => console.log('onPause hook')}
                        onStop={() => {
                          setStarted(false)
                          timerStopped()
                        }}
                      // onReset={() => console.log('onReset hook')}
                      >
                        {({ start, resume, pause, stop, reset, timerState }) => (
                          <React.Fragment>
                            <div>
                              <Timer.Hours /> h
                              <Timer.Minutes /> m
                              <Timer.Seconds /> s
                            </div>

                            <br />
                            <div>
                              {!started && <Button className="btn btn-lg" variant="info" onClick={start}>Start</Button>}
                              {/* <button onClick={pause}>Pause</button>
                              <button onClick={resume}>Resume</button> */}
                              {started && <Button className="btn btn-lg" variant="danger" onClick={stop}>Stop</Button>}
                              {/* <button onClick={reset}>Reset</button> */}
                            </div>
                          </React.Fragment>
                        )}
                      </Timer>


                    </span>
                  </div>
                </div>
              </div>
            </Row>


            <div className="row">
              <Card className="col-12 m-2">
                <ResponsiveContainer width="100%" height={200}>
                  <LineChart
                    width={500}
                    height={200}
                    data={data}
                    margin={{
                      top: 10,
                      right: 30,
                      left: 0,
                      bottom: 0,
                    }}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" />
                    <YAxis />
                    <Tooltip />
                    <Line type="monotone" dataKey="sleep" stroke="#8884d8" fill="#8884d8" />
                  </LineChart>
                </ResponsiveContainer>

              </Card>
            </div>

          </div>
        </section>
      </div>
    </>
  )
}

export default Sleep
