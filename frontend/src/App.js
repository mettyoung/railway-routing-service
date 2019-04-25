import React from 'react';
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";

function App() {
  return (
    <Container className="mt-4">
      <Row>
        <h2 className="m-auto mx-md-2">Singapore Railway System</h2>
      </Row>
      <Row className="mt-4">
        <Col md={4} xs={12}>
          <Form>
            <Form.Group controlId="formOriginStation">
              <Form.Label>Origin Station</Form.Label>
              <Form.Control as="select">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
              </Form.Control>
              <Form.Text className="text-muted">
                Please choose your origin station.
              </Form.Text>
            </Form.Group>
            <Form.Group controlId="formTargetStation">
              <Form.Label>Target Station</Form.Label>
              <Form.Control as="select">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
              </Form.Control>
              <Form.Text className="text-muted">
                Please choose your destination station.
              </Form.Text>
            </Form.Group>
            <Button variant="primary" type="submit">
              Suggest Routes
            </Button>
          </Form>
        </Col>
        <Col className="my-4 my-md-0">
          <Card>
            <Card.Body className="m-xl-2">
              <Card.Title>Stations Travelled: 8</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">Route: ('CC21', 'CC20', 'CC19', 'DT9', 'DT10', 'DT11', 'DT12',
                'DT13', 'DT14')</Card.Subtitle>
              <Card.Text>
                <ul>
                  <li>Take CC line from Holland Village to Farrer Road</li>
                  <li>Take CC line from Farrer Road to Botanic Gardens</li>
                  <li>Change from CC line to DT line</li>
                  <li>Take DT line from Botanic Gardens to Stevens</li>
                  <li>Take DT line from Stevens to Newton</li>
                  <li>Take DT line from Newton to Little India</li>
                  <li>Take DT line from Little India to Rochor</li>
                  <li>Take DT line from Rochor to Bugis</li>
                </ul>
              </Card.Text>
              <Card.Link href="#">Previous</Card.Link>
              <Card.Link href="#">Next</Card.Link>
              <span className="float-right text-muted small">Page 1 of 3</span>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default App;
