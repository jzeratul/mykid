import React, { useState, useEffect } from "react"
import { Button, InputGroup, Table, Tab, FormControl, Container, Form, Row, Card, Navbar, Image, Nav } from "react-bootstrap"
import Tabs from 'react-bootstrap/Tabs'
import { IconArrowBarLeft, IconArrowBarRight, IconCapture, IconClock, IconEraser, IconGlassFull, IconMask, IconMoodKid } from '@tabler/icons'
import DatePicker from "react-datepicker"
import "react-datepicker/dist/react-datepicker.css"
import TRANSLATIONS from "./translations"
import {
  ResponsiveContainer, LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid, Legend, BarChart, Bar
} from 'recharts';
import DataService from '../services/DataService'
import AppLogo from "../images/logo.svg"
import AuthService from "../services/AuthService"

const GENERIC_ACTIVITIES = ["BREAST_MILK", "BOTTLE_MOTHER_MILK", "BOTTLE_FORMULAE_MILK", "SPIT", "URINE", "POOP", "VITAMIN_D", "VITAMIN_K", "TEMPERATURE", "PUMP", "WEIGHT"]
const TEMPERATURES = [37.6, 37.5, 37.4, 37.3, 37.2, 37.1, 37, 36.9, 36.8, 36.7, 36.6, 36.5, 36.4]
const FOOD_DURATION = [5, 10, 15, 20, 25, 30]
const FOOD_QUANTITIES = [5, 10, 15, 20, 25, 30]

const App = (props) => {

  const empty_record = {
    datetime: new Date(),
    activities: [],
    temperature: '',
    feedFromLeftDuration: '',
    extraBottleMotherMilkQuantity: '',
    extraBottleFormulaeMilkQuantity: '',
    feedFromRightDuration: '',
    pumpFromLeftQuantity: '',
    pumpFromRightQuantity: '',
    weight: ''
  }

  const [newRecord, setNewRecord] = useState({ ...empty_record })

  const [records, setRecords] = useState([])
  const [totals, setTotals] = useState([])
  const [graph1, setGraph1] = useState([])
  const [graph2, setGraph2] = useState([])
  const [graph3, setGraph3] = useState([])

  const updateGenericActivity = (activity) => {

    let record = { ...newRecord }
    let idx = record.activities.indexOf(activity)

    if (idx === -1) {
      record.activities.push(activity)
    } else {
      record.activities.splice(idx, 1)
    }
    setNewRecord(record)
  }

  const temperatureSelection = (temperature) => {

    let record = { ...newRecord }
    record.temperature = parseFloat(temperature)
    setNewRecord(record)
  }

  const feedFromLeft = (duration) => {

    let record = { ...newRecord }
    record.feedFromLeftDuration = parseFloat(duration)
    setNewRecord(record)
  }

  const feedFromRight = (duration) => {

    let record = { ...newRecord }
    record.feedFromRightDuration = parseFloat(duration)
    setNewRecord(record)
  }

  const feedFromBottleWithMotherMilk = (quantity) => {

    let record = { ...newRecord }
    record.extraBottleMotherMilkQuantity = parseFloat(quantity)

    setNewRecord(record)
  }

  const feedFromBottleWithFormulaeMilk = (quantity) => {

    let record = { ...newRecord }
    record.extraBottleFormulaeMilkQuantity = parseFloat(quantity)

    setNewRecord(record)
  }

  const pumpFromLeft = (quantity) => {

    let record = { ...newRecord }
    record.pumpFromLeftQuantity = parseFloat(quantity)
    setNewRecord(record)
  }

  const pumpFromRight = (quantity) => {

    let record = { ...newRecord }
    record.pumpFromRightQuantity = parseFloat(quantity)
    setNewRecord(record)
  }

  const weight = (quantity) => {

    let record = { ...newRecord }

    record.weight = parseFloat(quantity)
    setNewRecord(record)
  }

  const onChangeDatetime = (datetime) => {
    let record = { ...newRecord }
    record.datetime = datetime;
    setNewRecord(record)
  }

  const resetTemperature = () => {

    let record = { ...newRecord }
    record.temperature = '';
    setNewRecord(record)
  }

  const resetFeedFromLeft = () => {

    let record = { ...newRecord }
    record.feedFromLeftDuration = ''
    setNewRecord(record)
  }

  const resetFeedFromRight = () => {

    let record = { ...newRecord }
    record.feedFromRightDuration = ''
    setNewRecord(record)
  }

  const resetFeedFromBottleWithMotherMilk = () => {

    let record = { ...newRecord }
    record.extraBottleMotherMilkQuantity = ''
    setNewRecord(record)
  }

  const resetFeedFromBottleWithFormulaeMilk = () => {

    let record = { ...newRecord }
    record.extraBottleFormulaeMilkQuantity = ''
    setNewRecord(record)
  }

  const resetPumpFromRight = () => {

    let record = { ...newRecord }
    record.pumpFromRightQuantity = ''
    setNewRecord(record)
  }

  const resetPumpFromLeft = () => {

    let record = { ...newRecord }
    record.pumpFromLeftQuantity = ''
    setNewRecord(record)
  }

  const resetWeight = () => {

    let record = { ...newRecord }
    record.weight = ''
    setNewRecord(record)
  }

  const activitySelected = (activity) => {
    return newRecord.activities.indexOf(activity) > -1
  }

  const submit = () => {

    newRecord.createdAt = new Date()

    DataService.saveStat(newRecord).then(
      () => {
        setNewRecord({ ...empty_record })
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        console.log(resMessage)
        if (403 === error.response.data.status) {
          AuthService.logout()
          props.history.push("/mykid/login")
          window.location.reload()
        }
      }
    );
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

  const statsTabSelected = (eventKey, event) => {

    if (eventKey === "stats") {

      DataService.getStats().then(
        (response) => {
          setRecords(filter(response.data.stats));
        },
        (error) => {
          console.log(error)
        }
      )
    } else if (eventKey === "graphs") {

      DataService.getStats().then(
        (response) => {
          setRecords(response.data.stats)
          setGraph1Data(response.data.stats)
          setGraph2Data(response.data.stats)
          setGraph3Data(response.data.stats)
          setTotals(response.data.dailyTotals)
        },
        (error) => {
          console.log(error)
          if (403 === error.response.data.status) {
            AuthService.logout()
            props.history.push("/mykid/login")
            window.location.reload()
          }
        })
    }
  }

  const formatDateTime = (date) => {

    let day = ('0' + date.getDate()).slice(-2)
    let month = ('0' + (date.getMonth() + 1)).slice(-2)
    let year = date.getFullYear()
    let hours = ('0' + date.getHours()).slice(-2)
    let minutes = ('0' + date.getMinutes()).slice(-2)
    // let seconds = ('0' + date.getSeconds()).slice(-2)

    return `${day}/${month}/${year} ${hours}:${minutes}`
  }

  const filter = (stats) => {
    return stats.map(function (s, idx) {
      s.datetime = formatDateTime(new Date(s.datetime))
      return s
    })
  }

  const setGraph1Data = (stats) => {
    // filter out the weights from the same day, and take the latest non empty value measured during the day

    let data = stats.map(function (s, idx) {
      return {
        datetime: new Date(s.datetime),
        weight: s.weight
      }
    })
      // we need only the entries that have a weight
      .filter(stat => !isNaN(stat.weight))
      .sort((s1, s2) => s1.datetime - s2.datetime)
      .map(function (s, idx) {
        return {
          datetime: formatDateTime(s.datetime),
          weight: s.weight
        }
      })
    setGraph1(data)
  }

  const setGraph2Data = (stats) => {

    let data = stats.map(function (s, idx) {
      return {
        datetime: formatDateTime(new Date(s.datetime)),
        FormulaBottle: s.extraBottleFormulaeMilkQuantity,
        MotherBottle: s.extraBottleMotherMilkQuantity
      }
    })
    setGraph2(data)

    console.log(data)
  }

  const setGraph3Data = (stats) => {

    let data = stats.map(function (s, idx) {
      return {
        datetime: formatDateTime(new Date(s.datetime)),
        LeftTime: s.feedFromLeftDuration,
        RightTime: s.feedFromRightDuration
      }
    })
    setGraph3(data)

    console.log(data)
  }

  const logout = () => {
    AuthService.logout()
    props.history.push("/mykid/login")
    window.location.reload()
  }

  return (
    <main>

      <Navbar bg="dark" expand="lg" variant="dark">
        <Image src={AppLogo} width={20} height={20} rounded fluid className="mr-3" />
        <Navbar.Brand>MyKid</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="mr-auto">

          </Nav>
          <Nav className="justify-content-end">

            <Nav.Item>
              <Nav.Link onClick={logout}>
                logout
              </Nav.Link>
            </Nav.Item>
          </Nav>
        </Navbar.Collapse>
      </Navbar>

      <Container className="mt-4 mb-4">
        <Tabs defaultActiveKey="home" transition={false} id="noanim-tab-example" onSelect={statsTabSelected}>
          <Tab eventKey="home" title="New Entry">

            <Container fluid>
              <Row>
                <Form>

                  {/* Date */}
                  <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Date</Card.Subtitle>

                      <DatePicker
                        className="form-control form-control-sm"
                        selected={newRecord.datetime}
                        onChange={datetime => onChangeDatetime(datetime)}
                        showTimeSelect
                        timeIntervals={15}
                        timeFormat="HH:mm"
                        dateFormat="d MMMM yyyy HH:mm"
                      />
                    </Card.Body>
                  </Card>

                  {/* Generic Activities */}
                  <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Generic Activities</Card.Subtitle>

                      {GENERIC_ACTIVITIES.map(function (activity, idx) {
                        return (
                          <Button key={idx} variant={activitySelected(activity) ? "outline-primary active" : "outline-primary"} size="md" className="m-2" onClick={() => updateGenericActivity(activity)}>{TRANSLATIONS['nl'][activity]}</Button>
                        )
                      })}
                    </Card.Body>
                  </Card>

                  {/* Temperature */}
                  {activitySelected("TEMPERATURE") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Temperature (Celsius)</Card.Subtitle>

                      {TEMPERATURES.map(function (temperature, idx) {
                        return (
                          <Button key={idx} variant={idx === 0 || idx === TEMPERATURES.length - 1 ? "outline-danger" : "outline-primary"} size="md" className="mb-2" onClick={() => temperatureSelection(temperature)}>{temperature}</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconCapture size={23} color="orange" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => temperatureSelection(event.target.value)}
                          width="50"
                          min={35}
                          max={41}
                          // selected={newRecord.temperature}
                          value={newRecord.temperature}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetTemperature()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Linker Borst Food */}
                  {activitySelected("BREAST_MILK") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Linker Borst Food</Card.Subtitle>

                      {FOOD_DURATION.map(function (duration, idx) {
                        return (
                          <Button key={idx} variant="outline-primary" size="md" className="mb-2" onClick={() => feedFromLeft(duration)}>{duration} min</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconClock size={23} color="red" stroke={3} /> <IconMoodKid size={23} color="blue" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          width="70"
                          onChange={event => feedFromLeft(event.target.value)}
                          min={0}
                          max={200}
                          value={newRecord.feedFromLeftDuration}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetFeedFromLeft()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Rechter Borst Food */}
                  {activitySelected("BREAST_MILK") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Rechter Borst Food</Card.Subtitle>

                      {FOOD_DURATION.map(function (duration, idx) {
                        return (
                          <Button key={idx} variant="outline-primary" size="md" className="mb-2" onClick={() => feedFromRight(duration)}>{duration} min</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconClock size={23} color="red" stroke={3} /> <IconMoodKid size={23} color="blue" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => feedFromRight(event.target.value)}
                          width="70"
                          min={0}
                          max={200}
                          value={newRecord.feedFromRightDuration}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetFeedFromRight()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Bottle Mother Food */}
                  {activitySelected("BOTTLE_MOTHER_MILK") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Bottle Mother Food</Card.Subtitle>

                      {FOOD_QUANTITIES.map(function (quantity, idx) {
                        return (
                          <Button key={idx} variant="outline-primary" size="md" className="mb-2" onClick={() => feedFromBottleWithMotherMilk(quantity)}>{quantity} ml</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconClock size={23} color="red" stroke={3} /> <IconMoodKid size={23} color="blue" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => feedFromBottleWithMotherMilk(event.target.value)}
                          width="70"
                          min={0}
                          max={200}
                          value={newRecord.extraBottleMotherMilkQuantity}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetFeedFromBottleWithMotherMilk()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Bottle Formlae Food */}
                  {activitySelected("BOTTLE_FORMULAE_MILK") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Bottle Formlae Food</Card.Subtitle>

                      {FOOD_QUANTITIES.map(function (quantity, idx) {
                        return (
                          <Button key={idx} variant="outline-primary" size="md" className="mb-2" onClick={() => feedFromBottleWithFormulaeMilk(quantity)}>{quantity} ml</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconClock size={23} color="red" stroke={3} /> <IconMoodKid size={23} color="blue" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => feedFromBottleWithFormulaeMilk(event.target.value)}
                          width="70"
                          min={0}
                          max={200}
                          value={newRecord.extraBottleFormulaeMilkQuantity}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetFeedFromBottleWithFormulaeMilk()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Linker Borst Pump */}
                  {activitySelected("PUMP") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Linker Borst Pump</Card.Subtitle>

                      {FOOD_QUANTITIES.map(function (quantity, idx) {
                        return (
                          <Button key={idx} variant="outline-primary" size="md" className="mb-2" onClick={() => pumpFromLeft(quantity)}>{quantity} ml</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconGlassFull size={23} color="red" stroke={3} /> <IconArrowBarLeft size={23} color="green" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => pumpFromLeft(event.target.value)}
                          width="70"
                          min={0}
                          max={200}
                          value={newRecord.pumpFromLeftQuantity}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetPumpFromLeft()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Rechter Borst Pum */}
                  {activitySelected("PUMP") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Rechter Borst Pum</Card.Subtitle>
                      {FOOD_QUANTITIES.map(function (quantity, idx) {
                        return (
                          <Button key={idx} variant="outline-primary" size="md" className="mb-2" onClick={() => pumpFromRight(quantity)}>{quantity} ml</Button>
                        )
                      })}

                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconGlassFull size={23} color="red" stroke={3} /> <IconArrowBarRight size={23} color="green" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => pumpFromRight(event.target.value)}
                          width="70"
                          min={0}
                          max={200}
                          value={newRecord.pumpFromRightQuantity}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetPumpFromRight()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>
                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  {/* Weight */}
                  {activitySelected("WEIGHT") && <Card className="mt-3">
                    <Card.Body>
                      <Card.Subtitle>Weight</Card.Subtitle>
                      <InputGroup className="mb-3">
                        <InputGroup.Prepend>
                          <InputGroup.Text> <IconMask size={23} color="blue" stroke={3} /></InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl
                          size="lg"
                          type="number"
                          onChange={event => weight(event.target.value)}
                          width="70"
                          min={0}
                          max={200}
                          value={newRecord.weight}
                        />

                        <InputGroup.Append>
                          <Button variant="default" onClick={() => resetWeight()}> <IconEraser size={23} color="orange" stroke={3} /></Button>
                        </InputGroup.Append>

                      </InputGroup>
                    </Card.Body>
                  </Card>}

                  <Card className="mt-3">
                    <Card.Body>
                      <Button variant="success" onClick={() => submit()}> <IconCapture size={23} color="white" stroke={3} />Save</Button>
                    </Card.Body>
                  </Card>

                </Form>
              </Row>
            </Container>

          </Tab>
          <Tab eventKey="stats" title="Stats">
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
                      <td>{r.daycount}</td>
                      <td>{r.datetime}</td>
                      <td><p> {calculateTotalEatenTime(r)}</p>
                        {r.feedFromLeftDuration > 0 && <div><small>Feed Left {r.feedFromLeftDuration} min</small></div>}
                        {r.feedFromRightDuration > 0 && <div><small>Feed Right {r.feedFromRightDuration} min</small></div>}
                      </td>
                      <td><p>{calculateTotalEatenQty(r)}</p>
                        {r.pumpFromLeftQuantity > 0 && <div><small>Pump Left {r.pumpFromLeftQuantity}mL</small></div>}
                        {r.pumpFromRightQuantity > 0 && <div><small>Pump Right {r.pumpFromRightQuantity}mL</small></div>}</td>
                      <td>{r.weight}</td>
                    </tr>
                  )
                })}
              </tbody>
            </Table>
          </Tab>
          <Tab eventKey="graphs" title="Graphs">

            <div className="mt-5" style={{ width: '100%' }}>
              <ResponsiveContainer width="100%" minWidth={500} minHeight={400}>
                <LineChart width={500} height={400} data={totals} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="date" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line connectNulls type="monotone" dataKey="weight" stroke="#FF0000" fill="#FF0000" />
                  <Line connectNulls type="monotone" dataKey="dailyFeedCount" stroke="#00FF00" fill="#00FF00" />
                  <Line connectNulls type="monotone" dataKey="dailyFeedTime" stroke="#0000FF" fill="#0000FF" />
                </LineChart>
              </ResponsiveContainer>

              <ResponsiveContainer width="100%" minWidth={500} minHeight={400} >
                <BarChart width={500} height={400} data={graph2} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="datetime" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar connectNulls dataKey="FormulaBottle" fill="#8884d8" />
                  <Bar connectNulls dataKey="MotherBottle" fill="#880088" />
                </BarChart>

              </ResponsiveContainer>

              <ResponsiveContainer width="100%" minWidth={500} minHeight={400} >
                <BarChart width={500} height={400} data={graph3} margin={{ top: 5, right: 30, left: 20, bottom: 5 }} >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="datetime" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Bar connectNulls dataKey="LeftTime" fill="#FF84AA" />
                  <Bar connectNulls dataKey="RightTime" fill="#88FFCC" />
                </BarChart>

              </ResponsiveContainer>
            </div>

          </Tab>
        </Tabs>
      </Container>
    </main >
  )
}

export default App
