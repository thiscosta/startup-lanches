import { takeEvery, call, put } from 'redux-saga/effects'

import {
    CARREGAR_LISTA_LANCHES, carregouListaLanches,
    alertarErro
} from '../reducers/lanchesReducer'

import LanchesService from '../services/lanchesService'


//Sagas
//LOADS
function* loadListLanches() {
    try {
        //yield call()
        const result = yield call(LanchesService.listarLanches)
        if (Array.isArray(result))
            yield put(carregouListaLanches({ listaLanches: result }))
        else
            throw new Error("Falha ao resgatar lanches")
    }
    catch (erro) {
        yield put(alertarErro({ erro: erro }))
    }
}

export const sagasLanches = [
    takeEvery(CARREGAR_LISTA_LANCHES.START, loadListLanches)
]
