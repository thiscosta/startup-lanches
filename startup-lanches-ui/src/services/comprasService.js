const axios = require('axios');


const ComprasService = {

    async comprarLanche(compra) {
        let novaCompra
        await axios.post('http://localhost:8080/compras', compra)
            .then(function (response) {
                console.log('response: ',response)
                novaCompra = response.data
            })
            .catch(function (error) {
                novaCompra = error
            })

        return novaCompra
    },

    async carregarListaCompras() {
        let listaCompras
        await axios.get('http://localhost:8080/compras')
            .then(function (response) {
                listaCompras = response.data
            })
            .catch(function (error) {
                listaCompras = error
            })

        return listaCompras
    }

}

export default ComprasService