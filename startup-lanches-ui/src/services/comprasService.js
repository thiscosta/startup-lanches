const axios = require('axios');

const returnObject = {
    success: null,
    data: ''
}

const ComprasService = {

    async comprarLanche(compra) {
        let novaCompra = returnObject
        await axios.post('http://localhost:8080/compras', compra)
            .then(function (response) {
                novaCompra.success = true
                novaCompra.data = response.data
            })
            .catch(function (error) {
                novaCompra.success = false
                novaCompra.data = error
            })

        return novaCompra
    },

    async carregarListaCompras() {
        let listaCompras = returnObject
        await axios.get('http://localhost:8080/compras')
            .then(function (response) {
                listaCompras.success = true
                listaCompras.data = response.data
            })
            .catch(function (error) {
                listaCompras.success = false
                listaCompras.data = error
            })

        return listaCompras
    }

}

export default ComprasService