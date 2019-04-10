import React from 'react'

import { Container, Row, Col } from 'react-bootstrap'

export default class Rodape extends React.Component {
    render() {
        return (
            <Container fluid style={{
                width: '100%', height: 150, marginTop: 50
            }}>
                <Row>
                    <Col style={{
                        color: 'gray', display: 'flex', justifyContent: 'flex-end', alignItems: 'flex-end', height: 150
                    }}>
                        Desenvolvido por &nbsp;
                        <a href="http://github.com/thiscosta" target="_blank" style={{
                            color: 'black', textDecoration: 'none'
                        }}> Thiago Costa</a>
                    </Col>
                </Row>
            </Container>
        )
    }
}