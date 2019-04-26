import React, { Component } from 'react';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import RailwayService from './railway/railway-service';
import Alert from "react-bootstrap/Alert";
import Spinner from "react-bootstrap/Spinner";

export default class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      stations: [],
      paths: null,
      page: 0,
      total: 0,
      error: null,
      loading: false
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.previousPage = this.previousPage.bind(this);
    this.nextPage = this.nextPage.bind(this);
  }

  render() {
    return (
      <Container className="mt-4">
        {this.renderError(this.state.error)}
        <Row>
          <h2 className="m-auto mx-md-2">Singapore Railway System</h2>
        </Row>
        <Row className="mt-4">
          <Col md={4} xs={12}>
            <Form onSubmit={this.handleSubmit}>
              <Form.Group controlId="formOriginStation">
                <Form.Label>Origin Station</Form.Label>
                {this.renderChooser("origin", this.state.stations)}
                <Form.Text className="text-muted">
                  Please choose your origin station.
                </Form.Text>
              </Form.Group>
              <Form.Group controlId="formTargetStation">
                <Form.Label>Target Station</Form.Label>
                {this.renderChooser("target", this.state.stations)}
                <Form.Text className="text-muted">
                  Please choose your destination station.
                </Form.Text>
              </Form.Group>
              {this.renderButton()}
            </Form>
          </Col>
          <Col className="my-4 my-md-0">
            {this.renderResult()}
          </Col>
        </Row>
      </Container>
    );
  }

  renderError(error) {
    return error && (
      <Alert variant="danger">
        {error}
      </Alert>
    );
  }

  renderChooser(stateKey, stations = []) {
    if (!this.state[stateKey]) {
      const initialStationName = stations.length > 0 ? stations[0].name : "";
      this.state[stateKey] = initialStationName;
    }

    return (
      <Form.Control as="select" value={this.state[stateKey]}
                    onChange={event => this.setState({[stateKey]: event.currentTarget.value})}>
        {stations.map((station, index) => <option key={index}>{station.name}</option>)}
      </Form.Control>
    );
  }

  renderButton() {
    if (this.state.loading) {
      return (
        <Spinner animation="border" role="status">
          <span className="sr-only">Loading...</span>
        </Spinner>
      );
    }
    else {
      return (
        <Button variant="primary" type="submit">
          Suggest Routes
        </Button>
      );
    }
  }

  renderResult() {
    if (this.state.paths && this.state.paths.length > 0) {
      const {railwayEdges: edges} = this.state.paths[this.state.page];
      const stationCodes = Array.from(edges
        .reduce((set, {startStation, endStation}) => set.add(startStation.code).add(endStation.code), new Set()));

      return (
        <Card>
          <Card.Header>
            <Card.Link href="javascript:void(0)" onClick={this.previousPage}>Previous</Card.Link>
            <Card.Link href="javascript:void(0)" onClick={this.nextPage}>Next</Card.Link>
            <span className="float-right text-muted small">{this.state.page + 1} of {this.state.total}</span>
          </Card.Header>
          <Card.Body className="mt-xl-2">
            <Card.Title>Stations Travelled: {edges.length}</Card.Title>
            <Card.Subtitle className="mb-2 text-muted">Route:
              ({stationCodes.map(code => `'${code}'`).join(", ")})</Card.Subtitle>
            <Card.Body>
              <ul>
                {edges.map(({startStation, endStation, atJunction}, index) => {
                  if (atJunction) {
                    return <li key={index}>Change from {startStation.code} line to {endStation.code} line</li>;
                  }
                  else {
                    return <li key={index}>Take {startStation.code} line
                      from {startStation.name} to {endStation.name}</li>;
                  }
                })}
              </ul>
            </Card.Body>
          </Card.Body>
        </Card>
      );
    }
    else if (this.state.paths && this.state.paths.length === 0) {
      return (
        <Card className="mt-md-4">
          <Card.Header>
            <span className="float-right text-muted small">0 of 0</span>
          </Card.Header>
          <Card.Body className="m-auto">
            <Card.Title className="m-auto">No path generated</Card.Title>
          </Card.Body>
        </Card>
      );
    }
  }

  componentDidMount() {
    const error = null;
    RailwayService.getStations()
      .then(stations => this.setState({error, stations}))
      .catch(error => this.setState({error: error.toString()}));
  }

  handleSubmit(event) {
    const error = null;
    event.preventDefault();

    this.setState({loading: true});
    RailwayService.computePaths(this.state.origin, this.state.target)
      .then(paths => this.setState({error, paths, total: paths.length}))
      .catch(error => this.setState({error: error.toString()}))
      .then(() => this.setState({loading: false}));
  }

  previousPage() {
    if (this.state.page > 0) {
      this.setState({page: this.state.page - 1});
    }
  }

  nextPage() {
    if (this.state.page < this.state.total - 1) {
      this.setState({page: this.state.page + 1});
    }
  }
}