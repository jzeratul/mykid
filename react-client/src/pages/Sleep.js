import React, { useState, useEffect } from "react"
import Topbar from "./Topbar"
import { getDailySleep, startSleep, endSleep } from '../services/DataService'
import { logout } from "../services/AuthService"
import { Button, Card, Col, Container, Row, Tooltip } from "react-bootstrap"
import { CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, XAxis, YAxis } from "recharts"
import { useHistory } from "react-router-dom"
import { useStopwatch } from 'react-timer-hook'
import Moment from "react-moment"
import moment from 'moment';

// const GENERIC_ACTIVITIES = ["VITAMIN_D", "VITAMIN_K", "BONNISAN", "SPIT", "URINE", "POOP",
//   "BREAST_MILK", "BOTTLE_MOTHER_MILK", "BOTTLE_FORMULAE_MILK", "TEMPERATURE", "PUMP", "WEIGHT"]

const Sleep = (props) => {
  let history = useHistory()

  const [filter, setFilter] = useState("today")
  const [data, setData] = useState([])

  const {
    seconds,
    minutes,
    hours,
    isRunning,
    start,
    pause,
    reset,
  } = useStopwatch({
    autoStart: false,
    offsetTimestamp: 0
  })

  // const {
  //   seconds,
  //   minutes,
  //   hours,
  //   isRunning,
  //   start,
  //   pause,
  //   reset,
  // } = useStopwatch({
  //   autoStart: currentlySleeping,
  //   offsetTimestamp: startTime
  // })

  useEffect(() => {
    loadData(filter)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  const loadData = (filter, starttimer) => {
    getDailySleep(filter).then(
      (response) => {
        setData(response.data.dailySleepEntries)
        if (response.data.dailySleepEntries && response.data.dailySleepEntries.length > 0) {
          const recentSleep = response.data.dailySleepEntries[0]

          if (response.data.sleepActive && starttimer) {
            const sleepingTime = recentSleep.endSleep == null ? recentSleep.totalSleep : 0

            const time = moment()
            time.setSeconds(time.getSeconds() + sleepingTime)
            reset(time)
            start()
          } else {
            reset(0)
            pause()
          }

        }
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
                    <span className="info-box-number">

                      <Container>

                        <Row>
                          <Col><p>{isRunning ? 'Sleeping' : 'Not sleeping'}</p></Col>
                          <Col>
                            <p className=" float-sm-right" style={{ fontSize: '28px' }}>
                              <span>{hours}</span>:<span>{minutes}</span>:<span>{seconds}</span>
                            </p>
                          </Col>
                        </Row>

                        <Row>{!isRunning && <Button className="btn btn-lg" variant="info" onClick={() => {
                          start()
                          startSleep({
                            sleepStart: moment()
                          })
                        }}>Start</Button>}

                          {/* <button onClick={pause}>Pause</button> */}
                          {/* <button onClick={resume}>Resume</button> */}

                          {isRunning && <Button className="btn btn-lg" variant="danger" onClick={() => {

                            endSleep({
                              sleepEnd: moment()
                            })

                            reset(0)
                            loadData(filter, false)

                          }}>Stop</Button>}</Row>

                      </Container >

                    </span>
                  </div>
                </div>
              </div>
            </Row>


            <div className="row">
              <Card className="col-12 m-2">
                <div>
                  <ResponsiveContainer width="100%" height={200}>
                    <LineChart
                      width={500}
                      height={200}
                      data={data}
                      activeDot="true"
                    >
                      <CartesianGrid strokeDasharray="3 3" />
                      <XAxis dataKey="date" reversed={true} />
                      <YAxis />
                      <Tooltip />
                      <Legend />
                      <Line type="monotone" dataKey="totalSleep" stroke="#8884d8" activeDot={{ r: 8 }} />
                    </LineChart>
                  </ResponsiveContainer>
                </div>

              </Card>
            </div>

          </div>
        </section>
      </div>
    </>
  )
}

// const MyTimer = (props) => {

//   console.log(props.sleepsSince + "   " + props.currentlySleeping)
//   const startTime = props.currentlySleeping ? (+new Date()) - (+props.sleepsSince) : 0
//   console.log(startTime)

//   const {
//     seconds,
//     minutes,
//     hours,
//     days,
//     isRunning,
//     start,
//     pause,
//     resume,
//     reset,
//   } = useStopwatch({
//     autoStart: props.currentlySleeping,
//     offsetTimestamp: startTime
//   });

//   return (
//     <Container>

//       <Row>
//         <Col><p>{isRunning ? 'Sleeping' : 'Not sleeping'}</p></Col>
//         <Col>
//           <p className=" float-sm-right" style={{ fontSize: '18px' }}>
//             <span>{hours}</span>:<span>{minutes}</span>:<span>{seconds}</span>
//           </p>
//         </Col>
//       </Row>

//       <Row>{!isRunning && <Button className="btn btn-lg" variant="info" onClick={() => {
//         start()
//         saveSleep({
//           startSleep: new Date(),
//           endSleep: null,
//           createdAt: new Date()
//         })
//       }}>Start</Button>}

//         {/* <button onClick={pause}>Pause</button> */}
//         {/* <button onClick={resume}>Resume</button> */}

//         {isRunning && <Button className="btn btn-lg" variant="danger" onClick={() => {

//           reset(0)
//           pause()
//           loadData(filter)

//         }}>Stop</Button>}</Row>

//     </Container >
//   )
// }

export default Sleep
