import React, { useState, useEffect } from "react"
import { IconEraser, IconRefresh } from '@tabler/icons'
import DataService from '../services/DataService'
import AuthService from "../services/AuthService"
import { Alert, Button, Card } from "react-bootstrap"

import Moment from 'react-moment';
import Topbar from "./Topbar"
// import { Link } from "react-router-dom"

const TabStats = (props) => {

  const [records, setRecords] = useState([])
  const [shouldDelete, setShouldDelete] = useState([])
  const [errorMessage, setErrorMessage] = useState([])

  useEffect(() => {
    loadData()
  })

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

      <Topbar />

      <Card className="m-3">
        <Card.Header>
          <Button variant="link" size="lg" onClick={() => loadData()}><IconRefresh size={30} color="green" stroke={3} /></Button>
        </Card.Header>
      </Card>

      <Card className="m-3">

        <Card.Body>
          <Card.Subtitle>
            {errorMessage && errorMessage.length > 0 && <Alert variant="danger">
              {errorMessage}
            </Alert>}
          </Card.Subtitle>

          <div className="row">


            {records.map(function (r, idx) {
              return (

                <>
                  <div className="col-md-6 col-sm-6 col-12" key={idx}>
                    <div className="info-box bg-default">
                      <span className="info-box-icon"><small>#{r.daycount}</small>
                        <Button variant="link" size="sm" onClick={() => deleteRecord(r, idx)}>del</Button>
                      </span>

                      <div className="info-box-content">
                        <span className="info-box-text"><Moment format="DD MMM YYYY HH:mm z" local utc>{r.datetime}</Moment></span>
                        <span className="info-box-number">

                          <div className="row">
                            <div className="col-6">
                              {r.feedFromLeftDuration > 0 && <small>L {r.feedFromLeftDuration} min</small>}<br />
                              {r.feedFromRightDuration > 0 && <small> R {r.feedFromRightDuration} min</small>}
                            </div>

                            <div className="col-6">
                              {r.pumpFromLeftQuantity > 0 && <small>L {r.pumpFromLeftQuantity}mL</small>}<br />
                              {r.pumpFromRightQuantity > 0 && <small>R {r.pumpFromRightQuantity}mL</small>}

                            </div>
                          </div>

                        </span>

                        <div className="progress">
                          <div className="progress-bar"></div>
                        </div>
                        <span className="progress-description">
                          {r.weight > 0 && <p>{r.weight}g</p>}
                          {shouldDelete[idx] && <IconEraser size={20} color="red" stroke={3} onClick={() => doDelete(r)} />}
                        </span>
                      </div>
                    </div>
                  </div>
                </>
              )
            })}
          </div>

        </Card.Body>
      </Card>
    </main >
  )
}

export default TabStats
