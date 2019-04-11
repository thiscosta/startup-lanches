import React from 'react'

import { connect } from 'react-redux'

import { Jumbotron, Container, Row, Col, Card, Spinner } from 'react-bootstrap'

import { carregarListaCompras } from '../reducers/comprasReducer'

class Historico extends React.Component {
    constructor(props) {
        super(props)

        this.renderizarCompra = this.renderizarCompra.bind(this)
    }

    componentDidMount() {
        this.props.carregarListaCompras()
    }

    renderizarCompra(compra) {
        return (
            <Col sm={12} style={{ width: '100%', marginTop: 30 }}>
                <Card>
                    <Card.Header style={{
                        backgroundColor: '#981C1E', fontSize: 20, color: 'white'
                    }} >
                        <i className="fas fa-hamburger" style={{ fontSize: 25, marginRight: 15 }}></i>
                        Compra {compra.id} - R${(compra.preco).toFixed(2)}
                    </Card.Header>
                    <Card.Body>
                        <Card.Title>Itens da compra:</Card.Title>
                        <Card.Text>
                            <ul>
                                {compra.lanchesCompra.map((lancheCompra) => (
                                    <li>
                                        {lancheCompra.quantidade} {lancheCompra.lanche.nome} -
                                        R${(lancheCompra.quantidade * lancheCompra.lanche.preco).toFixed(2)}
                                    </li>
                                ))}
                            </ul>
                        </Card.Text>

                        {compra.promocoesCompra.length > 0 ?
                            <div>
                                <Card.Title style={{ marginTop: 50 }}>Promoções aplicadas (em cada lanche):</Card.Title>
                                <Card.Text>
                                    <ul>
                                        {compra.promocoesCompra.map((promocaoCompra) => (
                                            <li key={promocaoCompra.promocao.id}>
                                                {promocaoCompra.promocao.nome} -
                                        R${(promocaoCompra.desconto).toFixed(2)}
                                            </li>
                                        ))}
                                    </ul>
                                </Card.Text>
                            </div>
                            : <div></div>}
                    </Card.Body>
                </Card>
            </Col>
        )
    }

    render() {
        return (
            <div style={{
                minHeight: '75vh'
            }}>
                <Container fluid>
                    <Row>
                        <Col>
                            <Jumbotron style={{
                                display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center'
                            }}>
                                <h1>Histórico de compras</h1>
                            </Jumbotron>
                        </Col>
                    </Row>
                    <Row style={{
                        display: 'flex', justifyContent: 'center', alignItems: 'center',
                        marginTop: 50
                    }}>
                        {this.props.conteudoEstaPronto ?
                            this.props.compras.map((e) => this.renderizarCompra(e))
                            :
                            <Spinner animation="border" variant="danger" />
                        }
                    </Row>
                </Container>
            </div>
        )
    }
}

const mapStateToProps = store => ({
    compras: store.compras.listaCompras,
    conteudoEstaPronto: store.compras.conteudoEstaPronto,
    mensagemErro: store.mensagens.erro,
    temErro: store.mensagens.temErro
})

const mapDispatchToProps = {
    carregarListaCompras
}

export default connect(mapStateToProps, mapDispatchToProps)(Historico)