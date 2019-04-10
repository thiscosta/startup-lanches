import React from 'react'

import { Link, withRouter } from 'react-router-dom'
import { Navbar, Nav } from 'react-bootstrap'
import { connect } from 'react-redux'

import Alert from 'react-s-alert';

class Header extends React.Component {

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.mensagemErro !== this.props.mensagemErro) {
            Alert.error(this.props.mensagemErro, {
                effect: 'flip'
            });
        }
    }

    render() {
        return (
            <div>
                <Navbar collapseOnSelect={true} expand="lg" variant="dark"
                    style={{ backgroundColor: '#981C1E' }}>

                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mx-auto" style={{
                            display: 'flex', alignItems: 'center'
                        }}>
                            <Link to="/cardapio" style={{
                                textDecoration: 'none', fontSize: 20,
                                display: 'flex', flexDirection: 'column',
                                alignItems: 'center', color: this.props.location.pathname === '/cardapio' ? 'white' : 'rgba(237,237,237,.8)',
                                transition: 'all 0.3s'
                            }}>

                                <i className="fab fa-elementor" style={{ fontSize: 32 }}></i>
                                Cardápio
                        </Link>
                            <Link to="/cardapio" style={{
                                textDecoration: 'none'
                            }}>
                                <Navbar.Brand href="#home" style={{
                                    display: 'flex', justifyContent: 'center',
                                    marginLeft: 35, marginRight: 30, fontSize: 35, flexDirection: 'column',
                                    alignItems: 'center'
                                }}>
                                    <i className="fas fa-hamburger"></i>
                                    Super Lanches
                                </Navbar.Brand>
                            </Link>
                            <Link to="/historico" style={{
                                textDecoration: 'none', fontSize: 20,
                                display: 'flex', flexDirection: 'column',
                                alignItems: 'center', color: this.props.location.pathname === '/historico' ? 'white' : 'rgba(237,237,237,.8)',
                                transition: 'all 0.3s'
                            }}>
                                <i className="fas fa-history" style={{ fontSize: 32 }}></i>
                                <Nav.Item  onSelect={function() {}}>Histórico</Nav.Item>
                                
                            </Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                {this.props.children}
            </div>
        )

    }
}

const mapStateToProps = store => ({
    mensagemErro: store.mensagens.erro,
    temErro: store.mensagens.temErro
})

const mapDispatchToProps = {
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Header));
