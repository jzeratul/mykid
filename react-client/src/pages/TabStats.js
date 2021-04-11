import React, { useState, useEffect } from "react"
import { IconEraser, IconRefresh } from '@tabler/icons'
import DataService from '../services/DataService'
import AuthService from "../services/AuthService"
import { Alert, Button, Table } from "react-bootstrap"

import Moment from 'react-moment';
// import { Link } from "react-router-dom"

const TabStats = (props) => {

  const [records, setRecords] = useState([])
  const [shouldDelete, setShouldDelete] = useState([])
  const [errorMessage, setErrorMessage] = useState([])

  useEffect(() => {
    loadData()
  }, [])

  const loadData = () => {
    DataService.getStats().then(
      (response) => {
        setRecords(response.data.stats)
        setShouldDelete([])
        setErrorMessage([])
      },
      (error) => {
        setErrorMessage(error.toString())
        if (403 === error.response.data.status) {
          AuthService.logout()
          props.history.push("/login")
          window.location.reload()
        }
      })
  }

  const calculateTotalEatenTime = (rec) => {

    let duration = 0;

    if (rec.activities.indexOf("BREAST_MILK") > -1) {
      if (!isNaN(rec.feedFromLeftDuration)) {
        duration += rec.feedFromLeftDuration
      }
      if (!isNaN(rec.feedFromRightDuration)) {
        duration += rec.feedFromRightDuration
      }
      return duration
    }
  }

  const calculateTotalEatenQty = (rec) => {

    let qty = 0

    if (rec.activities.indexOf("BOTTLE_MOTHER_MILK") > -1) {
      if (!isNaN(rec.extraBottleMotherMilkQuantity)) {
        qty += rec.extraBottleMotherMilkQuantity
      }
    }

    if (rec.activities.indexOf("BOTTLE_FORMULAE_MILK") > -1) {
      if (!isNaN(rec.extraBottleFormulaeMilkQuantity)) {
        qty += rec.extraBottleFormulaeMilkQuantity
      }
    }
    if (qty === 0) qty = ''
    return qty
  }

  const deleteRecord = (r, idx) => {

    let tmp = [...shouldDelete]
    console.log(tmp)
    if (tmp[idx]) {
      tmp[idx] = false
    } else {
      tmp[idx] = true
    }
    console.log(tmp[idx] + " " + idx)
    setShouldDelete(tmp)
  }

  const doDelete = (r) => {
    DataService.deleteStat(r).then(
      (response) => {
        loadData()
      },
      (error) => {
        setErrorMessage(error.toString())
      })
  }

  // const logout = () => {
  //   AuthService.logout()
  // }

  return (
    <main>

      {errorMessage && errorMessage.length > 0 && <Alert variant="danger">
        {errorMessage}
      </Alert>}

      <Button variant="link" size="lg" onClick={() => loadData()}><IconRefresh size={50} color="green" stroke={3} /></Button>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Time</th>
            <th>Eaten Time</th>
            <th>Extra qty</th>
            <th>Weight</th>
          </tr>
        </thead>
        <tbody>

          {records.map(function (r, idx) {
            return (
              <tr key={idx}>
                <td>{r.daycount}<br />
                  <Button variant="link" size="sm" onClick={() => deleteRecord(r, idx)}>del</Button>
                </td>
                <td>
                  <Moment format="DD MMM YYYY HH:mm z" local utc>{r.datetime}</Moment>
                </td>

                <td><p> {calculateTotalEatenTime(r)}</p>
                  {r.feedFromLeftDuration > 0 && <div><small>Feed Left {r.feedFromLeftDuration} min</small></div>}
                  {r.feedFromRightDuration > 0 && <div><small>Feed Right {r.feedFromRightDuration} min</small></div>}
                </td>
                <td><p>{calculateTotalEatenQty(r)}</p>
                  {r.pumpFromLeftQuantity > 0 && <div><small>Pump Left {r.pumpFromLeftQuantity}mL</small></div>}
                  {r.pumpFromRightQuantity > 0 && <div><small>Pump Right {r.pumpFromRightQuantity}mL</small></div>}</td>
                <td>
                  {r.weight}
                  {shouldDelete[idx] && <IconEraser size={20} color="red" stroke={3} onClick={() => doDelete(r)} />}
                </td>
              </tr>
            )
          })}
        </tbody>
      </Table>

    </main >
  )
}

export default TabStats
