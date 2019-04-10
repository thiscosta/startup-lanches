import { takeEvery, call, put } from 'redux-saga/effects'

import {
    COMPRAR_LANCHE, comprouLanche,
    CARREGAR_LISTA_COMPRAS, carregouListaCompras,
    alterarConteudoProntoCompra
} from '../reducers/comprasReducer'

import {
    verificarLancheExiste
} from '../reducers/lanchesReducer'

import { alertarErro } from '../reducers/mensagemReducer'


import ComprasService from '../services/comprasService'


//Sagas
//LOADS
function* comprarLanche(action) {
    try {
        const result = yield call(ComprasService.comprarLanche, action.payload.compra)
        if (result.success) {
            yield put(comprouLanche({ compra: result.data }))
            let lancheComprado = result.data.lanchesCompra[0].lanche
            yield put(verificarLancheExiste({ lanche: lancheComprado }))
        }

        else {
            yield put(alertarErro({ erro: 'Falha ao comprar lanche. Por favor, tente novamente.' }))
            yield put(alterarConteudoProntoCompra())
        }

    }
    catch (erro) {
        yield put(alertarErro({ erro: 'Falha ao comprar lanche. Por favor, tente novamente.' }))
        yield put(alterarConteudoProntoCompra())
    }
}

function* carregarListaCompras() {
    try {
        const result = yield call(ComprasService.carregarListaCompras)
        console.log('result do ', result)
        if (result.success) {
            result.data.sort((a, b) => b.id - a.id);
            yield put(carregouListaCompras({ compras: result.data }))
        }
        else{
            yield put(alertarErro({ erro: 'Falha ao obter compras do servidor. Por favor, tente novamente.' }))
            yield put(alterarConteudoProntoCompra())
        }
    }
    catch (erro) {
        yield put(alertarErro({ erro: 'Falha ao obter compras do servidor. Por favor, tente novamente.' }))
        yield put(alterarConteudoProntoCompra())
    }
}

export const sagasCompras = [
    takeEvery(COMPRAR_LANCHE.START, comprarLanche),
    takeEvery(CARREGAR_LISTA_COMPRAS.START, carregarListaCompras)
]
