import React from 'react'
import {
    Jumbotron, Container, Row, Col, Button, Spinner,
    Alert
} from 'react-bootstrap'
import CardLanche from '../components/CardLanche'

import { carregarListaIngredientes } from '../reducers/ingredientesReducer'


import { connect } from 'react-redux'

import { carregarListaLanches, selecionarLanche, mudarVisibilidadeCriacao } from '../reducers/lanchesReducer'
import ModalCompra from '../components/ModalCompra'
import ModalCriacao from '../components/ModalCriacao'

class Cardapio extends React.Component {

    constructor(props) {
        super(props)

        this.carregarCardapio = this.carregarCardapio.bind(this)
        this.selecionarLanche = this.selecionarLanche.bind(this)
    }

    carregarCardapio() {
        this.props.carregarListaLanches()
    }

    componentDidMount() {
        this.carregarCardapio()
    }

    selecionarLanche(lanche) {
        this.props.selecionarLanche({ lanche })
    }

    render() {
        return (
            <div>
                <ModalCompra visible={this.props.lancheSelecionado != null ? true : false} />

                <ModalCriacao />

                <Container fluid>
                    <Row>
                        <Col>
                            <Jumbotron style={{
                                display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center'
                            }}>
                                <h1>Cardápio</h1>
                                <p>
                                    Escolha abaixo alguma das nossas deliciosas opções de lanches, ou se preferir, crie seu próprio lanche personalizado!
                            </p>
                            </Jumbotron>
                        </Col>
                    </Row>
                    <Row className="justify-content-md-center">

                        {
                            this.props.conteudoEstaPronto ?
                                this.props.tamanhoCardapio > 0 ?

                                    this.props.cardapio.map((lanche) => (
                                        <CardLanche key={lanche.nome} lanche={lanche} comprarLanche={() => { this.selecionarLanche(lanche) }
                                        } />
                                    ))

                                    :
                                    <div style={{
                                        display: 'flex', flexDirection: 'column', alignItems: 'center',
                                        textAlign: 'center'
                                    }}>
                                        <Alert variant='light'>
                                            <h4 style={{ color: '#DC3545' }}>Não foram encontrados lanches :( </h4>
                                            <Button variant="outline-danger" style={{
                                                marginTop: 20
                                            }} onClick={this.carregarCardapio} >Recarregar cardápio</Button>

                                        </Alert>
                                    </div>
                                :
                                <Spinner animation="border" variant="danger" />
                        }

                    </Row>
                    <Row className="justify-content-md-center">
                        <Col sm={12} style={{
                            display: 'flex', flexDirection: 'row', justifyContent: 'center', marginTop: 80
                        }}>
                            {this.props.conteudoIngredienteEstaPronto ?
                                <Button variant="outline-danger" style={{
                                    fontSize: 19
                                }} onClick={() => {
                                    if (Array.from(this.props.ingredientes).length > 0)
                                        this.props.mudarVisibilidadeCriacao()
                                    else
                                        this.props.carregarListaIngredientes()

                                }}>

                                    {Array.from(this.props.ingredientes).length > 0 ?
                                        'Crie o seu próprio lanche!' : 'Carregar a lista de ingredientes'


                                    }
                                </Button>
                                :
                                <Spinner animation="border" variant="danger" />
                            }
                        </Col>

                    </Row>
                </Container>
            </div>
        )
    }
}

const mapStateToProps = store => ({
    cardapio: store.lanches.listaLanches,
    tamanhoCardapio: store.lanches.listaLanches.length,
    mensagemErro: store.mensagens.erro,
    temErro: store.mensagens.temErro,
    conteudoEstaPronto: store.lanches.conteudoEstaPronto,
    conteudoIngredienteEstaPronto: store.ingredientes.conteudoEstaPronto,
    lancheSelecionado: store.lanches.lancheSelecionado,
    estaCriando: store.lanches.estaCriando,
    ingredientes: store.ingredientes.listaIngredientes
})

const mapDispatchToProps = {
    carregarListaLanches,
    selecionarLanche,
    mudarVisibilidadeCriacao,
    carregarListaIngredientes
}

export default connect(mapStateToProps, mapDispatchToProps)(Cardapio)