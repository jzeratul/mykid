import React, { useState, useEffect } from "react"
import Topbar from "./Topbar"
import { getDailySleep } from '../services/DataService'
import { logout } from "../services/AuthService"
import { Card } from "react-bootstrap"
import { Bar, BarChart, CartesianGrid, Legend, ReferenceLine, ResponsiveContainer, XAxis, YAxis } from "recharts"
import { useHistory } from "react-router-dom";

// const GENERIC_ACTIVITIES = ["VITAMIN_D", "VITAMIN_K", "BONNISAN", "SPIT", "URINE", "POOP",
//   "BREAST_MILK", "BOTTLE_MOTHER_MILK", "BOTTLE_FORMULAE_MILK", "TEMPERATURE", "PUMP", "WEIGHT"]

const Sleep = (props) => {
  let history = useHistory()

  const [data, setData] = useState([])

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

      <div className="mt-4">

        <section className="content">
          <div className="container-fluid">

            <div className="row">
              <div className="col-12 col-sm-6 col-md-6">
                <div className="info-box">
                  <span className="info-box-icon bg-success elevation-1"><i className="fas fa-bed"></i></span>

                  <div className="info-box-content">
                    <span className="info-box-text">Start/Stop sleep </span>
                    <span className="info-box-number">


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
                    data={data}
                  >
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="date" reversed={true} />
                    <YAxis />
                    <Legend />
                    <ReferenceLine y={0} stroke="#000" />
                    <Bar dataKey="sleep" stackId="a" barSize={20} fill="#8884d8" />

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

export default Sleep
