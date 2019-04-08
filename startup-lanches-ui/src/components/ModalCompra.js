import React from 'react'

import { Form, Modal, Button } from 'react-bootstrap'
import { connect } from 'react-redux'
import { selecionarLanche } from '../reducers/lanchesReducer'
import { comprarLanche } from '../reducers/comprasReducer'

import Alert from 'react-s-alert';

class ModalCompra extends React.Component {

    constructor(props) {
        super(props)

        this.comprarLanche = this.comprarLanche.bind(this)
        this.aumentarQuantidade = this.aumentarQuantidade.bind(this)
        this.diminuirQuantidade = this.diminuirQuantidade.bind(this)

        this.state = {
            quantidade: 0,
            qtdInvalida: false
        }

    }

    async comprarLanche() {
        if (this.state.quantidade > 0 && this.props.lanche != null) {
            let compra = { lanchesCompra: [] }

            compra.lanchesCompra.push({ lanche: this.props.lanche, quantidade: this.state.quantidade })
            this.props.comprarLanche({ compra })
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.compraEfetuada !== this.props.compraEfetuada && this.props.lanche) {
            this.props.selecionarLanche({ lanche: null })
            Alert.success('Compra efetuada com sucesso!', {
                effect: 'flip'
            });
        }
    }

    async aumentarQuantidade() {
        await this.setState({
            quantidade: this.state.quantidade + 1
        })
    }

    async diminuirQuantidade() {
        if (this.state.quantidade > 0) {
            await this.setState({
                quantidade: this.state.quantidade - 1
            })
        }

    }

    render() {
        return (
            <Modal
                show={this.props.visible}
                onHide={() => {
                    this.props.selecionarLanche({ lanche: null })
                }}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Comprar: {this.props.lanche ? this.props.lanche.nome : ''}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h6>Ingredientes:</h6><br /><br />
                    {this.props.lanche ?
                        this.props.lanche.ingredientes.map((ingredienteLanche) => (
                            <div key={ingredienteLanche.id}>
                                <p>{ingredienteLanche.quantidade} unidade(s) de {ingredienteLanche.ingrediente.nome} </p>
                            </div>
                        ))

                        :
                        <div></div>
                    }

                    <Form.Group style={{
                        marginTop: 70, display: 'flex', flexDirection: 'row', alignItems: 'center'
                    }}>
                        <Form.Label style={{
                            marginRight: 10
                        }}>Quantidade:</Form.Label>

                        <Form.Control type="number" placeholder="Quantidade de lanches" value={this.state.quantidade} disabled/>
                        
                        <Button style={{ marginLeft: 10 }} variant="danger" onClick={() => {
                            this.diminuirQuantidade()
                        }}>-</Button>

                        <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={() => {
                            this.aumentarQuantidade()
                        }}>+</Button>

                    </Form.Group>
                </Modal.Body>
                <Modal.Footer style={{
                    display: 'flex', flexDirection: 'row', justifyContent: 'space-between',
                    alignItems: 'center'
                }}>
                    Preço: R${this.props.lanche ? (this.props.lanche.preco * this.state.quantidade).toFixed(2) : ''} (Sem promoções aplicadas)
                    <Button variant="outline-danger" onClick={this.comprarLanche} disabled={this.state.quantidade <= 0}>
                        Comprar
                        </Button>
                </Modal.Footer>
            </Modal >
        )
    }
}

const mapStateToProps = store => ({
    lanche: store.lanches.lancheSelecionado,
    compraEfetuada: store.compras.compraEfetuada
})

const mapDispatchToProps = {
    comprarLanche,
    selecionarLanche
}

export default connect(mapStateToProps, mapDispatchToProps)(ModalCompra)