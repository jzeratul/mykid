import React, { useState, useEffect } from "react"
import DataService from '../services/DataService'
import {
  ResponsiveContainer, LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, Legend
} from 'recharts';
import Topbar from "./Topbar";
import { Card } from "react-bootstrap";

const TabGraphs = (props) => {

  const [graphTotals, setGraphTotals] = useState([])

  useEffect(() => {
    loadData()
  }, [])

  const loadData = () => {
    DataService.getStats().then(
      (response) => {
        setGraphTotals(response.data.dailyTotals
          .sort((s1, s2) => new Date(s1.date) - new Date(s2.date)))
      })
  }

  return (
    <main>

      <Topbar />
      <div className="mt-5" style={{ width: '100%' }}>

        <Card className="m-1">
          <Card.Body className="m-0 p-0">
            <ResponsiveContainer width="100%" minWidth={200} minHeight={200} className="m-0 p-0">
              <LineChart minWidth={100} minHeight={100} data={graphTotals} className="m-0 p-0">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="weight" stroke="#FF0000" fill="#FF0000" />
              </LineChart>
            </ResponsiveContainer>
          </Card.Body>
        </Card>


        <Card className="m-1">
          <Card.Body className="m-0 p-0">
            <ResponsiveContainer width="100%" minWidth={200} minHeight={200} className="m-0 p-0">
              <LineChart minWidth={100} minHeight={100} data={graphTotals} className="m-0 p-0">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="dailyFeedQuantity" stroke="#00FF00" fill="#FF0000" />
              </LineChart>
            </ResponsiveContainer>
          </Card.Body>
        </Card>

        <Card className="m-1">
          <Card.Body className="m-0 p-0">
            <ResponsiveContainer width="100%" minWidth={200} minHeight={200} className="m-0 p-0">
              <LineChart minWidth={100} minHeight={100} data={graphTotals} className="m-0 p-0">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="dailyFeedTime" stroke="#0000FF" fill="#FF0000" />
              </LineChart>
            </ResponsiveContainer>
          </Card.Body>
        </Card>


        <Card className="m-1">
          <Card.Body className="m-0 p-0">
            <ResponsiveContainer width="100%" minWidth={200} minHeight={200} className="m-0 p-0">
              <LineChart minWidth={100} minHeight={100} data={graphTotals} className="m-0 p-0">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="dailyFeedTime" stroke="#00FFFF" fill="#FF0000" />
              </LineChart>
            </ResponsiveContainer>
          </Card.Body>
        </Card>




      </div>

    </main >
  )
}

export default TabGraphs
