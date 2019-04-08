import React from 'react'

import { Form, Modal, Button, Row, Col } from 'react-bootstrap'
import { connect } from 'react-redux'
import { carregarListaIngredientes } from '../reducers/ingredientesReducer'
import { mudarVisibilidadeCriacao } from '../reducers/lanchesReducer'
import { comprarLanche } from '../reducers/comprasReducer'

import Alert from 'react-s-alert';

class ModalCriacao extends React.Component {

    constructor(props) {
        super(props)

        this.comprarLanche = this.comprarLanche.bind(this)
        this.aumentarQuantidade = this.aumentarQuantidade.bind(this)
        this.diminuirQuantidade = this.diminuirQuantidade.bind(this)
        this.lancheValido = this.lancheValido.bind(this)
        this.alterarNome = this.alterarNome.bind(this)
        this.criarLanche = this.criarLanche.bind(this)
        this.aumentarQuantidadeLanche = this.aumentarQuantidadeLanche.bind(this)
        this.diminuirQuantidadeLanche = this.diminuirQuantidadeLanche.bind(this)

        this.state = {
            qtdAlface: 0,
            qtdBacon: 0,
            qtdHamburguer: 0,
            qtdOvo: 0,
            qtdQueijo: 0,
            valorLanche: 0,
            valorLanches: 0,
            nomeLanche: '',
            qtdLanche: 1,
            lanche: {

            }
        }

    }

    async comprarLanche() {
        if (this.state.quantidade > 0 && this.props.lanche != null) {

            let compra = { lanchesCompra: [] }

            this.props.comprarLanche({ compra })
        }
    }

    async aumentarQuantidade(ingrediente) {
        switch (ingrediente) {
            case 'Alface':
                let alface = this.props.ingredientes.filter((ingrediente) => {
                    return ingrediente.nome === 'Alface'
                })[0]
                await this.setState({
                    qtdAlface: this.state.qtdAlface + 1,
                    valorLanche: this.state.valorLanche + alface.preco,
                    valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche + alface.preco) * this.state.qtdLanche : this.state.valorLanches
                })
                break
            case 'Bacon':
                let bacon = this.props.ingredientes.filter((ingrediente) => {
                    return ingrediente.nome === 'Bacon'
                })[0]
                await this.setState({
                    qtdBacon: this.state.qtdBacon + 1,
                    valorLanche: this.state.valorLanche + bacon.preco,
                    valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche + bacon.preco) * this.state.qtdLanche : this.state.valorLanches
                })
                break
            case 'Hamburguer':
                let hamburguer = this.props.ingredientes.filter((ingrediente) => {
                    return ingrediente.nome === 'Hambúrguer de carne'
                })[0]
                await this.setState({
                    qtdHamburguer: this.state.qtdHamburguer + 1,
                    valorLanche: this.state.valorLanche + hamburguer.preco,
                    valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche + hamburguer.preco) * this.state.qtdLanche : this.state.valorLanches
                })
                break
            case 'Ovo':
                let ovo = this.props.ingredientes.filter((ingrediente) => {
                    return ingrediente.nome === 'Ovo'
                })[0]
                await this.setState({
                    qtdOvo: this.state.qtdOvo + 1,
                    valorLanche: this.state.valorLanche + ovo.preco,
                    valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche + ovo.preco) * this.state.qtdLanche : this.state.valorLanches
                })
                break
            case 'Queijo':
                let queijo = this.props.ingredientes.filter((ingrediente) => {
                    return ingrediente.nome === 'Queijo'
                })[0]
                await this.setState({
                    qtdQueijo: this.state.qtdQueijo + 1,
                    valorLanche: this.state.valorLanche + queijo.preco,
                    valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche + queijo.preco) * this.state.qtdLanche : this.state.valorLanches
                })
                break
            default:
        }
    }

    async diminuirQuantidade(ingrediente) {
        switch (ingrediente) {
            case 'Alface':
                if (this.state.qtdAlface > 0) {
                    let alface = this.props.ingredientes.filter((ingrediente) => {
                        return ingrediente.nome === 'Alface'
                    })[0]
                    await this.setState({
                        qtdAlface: this.state.qtdAlface - 1,
                        valorLanche: this.state.valorLanche - alface.preco,
                        valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche - alface.preco) * this.state.qtdLanche : this.state.valorLanches
                    })
                }
                break
            case 'Bacon':
                if (this.state.qtdBacon > 0) {
                    let bacon = this.props.ingredientes.filter((ingrediente) => {
                        return ingrediente.nome === 'Bacon'
                    })[0]
                    await this.setState({
                        qtdBacon: this.state.qtdBacon - 1,
                        valorLanche: this.state.valorLanche - bacon.preco,
                        valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche - bacon.preco) * this.state.qtdLanche : this.state.valorLanches
                    })
                }
                break
            case 'Hamburguer':
                if (this.state.qtdHamburguer > 0) {
                    let hamburguer = this.props.ingredientes.filter((ingrediente) => {
                        return ingrediente.nome === 'Hambúrguer de carne'
                    })[0]
                    await this.setState({
                        qtdHamburguer: this.state.qtdHamburguer - 1,
                        valorLanche: this.state.valorLanche - hamburguer.preco,
                        valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche - hamburguer.preco) * this.state.qtdLanche : this.state.valorLanches
                    })
                }
                break
            case 'Ovo':
                if (this.state.qtdOvo > 0) {
                    let ovo = this.props.ingredientes.filter((ingrediente) => {
                        return ingrediente.nome === 'Ovo'
                    })[0]
                    await this.setState({
                        qtdOvo: this.state.qtdOvo - 1,
                        valorLanche: this.state.valorLanche - ovo.preco,
                        valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche - ovo.preco) * this.state.qtdLanche : this.state.valorLanches
                    })
                }
                break
            case 'Queijo':
                if (this.state.qtdQueijo > 0) {
                    let queijo = this.props.ingredientes.filter((ingrediente) => {
                        return ingrediente.nome === 'Queijo'
                    })[0]
                    await this.setState({
                        qtdQueijo: this.state.qtdQueijo - 1,
                        valorLanche: this.state.valorLanche - queijo.preco,
                        valorLanches: this.state.qtdLanche > 1 ? (this.state.valorLanche - queijo.preco) * this.state.qtdLanche : this.state.valorLanches
                    })
                }
                break
            default:
        }
    }

    async alterarNome(event) {
        var re = new RegExp("^[a-zA-Z-]*$")
        if (re.test(event.target.value))
            await this.setState({ nomeLanche: event.target.value })
    }

    async aumentarQuantidadeLanche() {
        let qtdLanche = this.state.qtdLanche + 1
        await this.setState({
            qtdLanche: qtdLanche,
            valorLanches: this.state.valorLanche * qtdLanche
        })
    }

    async diminuirQuantidadeLanche() {

        if (this.state.qtdLanche > 1) {
            let qtdLanche = this.state.qtdLanche - 1
            await this.setState({
                qtdLanche: qtdLanche,
                valorLanches: this.state.valorLanche * qtdLanche
            })
        }

    }

    lancheValido() {
        return this.state.valorLanche > 0 && this.state.nomeLanche.length > 0 ? true : false
    }

    async criarLanche() {
        let compra = { lanchesCompra: [] }

        let listaIngredientes = []

        this.props.ingredientes.map((ingrediente) => {
            switch (ingrediente.nome) {
                case 'Alface':
                    if (this.state.qtdAlface > 0) {
                        listaIngredientes.push({
                            ingrediente: ingrediente,
                            quantidade: this.state.qtdAlface
                        })
                    }
                    break
                case 'Bacon':
                    if (this.state.qtdBacon > 0) {
                        listaIngredientes.push({
                            ingrediente: ingrediente,
                            quantidade: this.state.qtdBacon
                        })
                    }
                    break
                case 'Hambúrguer de carne':
                    if (this.state.qtdHamburguer > 0) {
                        listaIngredientes.push({
                            ingrediente: ingrediente,
                            quantidade: this.state.qtdHamburguer
                        })
                    }
                    break
                case 'Ovo':
                    if (this.state.qtdOvo > 0) {
                        listaIngredientes.push({
                            ingrediente: ingrediente,
                            quantidade: this.state.qtdOvo
                        })
                    }
                    break
                case 'Queijo':
                    if (this.state.qtdQueijo > 0) {
                        listaIngredientes.push({
                            ingrediente: ingrediente,
                            quantidade: this.state.qtdQueijo
                        })
                    }
                    break
                default:
            }
        })

        console.log('listaIngredientes: ', listaIngredientes)

        let lanche = {
            nome: this.state.nomeLanche,
            ingredientes: listaIngredientes
        }

        compra.lanchesCompra.push({ lanche: lanche, quantidade: this.state.qtdLanche })

        console.log('compra: ', compra)

        this.props.comprarLanche({ compra })
    }



    componentDidMount() {
        this.props.carregarListaIngredientes()
    }

    async componentDidUpdate(prevProps, prevState) {
        if (prevProps.compraEfetuada !== this.props.compraEfetuada) {
            this.props.mudarVisibilidadeCriacao()
            await this.setState({
                qtdAlface: 0,
                qtdBacon: 0,
                qtdHamburguer: 0,
                qtdOvo: 0,
                qtdQueijo: 0,
                valorLanche: 0,
                valorLanches: 0,
                nomeLanche: '',
                qtdLanche: 1,
                lanche: {

                }
            })
            Alert.success('Lanche criado e comprado com sucesso!', {
                effect: 'flip'
            });
        }
    }

    render() {
        return (

            <Modal
                show={this.props.estaCriando}
                onHide={() => {
                    this.props.mudarVisibilidadeCriacao()
                }}
                dialogClassName="modal-90w"
                aria-labelledby="example-custom-modal-styling-title"
            >
                <Modal.Header closeButton>
                    <Modal.Title id="example-custom-modal-styling-title">
                        Criação de lanche
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Row>
                            <Col>
                                <Form.Group style={{ marginBottom: 40 }}>
                                    <Form.Label>Nome</Form.Label>
                                    <Form.Control type="text" placeholder="Nome do lanche" value={this.state.nomeLanche}
                                        onChange={this.alterarNome} />
                                    <Form.Text className="text-muted">
                                        É obrigatório dar um nome ao lanche
                                    </Form.Text>
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row style={{ display: 'flex' }}>

                            <Col style={{ flex: 1, justifyContent: 'center', alignItems: 'center', display: 'flex' }}>
                                <Form.Label style={{
                                    marginRight: 10
                                }}>Alface:</Form.Label>
                            </Col>

                            <Col style={{ flex: 3 }}>
                                <Form.Control type="number" placeholder="Quantidade" value={this.state.qtdAlface} disabled />
                            </Col>
                            <Col style={{ flex: 1, display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                                <Button style={{ marginLeft: 10 }} variant="danger" onClick={() => {
                                    this.diminuirQuantidade('Alface')
                                }}>-</Button>

                                <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={() => {
                                    this.aumentarQuantidade('Alface')
                                }}>+</Button>
                            </Col>

                        </Row>

                        <Row style={{ display: 'flex', marginTop: 10 }}>

                            <Col style={{ flex: 1, justifyContent: 'center', alignItems: 'center', display: 'flex' }}>
                                <Form.Label style={{
                                    marginRight: 10
                                }}>Bacon:</Form.Label>
                            </Col>

                            <Col style={{ flex: 3 }}>
                                <Form.Control type="number" placeholder="Quantidade" value={this.state.qtdBacon} disabled />
                            </Col>
                            <Col style={{ flex: 1, display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                                <Button style={{ marginLeft: 10 }} variant="danger" onClick={() => {
                                    this.diminuirQuantidade('Bacon')
                                }}>-</Button>

                                <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={() => {
                                    this.aumentarQuantidade('Bacon')
                                }}>+</Button>
                            </Col>

                        </Row>

                        <Row style={{ display: 'flex', marginTop: 10 }}>

                            <Col style={{ flex: 1, alignItems: 'center', display: 'flex' }}>
                                <Form.Label style={{
                                    marginRight: 10
                                }}>Hambúrguer:</Form.Label>
                            </Col>

                            <Col style={{ flex: 4 }}>
                                <Form.Control type="number" placeholder="Quantidade" value={this.state.qtdHamburguer} disabled />
                            </Col>
                            <Col style={{ flex: 1, display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                                <Button style={{ marginLeft: 10 }} variant="danger" onClick={() => {
                                    this.diminuirQuantidade('Hamburguer')
                                }}>-</Button>

                                <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={() => {
                                    this.aumentarQuantidade('Hamburguer')
                                }}>+</Button>
                            </Col>

                        </Row>

                        <Row style={{ display: 'flex', marginTop: 10 }}>

                            <Col style={{ flex: 1, justifyContent: 'center', alignItems: 'center', display: 'flex' }}>
                                <Form.Label style={{
                                    marginRight: 10
                                }}>Ovo:</Form.Label>
                            </Col>

                            <Col style={{ flex: 3 }}>
                                <Form.Control type="number" placeholder="Quantidade" value={this.state.qtdOvo} disabled />
                            </Col>
                            <Col style={{ flex: 1, display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                                <Button style={{ marginLeft: 10 }} variant="danger" onClick={() => {
                                    this.diminuirQuantidade('Ovo')
                                }}>-</Button>

                                <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={() => {
                                    this.aumentarQuantidade('Ovo')
                                }}>+</Button>
                            </Col>

                        </Row>

                        <Row style={{ display: 'flex', marginTop: 10 }}>

                            <Col style={{ flex: 1, justifyContent: 'center', alignItems: 'center', display: 'flex' }}>
                                <Form.Label style={{
                                    marginRight: 10
                                }}>Queijo:</Form.Label>
                            </Col>

                            <Col style={{ flex: 3 }}>
                                <Form.Control type="number" placeholder="Quantidade" value={this.state.qtdQueijo} disabled />
                            </Col>
                            <Col style={{ flex: 1, display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                                <Button style={{ marginLeft: 10 }} variant="danger" onClick={() => {
                                    this.diminuirQuantidade('Queijo')
                                }}>-</Button>

                                <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={() => {
                                    this.aumentarQuantidade('Queijo')
                                }}>+</Button>
                            </Col>

                        </Row>

                        <Row style={{ display: 'flex', marginTop: 60 }}>

                            <Col style={{ flex: 1, alignItems: 'center', display: 'flex' }}>
                                <Form.Label style={{
                                    marginRight: 10
                                }}>Unidades:</Form.Label>
                            </Col>

                            <Col style={{ flex: 3 }}>
                                <Form.Control style={{ flex: 1 }} type="number" placeholder="Quantidade" value={this.state.qtdLanche} disabled />
                            </Col>
                            <Col style={{ flex: 1, display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                                <Button style={{ marginLeft: 10 }} variant="danger" onClick={this.diminuirQuantidadeLanche}>-</Button>

                                <Button style={{ marginLeft: 10, marginRight: 10 }} variant="danger" onClick={this.aumentarQuantidadeLanche}>+</Button>
                            </Col>

                        </Row>


                        <Form.Group style={{ marginTop: 10 }}>
                            <Form.Label>Preço</Form.Label>
                            <Form.Control type="text" value={`R$` + (this.state.qtdLanche > 1 ? this.state.valorLanches : this.state.valorLanche).toFixed(2)} disabled />
                            <Form.Text className="text-muted">
                                *Preço sem promoções possivelmente aplicáveis
                            </Form.Text>
                        </Form.Group>

                        <Button variant="outline-danger" disabled={!this.lancheValido()} onClick={this.criarLanche}>
                            Criar e comprar
                        </Button>
                    </Form>
                </Modal.Body>
                <Modal.Footer style={{
                    display: 'flex', flexDirection: 'row', justifyContent: 'space-between',
                    alignItems: 'center'
                }}>

                </Modal.Footer>
            </Modal >

        )
    }
}

const mapStateToProps = store => ({
    ingredientes: store.ingredientes.listaIngredientes,
    estaCriando: store.lanches.estaCriando,
    compraEfetuada: store.compras.compraEfetuada
})

const mapDispatchToProps = {
    carregarListaIngredientes,
    mudarVisibilidadeCriacao,
    comprarLanche
}

export default connect(mapStateToProps, mapDispatchToProps)(ModalCriacao)