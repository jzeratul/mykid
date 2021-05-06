import React, { useState, useEffect } from "react"
import Topbar from "./Topbar"
import { getDailyStats } from '../services/DataService'
import { logout } from "../services/AuthService"
import { IconBed, IconPill } from "@tabler/icons"
import { Card, Tooltip } from "react-bootstrap"
import { Bar, BarChart, CartesianGrid, Legend, ReferenceLine, ResponsiveContainer, XAxis, YAxis } from "recharts"
import { useHistory } from "react-router-dom";

// const GENERIC_ACTIVITIES = ["VITAMIN_D", "VITAMIN_K", "BONNISAN", "SPIT", "URINE", "POOP",
//   "BREAST_MILK", "BOTTLE_MOTHER_MILK", "BOTTLE_FORMULAE_MILK", "TEMPERATURE", "PUMP", "WEIGHT"]

const App = (props) => {
  let history = useHistory()

  const [stats, setStats] = useState([])
  const [lastDayStat, setLastDayStat] = useState([])
  const [vitamins, setVitamins] = useState()
  const [activities, setActivities] = useState([])
  const [last24hSleep, setLast24hSleep] = useState()

  useEffect(() => {
    loadData()
  }, [])

  const loadData = () => {

    getDailyStats().then(
      (response) => {
        console.log(response)
        setStats(response.data.dailyStatsEntries)
        if (response.data.dailyStatsEntries && response.data.dailyStatsEntries.length > 0) {

          let last = response.data.dailyStatsEntries[0]
          setLastDayStat(last)

          let vitd = countOccurences(last.activities, "VITAMIN_D")
          let vitk = countOccurences(last.activities, "VITAMIN_K")
          let bonn = countOccurences(last.activities, "BONNISAN")

          setVitamins(vitd + '/' + vitk + '/' + bonn)

          setLast24hSleep("test")

          let acts = [];

          response.data.dailyStatsEntries.forEach(
            function (day) {
              acts.push({
                date: day.date,
                vitd: countOccurences(day.activities, "VITAMIN_D"),
                vitk: countOccurences(day.activities, "VITAMIN_K"),
                bonn: countOccurences(day.activities, "BONNISAN"),
                urin: countOccurences(day.activities, "URINE"),
                poop: countOccurences(day.activities, "POOP"),
                spit: countOccurences(day.activities, "SPIT")
              })
            }
          )
          setActivities(acts)
        } else {
          setLastDayStat(
            {
              weight: 0,
              dailyFeedQuantity: 0,
              dailyFeedTime: 0
            }
          )
        }
      },
      (error) => {
        if (403 === error.response.data.status) {
          logout()
          history.push('/login')
        }
      })
  }

  const countOccurences = (arr, val) => {
    let cnt = 0;
    arr.forEach(function (item) {
      if (item === val) {
        cnt++
      }
    })
    return cnt
  }

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
              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box">
                  <span className="info-box-icon bg-info elevation-1"><i className="fas fa-weight"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Weight {lastDayStat.date}</span>
                    <span className="info-box-number">
                      {lastDayStat.weight}
                      <small>g</small>
                    </span>
                  </div>
                </div>
              </div>
              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-danger elevation-1"><i className="fas fa-clock"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Feed time {lastDayStat.date}</span>
                    <span className="info-box-number">
                      {lastDayStat.dailyFeedTime}
                      <small>min</small>
                    </span>
                  </div>
                </div>
              </div>

              <div className="clearfix hidden-md-up"></div>

              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-success elevation-1"><i className="fas fa-shopping-cart"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Feed qty{lastDayStat.date}</span>
                    <span className="info-box-number">
                      {lastDayStat.dailyFeedQuantity}
                      <small>ml</small>
                    </span>
                  </div>
                </div>
              </div>
              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-info elevation-1"><IconPill size={30} color="white" stroke={3} /></span>

                  <div className="info-box-content">
                    <span className="info-box-text">VitD/VitK/Bonnisan</span>
                    <span className="info-box-number">
                      {vitamins}
                    </span>
                  </div>
                </div>
              </div>
              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box mb-3">
                  <span className="info-box-icon bg-warning elevation-1"><IconBed size={30} color="white" stroke={3} /></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Sleep</span>
                    <span className="info-box-number">
                      {last24hSleep}
                      <small>min</small>
                    </span>
                  </div>
                </div>
              </div>
            </div>


            <div className="row">
              <Card className="col-12 m-2">

                <ResponsiveContainer width="100%" minWidth={200} minHeight={200}>

                  <BarChart
                    minWidth={200} minHeight={200}
                    data={stats}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Legend />
                    <ReferenceLine y={0} stroke="#000" />
                    <Bar dataKey="weight" stackId="a" barSize={20} fill="#8884d8" />

                  </BarChart>

                </ResponsiveContainer>
              </Card>
            </div>

            <div className="row">
              <Card className="col-12 m-2">

                <ResponsiveContainer width="100%" minWidth={200} minHeight={200}>
                  <BarChart
                    minWidth={200} minHeight={200}
                    data={stats}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Legend />
                    <ReferenceLine y={0} stroke="#000" />
                    <Bar dataKey="dailyFeedQuantity" stackId="a" barSize={20} fill="#1884b8" />

                  </BarChart>
                </ResponsiveContainer>
              </Card>
            </div>

            <div className="row">
              <Card className="col-12 m-2">

                <ResponsiveContainer width="100%" minWidth={200} minHeight={200}>
                  <BarChart
                    minWidth={200} minHeight={200}
                    data={stats}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Legend />
                    <ReferenceLine y={0} stroke="#000" />
                    <Bar dataKey="dailyFeedTime" stackId="a" barSize={20} fill="#1004b8" />

                  </BarChart>
                </ResponsiveContainer>
              </Card>
            </div>

            <div className="row">
              <Card className="col-12 m-2">

                <ResponsiveContainer width="100%" minWidth={200} minHeight={200}>
                  <BarChart
                    minWidth={200} minHeight={200}
                    data={activities}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="vitd" stackId="a" fill="#8884d8" />
                    <Bar dataKey="vitk" stackId="a" fill="#82ca9d" />
                  </BarChart>
                </ResponsiveContainer>
              </Card>
            </div>

            <div className="row">
              <Card className="col-12 m-2">

                <ResponsiveContainer width="100%" minWidth={200} minHeight={200}>
                  <BarChart
                    minWidth={200} minHeight={200}
                    data={activities}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar stackId="a" dataKey="bonn" fill="#ffc658" />
                  </BarChart>
                </ResponsiveContainer>
              </Card>
            </div>

            <div className="row">
              <Card className="col-12 m-2">

                <ResponsiveContainer width="100%" minWidth={200} minHeight={200}>
                  <BarChart
                    minWidth={200} minHeight={200}
                    data={activities}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="poop" stackId="a" fill="#8884d8" />
                    <Bar dataKey="spit" stackId="a" fill="#82ca9d" />
                    <Bar dataKey="urine" stackId="a" fill="#ffcafd" />
                  </BarChart>
                </ResponsiveContainer>
              </Card>
            </div>

          </div>
        </section>
      </div>
    </>
  )
}

export default App
