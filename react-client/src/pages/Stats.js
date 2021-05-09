import React, { useState, useEffect } from "react"
import { IconEraser, IconRefresh } from '@tabler/icons'
import { deleteStat, getStats } from '../services/DataService'
import { logout } from "../services/AuthService"
import { Alert, Button, Card, Col, Row } from "react-bootstrap"
import { useHistory } from "react-router-dom"
import TRANSLATIONS from "./translations"

import Moment from 'react-moment'
import Topbar from "./Topbar"
// import { Link } from "react-router-dom"

const TabStats = (props) => {
  let history = useHistory()

  const [records, setRecords] = useState([])
  const [shouldDelete, setShouldDelete] = useState([])
  const [errorMessage, setErrorMessage] = useState([])

  useEffect(() => {
    loadData()
  }, [])

  const loadData = () => {
    getStats().then(
      (response) => {
        setRecords(response.data.statsEntries)
        setShouldDelete([])
        setErrorMessage([])
      },
      (error) => {
        setErrorMessage(error.toString())
        if (403 === error.response.data.status) {
          logout()
          history.push('/login')
        }
      })
  }

  const deleteRecord = (r, idx) => {

    let tmp = [...shouldDelete]
    if (tmp[idx]) {
      tmp[idx] = false
    } else {
      tmp[idx] = true
    }
    setShouldDelete(tmp)
  }

  const doDelete = (r) => {
    deleteStat(r).then(
      (response) => {
        loadData()
      },
      (error) => {
        setErrorMessage(error.toString())
      })
  }

  return (
    <main>

      <Topbar />

      <Card className="m-3">
        <Card.Header>
          <Button variant="link" size="lg" onClick={() => loadData()}><IconRefresh size={30} color="green" stroke={3} /></Button>
          {errorMessage && errorMessage.length > 0 && <Alert variant="danger"> {errorMessage} </Alert>}
        </Card.Header>
      </Card>

      {records.map(function (r, idx) {

        // let color = "bg-gradient-info"

        // const possibleColors = ["bg-gradient-info",
        //   "bg-gradient-success",
        //   "bg-gradient-warning",
        //   "bg-gradient-danger",
        //   "bg-gradient-primary",
        //   "bg-gradient-default"]

        // let change = 0
        // let ci = 0

        // const nextCi = () => {
        //   if (ci === possibleColors.length - 1) {
        //     ci = 0
        //   } else {
        //     ci++
        //   }
        //   console.log("nextCi: " + ci)
        // }
        // const nextColor = () => {

        //   if (change === 1) {
        //     console.log("change: true")
        //     nextCi()
        //     change = 0
        //   } else {
        //     console.log("strange " + change)
        //   }
        //   if (r.daycount === 1) { change = 1 }
        //   console.log("" + ci + " " + possibleColors[ci] + " " + change + " " + r.daycount)
        //   return possibleColors[ci]
        // }

        return (

          <Card className={"m-3 bg-gradient-default"} key={idx}>
            <Card.Title>
              #{r.daycount}
              <Button className="float-sm-right" variant="link" size="sm" onClick={() => deleteRecord(r, idx)}>del</Button>

            </Card.Title>
            <Card.Header>
              <Moment format="DD MMM YYYY HH:mm z" local utc>{r.datetime}</Moment>
            </Card.Header>
            <Card.Body>
              <Row>
                <div className="col-sm-12 col-lg-4">
                  {r.feedFromLeftDuration > 0 && <small>FeedLeft {r.feedFromLeftDuration} min</small>}<br />
                  {r.feedFromRightDuration > 0 && <small>FeedRight {r.feedFromRightDuration} min</small>}
                </div>

                <div className="col-sm-12 col-lg-4">
                  {r.pumpFromLeftQuantity > 0 && <small>PumpLeft {r.pumpFromLeftQuantity}mL</small>}<br />
                  {r.pumpFromRightQuantity > 0 && <small>PumpRight {r.pumpFromRightQuantity}mL</small>}
                </div>

                <div className="col-sm-12 col-lg-4">
                  {r.extraBottleMotherMilkQuantity > 0 && <p>BottleMilk {r.extraBottleMotherMilkQuantity}<small>mL</small></p>}<br />
                  {r.extraBottleFormulaeMilkQuantity > 0 && <p>BottleFormulae {r.extraBottleFormulaeMilkQuantity}<small>mL</small></p>}
                </div>
              </Row>
              <Row>
                <Col>
                  {r.activities.map(function (a, i) {
                    return (
                      <span key={i}>
                        {TRANSLATIONS['nl'][a]},
                      </span>
                    )
                  })}
                </Col>
              </Row>
            </Card.Body>
            <Card.Footer>
              {r.weight > 0 && <span>Weight: {r.weight}g</span>}
              <span className="float-sm-right">{shouldDelete[idx] && <IconEraser size={20} color="red" stroke={3} onClick={() => doDelete(r)} />}</span>
            </Card.Footer>
          </Card>

        )
      })}
    </main>
  )
}

export default TabStats
