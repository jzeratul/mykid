import React, { useState, useEffect } from "react"
import DataService from '../services/DataService'
import {
  ResponsiveContainer, LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, Legend
} from 'recharts';

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

      <div className="mt-5" style={{ width: '100%' }}>
        <ResponsiveContainer width="100%" minWidth={500} minHeight={400}>
          <LineChart width={500} height={400} data={graphTotals} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="date" label="date" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line connectNulls type="monotone" label="weight" dataKey="weight" stroke="#FF0000" fill="#FF0000" />
          </LineChart>
        </ResponsiveContainer>
        <ResponsiveContainer width="100%" minWidth={500} minHeight={400}>
          <LineChart width={500} height={400} data={graphTotals} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="date" label="date" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line connectNulls type="monotone" label="dailyFeedCount" dataKey="dailyFeedCount" stroke="#00FF00" fill="#00FF00" />
          </LineChart>
        </ResponsiveContainer>
        <ResponsiveContainer width="100%" minWidth={500} minHeight={400}>
          <LineChart width={500} height={400} data={graphTotals} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="date" label="date" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line connectNulls type="monotone" label="dailyFeedTime" dataKey="dailyFeedTime" stroke="#0000FF" fill="#0000FF" />
          </LineChart>
        </ResponsiveContainer>
        <ResponsiveContainer width="100%" minWidth={500} minHeight={400}>
          <LineChart width={500} height={400} data={graphTotals} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="dailyFeedCount" label="dailyFeedCount" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line connectNulls type="monotone" label="weight" dataKey="weight" stroke="#0000FF" fill="#0000FF" />
          </LineChart>
        </ResponsiveContainer>
        <ResponsiveContainer width="100%" minWidth={500} minHeight={400}>
          <LineChart width={500} height={400} data={graphTotals} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="dailyFeedTime" label="dailyFeedTime" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line connectNulls type="monotone" label="weight" dataKey="weight" stroke="#0000FF" fill="#0000FF" />
          </LineChart>
        </ResponsiveContainer>
      </div>

    </main >
  )
}

export default TabGraphs
