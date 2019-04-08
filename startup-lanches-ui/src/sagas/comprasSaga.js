import { takeEvery, call, put } from 'redux-saga/effects'

import {
    COMPRAR_LANCHE, comprouLanche,
    CARREGAR_LISTA_COMPRAS, carregouListaCompras,
    alertarErro
} from '../reducers/comprasReducer'

import {
    verificarLancheExiste
} from '../reducers/lanchesReducer'

import ComprasService from '../services/comprasService'


//Sagas
//LOADS
function* comprarLanche(action) {
    try {
        const result = yield call(ComprasService.comprarLanche, action.payload.compra)
        if (result.hasOwnProperty('id')) {
            yield put(comprouLanche({ compra: result }))
            let lancheComprado = result.lanchesCompra[0].lanche
            yield put(verificarLancheExiste({ lanche: lancheComprado }))
        }

        else
            throw new Error("Falha ao inserir compra")
    }
    catch (erro) {
        yield put(alertarErro({ erro: erro }))
    }
}

function* carregarListaCompras() {
    try {

        const result = yield call(ComprasService.carregarListaCompras)
        if (Array.isArray(result)){
            result.sort((a, b) => b.id - a.id);
            yield put(carregouListaCompras({ compras: result }))
        }
        else
            throw new Error("Falha ao carregar lista de compras")
    }
    catch (erro) {
        yield put(alertarErro({ erro: erro }))
    }
}

export const sagasCompras = [
    takeEvery(COMPRAR_LANCHE.START, comprarLanche),
    takeEvery(CARREGAR_LISTA_COMPRAS.START, carregarListaCompras)
]
