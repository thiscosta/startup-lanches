import React from 'react'

import { Link, withRouter } from 'react-router-dom'
import { Navbar, Nav } from 'react-bootstrap'

class Header extends React.Component {

    render() {
        return (
            <div>
                <Navbar collapseOnSelect expand="lg" variant="dark"
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
                            <Nav.Link>
                                <Navbar.Brand href="#home" style={{
                                    display: 'flex', justifyContent: 'center',
                                    marginLeft: 35, marginRight: 30, fontSize: 35, flexDirection: 'column',
                                    alignItems: 'center'
                                }}>
                                    <i className="fas fa-hamburger"></i>
                                    Super Lanches
                                </Navbar.Brand>
                            </Nav.Link>
                            <Link to="/historico" style={{
                                textDecoration: 'none', fontSize: 20,
                                display: 'flex', flexDirection: 'column',
                                alignItems: 'center', color: this.props.location.pathname === '/historico' ? 'white' : 'rgba(237,237,237,.8)',
                                transition: 'all 0.3s'
                            }}>
                                <i className="fas fa-history" style={{ fontSize: 32 }}></i>
                                Histórico
                        </Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                {this.props.children}
            </div>
        )

    }
}

export default withRouter(Header);