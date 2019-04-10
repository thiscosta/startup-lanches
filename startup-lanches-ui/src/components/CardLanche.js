import React from 'react'

import { Card, Col, Button } from 'react-bootstrap'

export default class CardLanche extends React.Component {
    constructor(props) {
        super(props)

        this.renderizarLanche = this.renderizarLanche.bind(this)
    }

    renderizarLanche(ingredienteLanche) {
        return (
            <div key={ingredienteLanche.id}>{ingredienteLanche.quantidade} - {ingredienteLanche.ingrediente.nome}</div>

        )
    }

    render() {
        return (
            <Col style={{
                margin: 20, display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer',
            }}>
                <Card text="white" style={{
                    width: '18rem', backgroundColor: '#981C1E',
                    maxHeight: '350px', height: '350px'
                }}>
                    <Card.Header style={{
                        display: 'flex', flexDirection: 'row', alignItems: 'center', justifyContent: 'center',
                        fontSize: 25
                    }}>
                        <i className="fas fa-hamburger" style={{ marginRight: 10 }}></i>
                        {this.props.lanche.nome}
                    </Card.Header>
                    <Card.Body>
                        <Card.Title>Ingredientes:</Card.Title>
                        <Card.Text>
                            {this.props.lanche.ingredientes.map((e, i) => this.renderizarLanche(e))}
                        </Card.Text>
                    </Card.Body>
                    <Card.Footer>
                        <Card.Text style={{
                            display:'flex', flexDirection: 'row', justifyContent: 'space-between',
                             alignItems: 'center'
                        }}>
                            Preço: R${this.props.lanche.preco.toFixed(2)} 
                            <Button variant="outline-light" onClick={this.props.comprarLanche}>Comprar</Button>
                        </Card.Text>
                    </Card.Footer>
                </Card>
            </Col>
        )
    }
}